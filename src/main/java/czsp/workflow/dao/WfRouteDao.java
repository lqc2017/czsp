package czsp.workflow.dao;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.workflow.model.WfRoute;

@IocBean
public class WfRouteDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年2月25日 根据当前环节代码和当前节点获得路由表
	 */
	public List getListByCurNode(String curNode, String wfCode) {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("displayOrder");
		cri.where().andEquals("curNode", curNode);
		cri.where().andEquals("wfCode", wfCode);
		List list = dao.query(WfRoute.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年2月25日 根据逐渐获得路由记录
	 */
	public WfRoute getRouteByRouteId(String routeId) {
		return dao.fetch(WfRoute.class, routeId);
	}

	public WfRoute getDefaultRoute(String wfCode, String curNode) {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("displayOrder");
		cri.where().andEquals("wfCode", wfCode);
		cri.where().andEquals("curNode", curNode);
		cri.where().andIsNull("isTesong");
		return dao.fetch(WfRoute.class,cri);
	}
}
