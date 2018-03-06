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
import czsp.common.util.MessageUtil;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.plan.service.PlanInfoService;
import czsp.user.model.UserInfo;

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
		List<UserInfo> infoList = planInfoService.getList();

		map.put("infoList", infoList);
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
}
