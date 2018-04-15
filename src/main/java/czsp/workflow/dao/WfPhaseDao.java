package czsp.workflow.dao;

import java.util.LinkedList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.workflow.model.WfPhase;

@IocBean
public class WfPhaseDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("nodeId");
		List list = dao.query(WfPhase.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年3月4日 加载环节链表
	 */
	public List loadPhases() {
		WfPhase phase = dao.fetch(WfPhase.class, Cnd.where("isStart", "=", "1"));
		LinkedList ll = new LinkedList();
		ll.add(phase);
		while (phase.getNextPhaseId() != null) {
			phase = dao.fetch(WfPhase.class, phase.getNextPhaseId());
			ll.add(phase);
		}
		return ll;
	}

	/**
	 * 全琛 2018年4月13日 根据主键获取环节
	 */
	public WfPhase getWfPhaseByPhaseId(String phaseId) {
		return dao.fetch(WfPhase.class, phaseId);
	}

	/**
	 * 全琛 2018年4月13日 获取起始环节
	 */
	public WfPhase getStartPhase() {
		return dao.fetch(WfPhase.class, Cnd.where("isStart", "=", "1"));
	}

}
