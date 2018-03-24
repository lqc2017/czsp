package czsp.plan.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.plan.dao.PlanAppDao;
import czsp.plan.dao.PlanInfoDao;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.plan.model.view.VplanInfoDetail;
import czsp.workflow.WFOperation;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfCurInstance;
import czsp.workflow.model.WfRoute;

@IocBean
public class PlanInfoService {
	@Inject
	private PlanInfoDao planInfoDao;

	@Inject
	private PlanAppDao planAppDao;

	@Inject
	private WFOperation wfOperation;

	@Inject
	private WfRouteDao wfRouteDao;

	public List<VplanInfoDetail> getList() {
		return planInfoDao.getList();
	}

	public void add(PlanInfo newPlan, PlanApp newPlanApp) {

		Trans.exec(new Atom() {
			public void run() {
				// 初始化info表
				newPlan.setIsFinished("0");
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

	/**
	 * 全琛 2018年3月6日 根据主键获得planInfo
	 */
	public PlanInfo getPlanInfoByPlanId(String planId) {
		return planInfoDao.getPlanInfoByPlanId(planId);
	}

	/**
	 * 全琛 2018年3月8日 启动计划
	 */
	public void launchPlan(String planId) {
		PlanInfo planInfo = planInfoDao.getPlanInfoByPlanId(planId);
		WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(planInfo.getInstanceId());

		// 找到开始节点的默认节点并推进一步
		WfRoute route = wfRouteDao.getDefaultRoute(curInstance.getNodeId().substring(0, 4),
				curInstance.getNodeId().substring(4));
		try {
			wfOperation.submitWF(route, curInstance, "启动", null);

			PlanApp planApp = planAppDao.getAppByAppId(planInfo.getAppId());
			planApp.setStatus("1");
			planAppDao.update(planApp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 全琛 2018年3月22日 修改计划
	 */
	public void update(PlanInfo planInfo) {
		planInfoDao.update(planInfo, false);
	}

	/**
	 * 全琛 2018年3月23日 删除计划
	 */
	public void deletePlan(String planId) {
		Trans.exec(new Atom() {
			public void run() {
				PlanInfo planInfo = getPlanInfoByPlanId(planId);

				planInfoDao.deletePlan(planId);

				if (planInfo != null) {
					if (StringUtils.isNotEmpty(planInfo.getAppId())) {
						planAppDao.deleteApp(planInfo.getAppId());
					}

					if (StringUtils.isNotEmpty(planInfo.getInstanceId())) {
						wfOperation.deleteInstance(planInfo.getInstanceId());
					}
				}
			}
		});
	}

	/**
	 * 全琛 2018年3月24日 根据appId获得app
	 */
	public PlanApp getPlanAppByAppId(String appId) {
		return planAppDao.getAppByAppId(appId);
	}

	/**
	 * 全琛 2018年3月24日 根据删选条件获取计划列表
	 */
	public List getListByCondition(VplanInfoDetail planCondition, String orderBy) {
		return planInfoDao.getListByCondition(planCondition, orderBy);
	}

}
