package czsp.plan.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.plan.dao.PlanAppDao;
import czsp.plan.dao.PlanInfoDao;
import czsp.plan.entity.PlanApp;
import czsp.plan.entity.PlanInfo;
import czsp.user.model.UserInfo;
import czsp.workflow.WFOperation;
import czsp.workflow.model.WfCurInstance;

@IocBean
public class PlanInfoService {
	@Inject
	private PlanInfoDao planInfoDao;

	@Inject
	private PlanAppDao planAppDao;

	@Inject
	private WFOperation wfOperation;

	public List<UserInfo> getList() {
		return planInfoDao.getList();
	}

	public void add(PlanInfo newPlan, PlanApp newPlanApp) {

		Trans.exec(new Atom() {
			public void run() {
				// 初始化info表
				PlanInfo planInfo = planInfoDao.add(newPlan);

				// 初始化app表
				PlanApp planApp = planAppDao.add(planInfo, newPlanApp);

				// 初始化流程表
				WfCurInstance curInfstance = wfOperation.initInstance(planApp.getCurPhase());

				// 更新info表关联字段
				planInfo.setInstanceId(curInfstance.getInstanceId());
				planInfoDao.update(planInfo);

				// 更新app表关联字段
				planApp.setInstanceNo(curInfstance.getInstanceNo());
				planApp.setCurNode(curInfstance.getNodeId());
				planAppDao.update(planApp);
			}
		});

	}

}
