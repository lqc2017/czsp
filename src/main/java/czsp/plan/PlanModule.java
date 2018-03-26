package czsp.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DateUtil;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.plan.model.view.VplanInfoDetail;
import czsp.plan.service.PlanInfoService;
import czsp.workflow.service.WfDefineService;

@IocBean
@At("/plan")
public class PlanModule {

	@Inject
	private PlanInfoService planInfoService;

	@Inject
	private WfDefineService wfDefineService;

	final Log log = Logs.getLog(MainSetup.class);

	/**
	 * 全琛 2018年3月3日 显示计划列表
	 */
	@At("/list")
	@Ok("jsp:/czsp/plan/show_list")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<VplanInfoDetail> infoList = planInfoService.getList();

		Map dicQx = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));

		map.put("infoList", infoList);
		map.put("dicQx", dicQx);
		return map;
	}

	/**
	 * 全琛 2018年3月21日 新增计划页面
	 */
	@At("/add")
	@Ok("jsp:/czsp/plan/add")
	public Map<String, Object> add() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("dicQx", DicUtil.getInstance().getDicMap().get(Constants.DIC_QX_NO));
		return map;
	}

	/**
	 * 全琛 2018年3月3日 创建计划
	 */
	@At("/create")
	@Ok(">>:/plan/list")
	public Map<String, Object> create(@Param("..") PlanInfo newPlan, @Param("..") PlanApp newPlanApp) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			planInfoService.add(newPlan, newPlanApp);
		} catch (Exception e) {
			log.error(MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月8日 启动计划（开始流转）
	 */
	@At("/launch")
	@Ok("json")
	public Map<String, Object> launch(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			planInfoService.launchPlan(planId);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月22日 编辑页面
	 */
	@At("/edit/?")
	@Ok("jsp:/czsp/plan/edit")
	public Map<String, Object> edit(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);

		map.put("planInfo", planInfo);
		return map;
	}

	/**
	 * 全琛 2018年3月23日 删除计划
	 */
	@At("/delete/?")
	@Ok("json")
	public Map<String, Object> delete(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			planInfoService.deletePlan(planId);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
		}
		return map;
	}

	/**
	 * 全琛 2018年3月22日 更新计划
	 */
	@At("/update")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> update(@Param("..") PlanInfo planInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println(planInfo.getPlanId() + " " + planInfo.getPlanName());
			planInfoService.update(planInfo);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月24日 查询页面
	 */
	@At("/query")
	@Ok("jsp:/czsp/plan/query/query_plan_list")
	public Map<String, Object> queryPlan(@Param("..") VplanInfoDetail planCondition) {
		Map<String, Object> map = new HashMap<String, Object>();
		List infoList = planInfoService.getListByCondition(planCondition, null);

		// 添加区县选项
		Map qxMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("infoList", infoList);
		map.put("yearList", DateUtil.getYearList(5));
		map.put("planCondition", planCondition);
		map.put("qxList", qxList);
		map.put("dicUtil", DicUtil.getInstance());
		return map;
	}

	/**
	 * 全琛 2018年3月24日 案件详细信息界面
	 */
	@At("/detail/?")
	@Ok("jsp:/czsp/plan/query/show_plan_detail")
	public Map<String, Object> showDetail(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);
		PlanApp planApp = planInfoService.getPlanAppByAppId(planInfo.getAppId());

		map.put("dicUtil", DicUtil.getInstance());
		map.put("planInfo", planInfo);
		map.put("planApp", planApp);
		return map;
	}

	/**
	 * 全琛 2018年3月26日 待办列表
	 */
	@At("/todoList")
	@Ok("jsp:/czsp/plan/todo_list")
	public Map<String, Object> todoList() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载环节
		List phases = wfDefineService.loadPhases();
		// 加载案件
		VplanInfoDetail planCondition = new VplanInfoDetail();
		planCondition.setStatus("1");
		List infoList = planInfoService.getListByCondition(planCondition, null);

		map.put("phases", phases);
		map.put("infoList", infoList);
		return map;
	}

	/**
	 * 全琛 2018年3月26日 审核界面
	 */
	@At("/audit/?")
	@Ok("jsp:/czsp/plan/audit_plan")
	public Map<String, Object> audit(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);
		PlanApp planApp = planInfoService.getPlanAppByAppId(planInfo.getAppId());

		map.put("dicUtil", DicUtil.getInstance());
		map.put("planInfo", planInfo);
		map.put("planApp", planApp);
		return map;
	}
}
