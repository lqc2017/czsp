package czsp.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.MainSetup;
import czsp.common.util.SessionUtil;
import czsp.plan.dao.PlanAppDao;
import czsp.plan.dao.PlanInfoDao;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.user.dao.UserInfoDao;
import czsp.user.dao.UserOperationDao;
import czsp.user.model.UserInfo;
import czsp.user.model.UserOperation;
import czsp.workflow.dao.WfInstanceDao;
import czsp.workflow.dao.WfNodeDao;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfCurInstance;
import czsp.workflow.model.WfHisInstance;
import czsp.workflow.model.WfNode;
import czsp.workflow.model.WfRoute;

@IocBean(name = "wfOperation")
public class WFOperation extends WfInstanceDao {

	@Inject
	private WfRouteDao wfRouteDao;

	@Inject
	private WfNodeDao wfNodeDao;

	@Inject
	private UserOperationDao userOperationDao;

	@Inject
	private UserInfoDao userInfoDao;

	@Inject
	private PlanInfoDao planInfoDao;

	@Inject
	private PlanAppDao planAppDao;

	final Log log = Logs.getLog(MainSetup.class);

	/**
	 * 全琛 2018年2月24日 创建流程
	 */
	public WfCurInstance initInstance(String phaseId) {
		WfCurInstance curInstance = new WfCurInstance();
		curInstance.setIfSign("0");
		curInstance.setIfRetrieve("1");
		curInstance.setIfValid("1");
		curInstance.setCreateTime(new Date());
		// nodeId根据phaseId自己找
		curInstance.setNodeId(wfNodeDao.getStartNode(phaseId).getNodeId());
		return dao.insert(curInstance);
	}

	/**
	 * 全琛 2018年2月24日 提交&特送(归档时会清除app表的instanceNo)
	 */
	public void submitWF(WfRoute route, WfCurInstance curInstance, String opType, String todoUserId) throws Exception {
		// 当前instance无效
		if ("0".equals(curInstance.getIfValid())) {
			throw new Exception("this instance is not valid,please deal it.");
		}

		// 到时候phases字典从app表获取
		String phases = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getPhases();
		/* 测试段代码 */
		/*
		 * if(phases == null){ phases = "1103,1101,1102"; }
		 */
		/* 测试段代码 */
		String curPhaseId = route.getPhaseId();
		String nextNodeId = route.getPhaseId() + route.getNextNode();

		boolean hasNextPhase = phases.contains(curPhaseId) && !phases.endsWith(curPhaseId);
		WfNode nextNode = wfNodeDao.getNodeByNodeId(nextNodeId);

		WfNode newNextNode = generateNextNode(nextNode, curPhaseId, phases);

		if (!"1".equals(newNextNode.getIsEnd()) && (todoUserId == null || todoUserId.trim().isEmpty())) {
			todoUserId = generateTodoUserId(newNextNode.getNodeId());
			log.debug("用户未选择办理人，初始化办理人列表 : " + todoUserId);
		}

		WfCurInstance newInstance = new WfCurInstance(null, null, newNextNode.getNodeId(), "1", "0", "1", new Date(),
				todoUserId, null);
		Trans.exec(new Atom() {
			public void run() {
				PlanApp planApp = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo());
				if (planApp == null)
					planApp = new PlanApp();

				PlanInfo planInfo = planInfoDao.getPlanInfoByAppId(planApp.getAppId());
				String instanceNo = curInstance.getInstanceNo();
				archive(curInstance);
				// 归档后会有外键表会设成空值，此时要取出暂存

				// 如果下一节点是该环节最后一个节点，判断是否有下一环节
				if ("1".equals(nextNode.getIsEnd())) {
					if (hasNextPhase) {
						// 更新id和no
						WfCurInstance curInstance = dao.insert(newInstance);

						// 更新app表的no和curPhase
						planApp.setInstanceNo(curInstance.getInstanceNo());
						planApp.setCurPhase(newNextNode.getPhaseId());
					} else { // 办结
						// 将app的状态置为办结
						planApp.setStatus("2");
						planApp.setInstanceNo(null);
						
						planInfo.setIsFinished("1");
						planInfoDao.update(planInfo);
					}
				} else {
					// 只更新id不更新no
					dao.insert(newInstance);
					newInstance.setInstanceNo(instanceNo);
					dao.update(newInstance);
				}
				planAppDao.update(planApp);
				cascade(opType, SessionUtil.getCurrenUserId(), curInstance, newInstance);
			}
		});
	}

	/**
	 * 全琛 2018年2月25日 回退(归档时会清除app表的instanceNo)
	 */
	public void retreatWF(WfHisInstance hisInstance, WfCurInstance curInstance, String opType) throws Exception {
		if (hisInstance.getNodeId().endsWith("00"))
			throw new Exception("cant't retreat to start node.");

		Trans.exec(new Atom() {
			public void run() {
				PlanApp planApp = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo());
				archive(curInstance);

				WfCurInstance newInstance = new WfCurInstance(null, null, hisInstance.getNodeId(), "0", "1", "1",
						new Date(), hisInstance.getSignUserId(), hisInstance.getSignUserId());
				newInstance = dao.insert(newInstance);

				dao.update(WfCurInstance.class,
						Chain.make("instanceId", hisInstance.getInstanceId()).add("instanceNo",
								hisInstance.getInstanceNo()),
						Cnd.where("instanceId", "=", newInstance.getInstanceId()));

				newInstance = getInstanceByInstanceId(hisInstance.getInstanceId());

				planAppDao.update(planApp);
				cascade(opType, SessionUtil.getCurrenUserId(), curInstance, newInstance);
			}
		});
	}

	/**
	 * 全琛 2018年3月1日 流转
	 */
	public void circltWF(WfCurInstance curInstance, String opType, String todoUserId) throws Exception {
		if (todoUserId == null || todoUserId.trim().isEmpty()) {
			throw new Exception("circulate failed,can not find this user");
		}
		Trans.exec(new Atom() {
			public void run() {
				curInstance.setTodoUserId(todoUserId);
				curInstance.setSignUserId(null);
				curInstance.setIfSign("0");
				curInstance.setIfRetrieve("1");
				dao.update(curInstance);

				cascade(opType, SessionUtil.getCurrenUserId(), curInstance, curInstance);
			}
		});
	}

	/**
	 * 全琛 2018年2月26日 归档
	 */
	public void archive(WfCurInstance curInstance) {
		// 删除当前instance
		dao.delete(curInstance);
		// 保存当前实例到hisinstance
		WfHisInstance newHisInstance = new WfHisInstance(curInstance);
		dao.insert(newHisInstance);

	}

	/**
	 * 全琛 2018年3月4日 串联操作其他表
	 * 
	 * @param opType
	 *            操作类型
	 * @param userId
	 *            操作人
	 * @param preInstance
	 *            旧的curInstance
	 * @param newInstance
	 *            更新之后的curInstance
	 */
	protected void cascade(String opType, String userId, WfCurInstance preInstance, WfCurInstance newInstance) {
		if (newInstance == null)
			newInstance = new WfCurInstance();

		// 保存记录到user_op
		UserOperation operation = new UserOperation(opType, new Date(), userId, preInstance.getNodeId(),
				preInstance.getInstanceId(), newInstance.getInstanceId());
		userOperationDao.addOperation(operation);

		// 修改app表(curNode\lastOpUser\lastOpTime\opedUsers)
		PlanApp planApp = planAppDao.getAppByInstanceNo(newInstance.getInstanceNo());
		if (newInstance != null && !opType.equals("流转")) {
			planApp.setCurNode(newInstance.getNodeId());
		}
		planApp.setLastOpUser(userId);
		planApp.setLastOpTime(new Date());
		if (planApp.getOpedUsers() == null || "".equals(planApp.getOpedUsers()))
			planApp.setOpedUsers(userId);
		else
			planApp.setOpedUsers(planApp.getOpedUsers() + "," + userId);
		planAppDao.update(planApp);

		// 流转操作不涉及info表，只修改curInstance表和部分app表字段
		if (opType.equals("流转"))
			return;

		// 修改info表(curInstanceId)
		PlanInfo planInfo = planInfoDao.getPlanInfoByAppId(planApp.getAppId());
		if (newInstance.getInstanceId() != null) {
			planInfo.setInstanceId(newInstance.getInstanceId());
		}
		planInfoDao.update(planInfo);

	}

	/**
	 * 全琛 2018年2月28日 签收
	 */
	public void signWf(String userId, WfCurInstance instance) {
		//
		instance.setIfSign("1");
		instance.setIfRetrieve("0");
		instance.setSignUserId(userId);
		dao.update(instance);
		// ...
	}

	/**
	 * 全琛 2018年2月24日
	 */
	private String getNextPhase(String phases, String curPhaseId) {
		String[] phaseArr = phases.split(",");
		int i = 0;
		for (String phaseId : phaseArr) {
			if (phaseId.equals(curPhaseId))
				break;
			i++;
		}
		return phaseArr[i + 1];
	}

	/**
	 * 全琛 2018年2月28日 初始化正确的nextNode
	 * 
	 * @param nextNode
	 *            根据当前路由取得的下一节点
	 * @param curPhaseId
	 * @param phases
	 * @return
	 */
	private WfNode generateNextNode(WfNode nextNode, String curPhaseId, String phases) {
		boolean hasNextPhase = phases.contains(curPhaseId) && !phases.endsWith(curPhaseId);
		String nextNodeId = nextNode.getNodeId();
		if ("1".equals(nextNode.getIsEnd())) {
			if (hasNextPhase) {
				// 如果当前阶段不是最后一个阶段则初始化一个新的流程编号并指向下一阶段的起始节点的下一个节点
				String nextPhaseId = getNextPhase(phases, curPhaseId);
				WfNode startNode = wfNodeDao.getStartNode(nextPhaseId);
				nextNodeId = nextPhaseId
						+ wfRouteDao.getDefaultRoute(nextPhaseId, startNode.getWfCurNode()).getNextNode();
			}
		}
		return dao.fetch(WfNode.class, nextNodeId);
	}

	/**
	 * 全琛 2018年2月28日 生成todoUserId
	 * 
	 * 如果下一节点办理人为null,则选中该角色下所有的办理人
	 */
	private String generateTodoUserId(String nextNodeId) {
		WfNode node = wfNodeDao.getNodeByNodeId(nextNodeId);
		List<String> ids = new ArrayList<String>();
		List<UserInfo> users = userInfoDao.getListByRoleId(node.getRoleId());
		for (UserInfo user : users) {
			ids.add(user.getUserId());
		}
		String todoUserId = StringUtils.join(ids, ",");
		return todoUserId;
	}
}
