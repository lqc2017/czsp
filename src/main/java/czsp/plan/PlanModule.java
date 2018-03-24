package czsp.plan;

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

}
