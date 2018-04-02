package czsp.plan.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.model.PlanInfo;
import czsp.plan.model.view.VplanInfoDetail;
import czsp.plan.model.view.VplanWfDetail;

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
		return dao.fetchLinks(dao.fetch(PlanInfo.class, planId), null);
	}

	/**
	 * 全琛 2018年3月23日 删除计划
	 */
	public void deletePlan(String planId) {
		dao.delete(PlanInfo.class, planId);
	}

	/**
	 * 全琛 2018年3月24日 根据筛选条件获取列表 （获取信息用）
	 */
	public List getListByCondition(VplanInfoDetail planCondition, String orderBy) {
		Criteria cri = Cnd.cri();

		// 计划名称模糊
		if (planCondition.getPlanName() != null)
			cri.where().andLike("planName", planCondition.getPlanName());

		// 年份
		if (StringUtils.isNotBlank(planCondition.getCreateYear()))
			cri.where().and("to_char(CREATE_TIME,'YYYY')", "=", planCondition.getCreateYear());

		// 区县
		if (StringUtils.isNotBlank(planCondition.getQxId()))
			cri.where().andEquals("qxId", planCondition.getQxId());

		// 状态
		if (StringUtils.isNotBlank(planCondition.getQxId()))
			cri.where().andEquals("status", planCondition.getStatus());

		if (orderBy == null)
			cri.getOrderBy().desc("create_time");

		return dao.query(VplanInfoDetail.class, cri);
	}

	/**
	 * 全琛 2018年3月27日 根据筛选条件获取列表（加载待办列表用）
	 */
	public List getListByCondition(VplanWfDetail planCondition, Object orderBy) {
		Criteria cri = Cnd.cri();

		// 待办人id和签收人id
		if (StringUtils.isNotBlank(planCondition.getTodoUserId())
				&& StringUtils.isNotBlank(planCondition.getSignUserId())) {
			SqlExpressionGroup e1 = Cnd.exps("todoUserId", "LIKE", "%" + planCondition.getTodoUserId() + "%")
					.and("ifSign", "=", "0");
			SqlExpressionGroup e2 = Cnd.exps("signUserId", "=", planCondition.getSignUserId()).and("ifSign", "=", "1");
			cri.where().or(e1).or(e2);
		}

		// 状态
		if (StringUtils.isNotBlank(planCondition.getStatus()))
			cri.where().andEquals("status", planCondition.getStatus());

		if (orderBy == null)
			cri.getOrderBy().desc("create_time");

		return dao.query(VplanWfDetail.class, cri);
	}

	/**
	 * 全琛 2018年3月28日 根据流程id获得planInfo
	 */
	public PlanInfo getPlanInfoByInstanceId(String curInstanceId) {
		return curInstanceId == null ? null : dao.fetch(PlanInfo.class, Cnd.where("instanceId", "=", curInstanceId));
	}

	/**
	 * 全琛 2018年4月2日 根据筛选条件获取计划列表（查询列表用）
	 */
	public List getListByCondition(VplanWfDetail planCondition) {
		Criteria cri = Cnd.cri();

		// 计划名称模糊
		if (planCondition.getPlanName() != null)
			cri.where().andLike("planName", planCondition.getPlanName());

		// 年份
		if (StringUtils.isNotBlank(planCondition.getCreateYear()))
			cri.where().and("to_char(CREATE_TIME,'YYYY')", "=", planCondition.getCreateYear());

		// 区县
		if (StringUtils.isNotBlank(planCondition.getQxId()))
			cri.where().andEquals("qxId", planCondition.getQxId());

		// 状态
		if (StringUtils.isNotBlank(planCondition.getStatus()))
			cri.where().andEquals("status", planCondition.getStatus());

		// 最后操作人
		if (StringUtils.isNotBlank(planCondition.getLastOpUser()))
			cri.where().andEquals("lastOpUser", planCondition.getLastOpUser());

		// 签收状态
		if (StringUtils.isNotBlank(planCondition.getIfSign()))
			cri.where().andEquals("ifSign", planCondition.getIfSign());

		// 回收状态
		if (StringUtils.isNotBlank(planCondition.getIfRetrieve()))
			cri.where().andEquals("ifRetrieve", planCondition.getIfRetrieve());

		cri.getOrderBy().desc("create_time");

		return dao.query(VplanWfDetail.class, cri);
	}

}
