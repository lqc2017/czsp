package czsp.plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.plan.model.view.VplanInfoDetail;
import czsp.plan.service.PlanInfoService;

@IocBean
@At("/plan")
public class PlanModule {

	@Inject
	private PlanInfoService planInfoService;

	final Log log = Logs.getLog(MainSetup.class);

	/**
	 * 全琛 2018年3月3日 显示计划列表
	 */
	@At("/list")
	@Ok("jsp:/czsp/plan/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<VplanInfoDetail> infoList = planInfoService.getList();

		map.put("infoList", infoList);
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
			map.put("message", map.put("message", MessageUtil.getStackTraceInfo(e)));
		}
		return map;
	}
}
