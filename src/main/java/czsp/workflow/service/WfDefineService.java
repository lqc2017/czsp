package czsp.workflow.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.workflow.dao.WfNodeDao;
import czsp.workflow.dao.WfPhaseDao;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfNode;
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
}
