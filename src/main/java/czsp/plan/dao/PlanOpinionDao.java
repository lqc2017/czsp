package czsp.plan.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
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
	 * 
	 * @param instanceId
	 * @param curNode
	 * @return
	 */
	public PlanOpinion getLatestOpinion(String instanceId, String curNode) {
		return dao.fetch(PlanOpinion.class,
				Cnd.where("instanceId", "=", instanceId).and("nodeId", "=", curNode).orderBy("createTime", "desc"));
	}

	/**
	 * 全琛 2018年4月2日 更新办理意见
	 */
	public void updateOpinion(PlanOpinion planOpinion) {
		dao.updateIgnoreNull(planOpinion);
	}

}
