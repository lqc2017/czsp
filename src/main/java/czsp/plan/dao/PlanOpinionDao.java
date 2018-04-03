package czsp.plan.dao;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.model.PlanOpinion;

@IocBean
public class PlanOpinionDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年4月2日 添加意见
	 */
	public void addOpinion(PlanOpinion planOpinion) {
		dao.insert(planOpinion);
	}

	/**
	 * 全琛 2018年4月2日 根据当前实例id和当前节点id筛选已填办理意见
	 */
	public PlanOpinion getLatestOpinion(String instanceId, String curNode, String curUserId, String opType) {
		Criteria cri = Cnd.cri();

		// 计划id
		if (StringUtils.isNotBlank(instanceId)) {
			cri.where().andEquals("instanceId", instanceId);
		}

		// 当前节点
		if (StringUtils.isNotBlank(curNode)) {
			cri.where().andEquals("nodeId", curNode);
		}

		// 创建人
		if (StringUtils.isNotBlank(curUserId)) {
			cri.where().andEquals("createBy", curUserId);
		}

		// 操作类型
		if (StringUtils.isNotBlank(opType)) {
			cri.where().andEquals("opType", opType);
		}
		
		cri.getOrderBy().desc("createTime");
		return dao.fetch(PlanOpinion.class, cri);
	}

	/**
	 * 全琛 2018年4月2日 更新办理意见
	 */
	public void updateOpinion(PlanOpinion planOpinion) {
		dao.updateIgnoreNull(planOpinion);
	}

}
