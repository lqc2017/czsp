package czsp.workflow.dao;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.workflow.model.WfNode;
import czsp.workflow.model.view.VwfNodeDetail;

@IocBean
public class WfNodeDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("nodeId");
		List list = dao.query(WfNode.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年2月24日 根据主键获得节点详细信息
	 */
	public VwfNodeDetail getNodeDetailByNodeId(String nodeId) {
		return dao.fetch(VwfNodeDetail.class, nodeId);
	}

	/**
	 * 全琛 2018年2月25日 根据主键获得node
	 */
	public WfNode getNodeByNodeId(String nodeId) {
		return dao.fetch(WfNode.class, nodeId);
	}

	/**
	 * 全琛 2018年2月25日 根据phase_id获得起始节点
	 */
	public WfNode getStartNode(String phaseId) {
		Criteria cri = Cnd.cri();
		cri.where().andEquals("phaseId", phaseId).andEquals("isStart", "1");
		return dao.fetch(WfNode.class, cri);
	}
}
