package czsp.workflow;

import java.util.Date;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.MainSetup;
import czsp.common.util.DicUtil;
import czsp.common.Constants;
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
				// 删除当前instance
				dao.delete(curInstance);
				// 保存一条记录到hisinstance
				WfHisInstance hisInstance = new WfHisInstance(curInstance);
				dao.insert(hisInstance);

				WfCurInstance newInstance = new WfCurInstance();
				newInstance.setCreateTime(new Date());
				newInstance.setIfSign("0");
				newInstance.setIfRetrieve("1");
				newInstance.setIfValid("1");
				newInstance.setNodeId(nextNodeId);
				// 如果下一节点不是该环节最后一个节点
				if (!"1".equals(nextNode.getIsEnd())) {
					// 初始化一条新记录并保存到curinstance
					dao.insert(newInstance);
					// 更新newInstance的编号
					newInstance.setInstanceNo(curInstance.getInstanceNo());
					dao.update(newInstance);
				} else {
					if (hasNextPhase) {
						// 如果当前阶段不是最后一个阶段则初始化一个新的流程编号并指向下一阶段的第一个节点
						String nextPhaseId = getNextPhase(phases, curPhaseId);
						WfNode startNode = wfNodeDao.getStartNode(nextPhaseId);
						String wfCode = DicUtil.getItemCode(Constants.DIC_WF_PHASE_NO, nextPhaseId);
						log.debug("getDefaultRoute():"+wfRouteDao.getDefaultRoute(wfCode, startNode.getWfCurNode()));
						String nextNodeId = nextPhaseId
								+ wfRouteDao.getDefaultRoute(wfCode, startNode.getWfCurNode()).getNextNode();
						log.debug("wfCode:"+wfCode);
						log.debug("getWfCurNode():"+startNode.getWfCurNode());
						newInstance.setNodeId(nextNodeId);
						// 初始化一条新记录并保存到curinstance
						dao.insert(newInstance);
					} else {
						// 将app的状态置为办结
					}
				}
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

		// 保存记录到user_op
		// todo..
		// 修改app表
		// todo..
	}

	/**
	 * 全琛 2018年2月25日 回退
	 */
	public void retreatWF(WfRoute route) {
	}

}
