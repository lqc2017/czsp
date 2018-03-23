package czsp.plan.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;

@IocBean
public class PlanAppDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	public PlanApp add(PlanInfo planInfo, PlanApp planApp) {
		planApp.setAppId(planInfo.getAppId());
		planApp.setLastOpUser(planInfo.getCreateUserId());
		planApp.setStatus("0");

		// 以下字段页面生成
		// planApp.setPhases(phases);

		if (planApp.getPhases() != null) {
			planApp.setCurPhase(planApp.getPhases().split(",")[0]);
		}

		// 以下字典由curInstance表提供
		// planApp.setInstanceNo(instanceNo);
		// planApp.setCurNode(curNode);
		planApp.setLastOpTime(planInfo.getCreateTime());
		planApp.setOpedUsers(planInfo.getCreateUserId());
		return dao.insert(planApp);
	}

	/**
	 * 全琛 2018年3月3日 修改App
	 */
	public void update(PlanApp planApp) {
		dao.update(planApp);
	}

	/**
	 * 全琛 2018年3月4日 根据instanceNo获得App
	 */
	public PlanApp getAppByInstanceNo(String instanceNo) {
		return dao.fetch(PlanApp.class, Cnd.where("instanceNo", "=", instanceNo));
	}

	/**
	 * 全琛 2018年3月8日 根据主键获得App
	 */
	public PlanApp getAppByAppId(String appId) {
		return dao.fetch(PlanApp.class, appId);
	}

	/**
	 * 全琛 2018年3月23日 删除App
	 */
	public void deleteApp(String appId) {
		dao.delete(PlanApp.class, appId);
	}

}
