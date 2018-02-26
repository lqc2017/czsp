package czsp.workflow;

import java.util.Date;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.MainSetup;
import czsp.user.dao.UserOperationDao;
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

	final Log log = Logs.getLog(MainSetup.class);

	/**
	 * 全琛 2018年2月24日 提交
	 * 
	 * @throws Exception
	 */
	public void submitWF(WfRoute route, WfCurInstance curInstance, String opType) throws Exception {
		// 当前instance无效
		if ("0".equals(curInstance.getIfValid())) {
			throw new Exception("this instance is not valid,please deal it.");
		}

		String phases = "1101,1102,1103";
		String curPhaseId = curInstance.getNodeId().substring(0, 4);
		boolean hasNextPhase = phases.contains(curPhaseId) && !phases.endsWith(curPhaseId);
		String nextNodeId = route.getRouteId().substring(0, 4) + route.getNextNode();
		WfNode nextNode = wfNodeDao.getNodeByNodeId(nextNodeId);

		Trans.exec(new Atom() {
			public void run() {
				archive(curInstance);
				String newInstanceId = null;

				WfCurInstance newInstance = new WfCurInstance();
				newInstance.setCreateTime(new Date());
				newInstance.setIfSign("0");
				newInstance.setIfRetrieve("1");
				newInstance.setIfValid("1");
				newInstance.setNodeId(nextNodeId);
				// newInstance.setUserId(userId);
				// 如果下一节点不是该环节最后一个节点
				if (!"1".equals(nextNode.getIsEnd())) {
					// 初始化一条新记录并保存到curinstance
					newInstanceId = dao.insert(newInstance).getInstanceId();
					// 更新newInstance的编号
					newInstance.setInstanceNo(curInstance.getInstanceNo());
					dao.update(newInstance);
				} else {
					if (hasNextPhase) {
						// 如果当前阶段不是最后一个阶段则初始化一个新的流程编号并指向下一阶段的第一个节点
						String nextPhaseId = getNextPhase(phases, curPhaseId);
						WfNode startNode = wfNodeDao.getStartNode(nextPhaseId);
						String nextNodeId = nextPhaseId
								+ wfRouteDao.getDefaultRoute(nextPhaseId, startNode.getWfCurNode()).getNextNode();
						newInstance.setNodeId(nextNodeId);
						// 初始化一条新记录并保存到curinstance
						dao.insert(newInstance);
					} else {
						// 将app的状态置为办结
					}
				}

				// 保存记录到user_op
				UserOperation operation = new UserOperation(opType, new Date(), null, curInstance.getNodeId(),
						curInstance.getInstanceId(), newInstanceId);
				userOperationDao.addOperation(operation);
			}

			// 遍历查找下一环节
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
		});

		// 修改app表
		// todo..
	}

	/**
	 * 全琛 2018年2月25日 回退
	 * 
	 * @throws Exception
	 */
	public void retreatWF(WfHisInstance hisInstance, WfCurInstance curInstance, String opType) throws Exception {
		if (hisInstance.getNodeId().endsWith("00"))
			throw new Exception("cant't retreat to start node.");

		Trans.exec(new Atom() {
			public void run() {
				archive(curInstance);

				WfCurInstance newInstance = new WfCurInstance();
				newInstance.setCreateTime(new Date());
				newInstance.setIfSign("1");
				newInstance.setIfRetrieve("0");
				newInstance.setIfValid("1");
				newInstance.setNodeId(hisInstance.getNodeId());
				// newInstance.setUserId(userId);
				String newInstanceId = dao.insert(newInstance).getInstanceId();

				dao.update(WfCurInstance.class, Chain.make("instanceId", hisInstance.getInstanceId()).add("instanceNo",
						hisInstance.getInstanceNo()), Cnd.where("instanceId", "=", newInstanceId));

				// 保存记录到user_op
				UserOperation operation = new UserOperation(opType, new Date(), null, curInstance.getNodeId(),
						curInstance.getInstanceId(), hisInstance.getInstanceId());
				userOperationDao.addOperation(operation);
			}
		});

		// 修改app表
		// todo..
	}

	/**
	 * 全琛 2018年2月26日 归档
	 */
	public void archive(WfCurInstance curInstance) {
		Trans.exec(new Atom() {
			@Override
			public void run() {
				// 删除当前instance
				dao.delete(curInstance);
				// 保存当前实例到hisinstance
				WfHisInstance newHisInstance = new WfHisInstance(curInstance);
				dao.insert(newHisInstance);

			}
		});
	}
}
