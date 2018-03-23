package czsp.plan.dao;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.model.PlanInfo;
import czsp.plan.model.view.VplanInfoDetail;

@IocBean
public class PlanInfoDao {

	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年3月3日 获取计划列表
	 */
	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().desc("CREATE_TIME");
		List list = dao.query(VplanInfoDetail.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年3月3日 新增计划
	 */
	public PlanInfo add(PlanInfo planInfo) {
		planInfo.setCreateTime(new Date());
		return dao.insert(planInfo);
	}

	/**
	 * 全琛 2018年3月3日 修改计划(流程修改用)
	 */
	public void update(PlanInfo planInfo) {
		dao.update(planInfo);
	}

	/**
	 * 全琛 2018年3月22日 修改计划（前台修改用）更新部分不为null的字段
	 */
	public void update(PlanInfo planInfo, boolean withNull) {
		if (withNull)
			dao.update(planInfo);
		else
			dao.updateIgnoreNull(planInfo);
	}

	/**
	 * 全琛 2018年3月4日 根据appId获得planInfo
	 */
	public PlanInfo getPlanInfoByAppId(String appId) {
		return dao.fetch(PlanInfo.class, Cnd.where("appId", "=", appId));
	}

	/**
	 * 全琛 2018年3月6日 根据主键获得planInfo
	 */
	public PlanInfo getPlanInfoByPlanId(String planId) {
		return dao.fetch(PlanInfo.class, planId);
	}

	/**
	 * 全琛 2018年3月23日 删除计划
	 */
	public void deletePlan(String planId) {
		dao.delete(PlanInfo.class, planId);
	}

}
