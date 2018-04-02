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
	public String submitWF(WfRoute route, WfCurInstance curInstance, String opType, String todoUserId)
			throws Exception {
		// 当前instance无效
		if ("0".equals(curInstance.getIfValid())) {
			throw new Exception("this instance is not valid,please deal it.");
		}

		// 到时候phases字典从app表获取
		String phases = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getPhases();
		String curPhaseId = route.getPhaseId();
		String nextNodeId = route.getPhaseId() + route.getNextNode();

		boolean hasNextPhase = phases.contains(curPhaseId) && !phases.endsWith(curPhaseId);
		WfNode nextNode = wfNodeDao.getNodeByNodeId(nextNodeId);

		WfNode newNextNode = generateNextNode(nextNode, curPhaseId, phases);

		//跨环节是选不了下一环节人员，所以要后台初始化
		if (!"1".equals(newNextNode.getIsEnd()) && StringUtils.isBlank(todoUserId)) {

			// 初始化下一节点人员(判断是否根据区县筛选)
			List<UserInfo> userInfos = new ArrayList<UserInfo>();
			String[] roleArr = newNextNode.getRoleId().split(",");
			String qxId = "";
			qxId = planInfoDao.getPlanInfoByInstanceId(curInstance.getInstanceId()).getQxId();
			if (newNextNode.getIsQxOp() == null) {
				userInfos.addAll(userInfoDao.getListByRoleId(roleArr, null, null));
			} else if (newNextNode.getIsQxOp().equals("1")) {
				userInfos.addAll(userInfoDao.getListByRoleId(roleArr, null, qxId));
			} else {
				userInfos.addAll(userInfoDao.getListByRoleId(roleArr, null, null));
			}

			List<String> userIds = new ArrayList<String>();
			for (UserInfo u : userInfos) {
				userIds.add(u.getUserId());
			}
			todoUserId = StringUtils.join(userIds.toArray(), ",");
		}

		WfCurInstance newInstance = new WfCurInstance(null, null, newNextNode.getNodeId(), "1", "0", "1", new Date(),
				todoUserId, null);
		String appId = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getAppId();

		Trans.exec(new Atom() {
			public void run() {
				boolean isEnd = false;
				archive(curInstance);

				// 如果下一节点是该环节最后一个节点，判断是否有下一环节
				if ("1".equals(nextNode.getIsEnd())) {
					if (hasNextPhase) {
						// 更新id和no
						dao.insert(newInstance);
					} else { // 办结
						isEnd = true;
					}
				} else {
					// 只更新id不更新no
					dao.insert(newInstance);
					newInstance.setInstanceNo(curInstance.getInstanceNo());
					dao.update(newInstance);
				}
				cascade(opType, SessionUtil.getCurrenUserId(), appId, curInstance, newInstance, isEnd);
			}
		});
		return newInstance.getInstanceId();
	}

	/**
	 * 全琛 2018年2月25日 回退(归档时会清除app表的instanceNo)
	 */
	public void retreatWF(WfHisInstance hisInstance, WfCurInstance curInstance, String opType) throws Exception {
		if (hisInstance.getNodeId().endsWith("00"))
			throw new Exception("cant't retreat to start node.");

		String appId = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getAppId();
		Trans.exec(new Atom() {
			public void run() {
				archive(curInstance);

				WfCurInstance newInstance = new WfCurInstance(null, null, hisInstance.getNodeId(), "0", "1", "1",
						new Date(), hisInstance.getSignUserId(), hisInstance.getSignUserId());
				newInstance = dao.insert(newInstance);

				dao.update(WfCurInstance.class,
						Chain.make("instanceId", hisInstance.getInstanceId()).add("instanceNo",
								hisInstance.getInstanceNo()),
						Cnd.where("instanceId", "=", newInstance.getInstanceId()));

				newInstance = getInstanceByInstanceId(hisInstance.getInstanceId());

				cascade(opType, SessionUtil.getCurrenUserId(), appId, curInstance, newInstance, false);
			}
		});
	}

	/**
	 * 全琛 2018年3月1日 流转
	 */
	public void circltWF(WfCurInstance curInstance, String opType, String todoUserId) throws Exception {
		if (todoUserId == null || todoUserId.trim().isEmpty()) {
			throw new Exception("circulate failed,can not find any user");
		}
		Trans.exec(new Atom() {
			public void run() {
				String appId = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getAppId();

				curInstance.setTodoUserId(todoUserId);
				curInstance.setSignUserId(null);
				curInstance.setIfSign("0");
				curInstance.setIfRetrieve("1");
				dao.update(curInstance);

				cascade(opType, SessionUtil.getCurrenUserId(), appId, curInstance, curInstance, false);
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
	 * @param appId
	 *            app主键
	 * @param preInstance
	 *            旧的curInstance
	 * @param newInstance
	 *            更新之后的curInstance
	 * @param isEnd
	 *            是否是最后一个节点
	 */
	protected void cascade(String opType, String userId, String appId, WfCurInstance preInstance,
			WfCurInstance newInstance, boolean isEnd) {
		if (newInstance == null)
			newInstance = new WfCurInstance();

		// 保存记录到user_op
		UserOperation operation = new UserOperation(opType, new Date(), userId, preInstance.getNodeId(),
				preInstance.getInstanceId(), newInstance.getInstanceId());
		userOperationDao.addOperation(operation);

		// 修改app表
		PlanApp planApp = planAppDao.getAppByAppId(appId);
		// 更新app表的LastOpUser和LastOpTime
		planApp.setLastOpUser(userId);
		planApp.setLastOpTime(new Date());
		// 更新app表的opedUsers
		if (StringUtils.isBlank(planApp.getOpedUsers()))
			planApp.setOpedUsers(userId);
		else
			planApp.setOpedUsers(planApp.getOpedUsers() + "," + userId);
		// 更新app表的no和curPhase
		planApp.setInstanceNo(newInstance.getInstanceNo());
		planApp.setCurPhase(newInstance.getNodeId().substring(0, 4));
		if (!opType.equals("流转")) {
			planApp.setCurNode(newInstance.getNodeId());
		}
		// 更新app表的Status和InstanceNo
		if (isEnd) {
			planApp.setStatus("2");
			planApp.setInstanceNo(null);
		}
		planAppDao.update(planApp);

		// 流转操作不涉及info表，只修改curInstance表和部分app表字段
		if (opType.equals("流转"))
			return;

		// 修改info表(curInstanceId)
		PlanInfo planInfo = planInfoDao.getPlanInfoByAppId(planApp.getAppId());
		if (newInstance.getInstanceId() != null) {
			planInfo.setInstanceId(newInstance.getInstanceId());
			if (isEnd)
				planInfo.setIsFinished("1");
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

		// 保存记录到user_op
		UserOperation operation = new UserOperation("签收", new Date(), userId, instance.getNodeId(),
				instance.getInstanceId(), instance.getInstanceId());
		userOperationDao.addOperation(operation);
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
	 * 全琛 2018年4月2日 回收
	 */
	public void retrieveWF(WfHisInstance hisInstance, WfCurInstance curInstance, String opType) {
		String appId = planAppDao.getAppByInstanceNo(curInstance.getInstanceNo()).getAppId();
		Trans.exec(new Atom() {
			public void run() {
				dao.delete(curInstance);

				WfCurInstance newInstance = new WfCurInstance(null, null, hisInstance.getNodeId(), "0", "1", "1",
						new Date(), hisInstance.getSignUserId(), hisInstance.getSignUserId());
				newInstance = dao.insert(newInstance);

				dao.update(WfCurInstance.class,
						Chain.make("instanceId", hisInstance.getInstanceId()).add("instanceNo",
								hisInstance.getInstanceNo()),
						Cnd.where("instanceId", "=", newInstance.getInstanceId()));

				newInstance = getInstanceByInstanceId(hisInstance.getInstanceId());

				cascade(opType, SessionUtil.getCurrenUserId(), appId, curInstance, newInstance, false);
			}
		});
	}

}
