package czsp.workflow.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.workflow.dao.WfNodeDao;
import czsp.workflow.dao.WfPhaseDao;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfNode;
import czsp.workflow.model.WfPhase;
import czsp.workflow.model.WfRoute;
import czsp.workflow.model.view.VwfNodeDetail;

@IocBean
public class WfDefineService {
	@Inject
	private WfNodeDao wfNodeDao;

	@Inject
	private WfPhaseDao wfPhaseDao;

	@Inject
	private WfRouteDao wfRouteDao;

	/**
	 * 全琛 2018年3月26日 获得所有节点
	 */
	public List getList() {
		return wfNodeDao.getList();
	}

	/**
	 * 全琛 2018年3月26日 根据主键获得节点详细信息
	 */
	public VwfNodeDetail getNodeDetailByNodeId(String nodeId) {
		return wfNodeDao.getNodeDetailByNodeId(nodeId);
	}

	/**
	 * 全琛 2018年3月26日 根据当前环节代码和当前节点获得路由表
	 */
	public List getListByCurNode(String curNode, String phaseId) {
		return wfRouteDao.getListByCurNode(curNode, phaseId);
	}

	/**
	 * 全琛 2018年3月26日 根据主键获得路由记录
	 */
	public WfRoute getRouteByRouteId(String routeId) {
		return wfRouteDao.getRouteByRouteId(routeId);
	}

	/**
	 * 全琛 2018年3月26日 根据主键获得node
	 */
	public WfNode getNodeByNodeId(String nodeId) {
		return wfNodeDao.getNodeByNodeId(nodeId);
	}

	/**
	 * 全琛 2018年3月26日 加载环节链表
	 */
	public List loadPhases() {
		return wfPhaseDao.loadPhases();
	}

	/**
	 * 全琛 2018年4月13日 根据环节id加载环节以及该环节下的所有节点
	 */
	public WfPhase loadPhasesWithNodes(String phaseId) {
		LinkedList<WfNode> wfNodes = new LinkedList<WfNode>();

		WfPhase wfPhase = wfPhaseDao.getWfPhaseByPhaseId(phaseId);
		WfNode wfNode = wfNodeDao.getStartNode(phaseId);
		wfNodes.add(wfNode);

		// 根据默认路由查找加载节点表
		while (!StringUtils.equals(wfNode.getIsEnd(), "1")) {
			WfRoute wfRoute = wfRouteDao.getDefaultRoute(wfNode.getPhaseId(), wfNode.getWfCurNode());
			wfNode = wfNodeDao.getNodeByNodeId(wfRoute.getPhaseId() + wfRoute.getNextNode());
			if (wfNode != null)
				wfNodes.add(wfNode);
		}
		wfPhase.setNodeList(wfNodes);

		return wfPhase;
	}

	/**
	 * 全琛 2018年4月13日 加载所有环节以及该环节下的所有节点
	 */
	public List<WfPhase> loadAllPhasesWithNodes() {
		LinkedList<WfPhase> wfPhases = new LinkedList<WfPhase>();

		WfPhase wfPhase = wfPhaseDao.getStartPhase();
		while(true) {
			wfPhase = this.loadPhasesWithNodes(wfPhase.getPhaseId());
			wfPhases.add(wfPhase);
			if(wfPhase.getNextPhaseId()==null)
				break;
			wfPhase = wfPhaseDao.getWfPhaseByPhaseId(wfPhase.getNextPhaseId());
		} 
		return wfPhases;
	}
}
