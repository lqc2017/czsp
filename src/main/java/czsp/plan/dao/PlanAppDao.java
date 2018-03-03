package czsp.plan.dao;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.entity.PlanApp;
import czsp.plan.entity.PlanInfo;

@IocBean
public class PlanAppDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	public PlanApp add(PlanInfo planInfo) {
		PlanApp planApp = new PlanApp();
		planApp.setAppId(planInfo.getAppId());
		planApp.setLastOpUser(planInfo.getCreateUserId());
		planApp.setStatus("0");

		// 以下字段页面生成
		// planApp.setCurPhase(curPhase);
		// planApp.setPhases(phases);

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

}
