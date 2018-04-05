package czsp.plan;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DateUtil;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.common.util.SessionUtil;
import czsp.plan.model.PlanApp;
import czsp.plan.model.PlanInfo;
import czsp.plan.model.PlanOpinion;
import czsp.plan.model.view.VplanInfoDetail;
import czsp.plan.model.view.VplanWfDetail;
import czsp.plan.service.PlanInfoService;
import czsp.plan.service.PlanOpinionService;
import czsp.user.model.UserInfo;
import czsp.workflow.service.WfDefineService;

@IocBean
@At("/plan")
public class PlanModule {

	@Inject
	private PlanInfoService planInfoService;

	@Inject
	private PlanOpinionService planOpinionService;

	@Inject
	private WfDefineService wfDefineService;

	final Log log = Logs.getLog(MainSetup.class);

	/**
	 * 全琛 2018年3月3日 计划列表页面
	 */
	@At("/list")
	@Ok("jsp:/czsp/plan/show_list")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<VplanInfoDetail> infoList = planInfoService.getList();

		Map dicQx = (TreeMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));

		map.put("infoList", infoList);
		map.put("dicQx", dicQx);
		return map;
	}

	/**
	 * 全琛 2018年4月2日 可回收列表页面
	 * 
	 * @throws Exception
	 */
	@At("/retrieveList")
	@Ok("jsp:/czsp/plan/query/retrieve_list")
	@Fail("jsp:/czsp/common/fail")
	public Map<String, Object> retrieveList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载案件
		UserInfo userInfo = SessionUtil.getCurrenUser();
		if (userInfo == null || StringUtils.isBlank(userInfo.getUserId())) {
			throw new Exception("用户未登录");
		}
		if (userInfo == null || StringUtils.isBlank(userInfo.getQxId())) {
			throw new Exception("用户未设置区县");
		}

		// 根据条件加载列表(下一用户未签收，当前用户是最后一个操作用户)
		VplanWfDetail planCondition = new VplanWfDetail();
		planCondition.setIfRetrieve("1");
		planCondition.setStatus("1");
		planCondition.setIfSign("0");
		planCondition.setLastOpUser(userInfo.getUserId());
		List infoList = planInfoService.getListByCondition(planCondition);

		map.put("infoList", infoList);
		map.put("planCondition", planCondition);
		map.put("dicUtil", DicUtil.getInstance());
		return map;
	}

	/**
	 * 全琛 2018年4月3日 待签收列表页面
	 */
	@At("/toSignList")
	@Ok("jsp:/czsp/plan/query/to_sign_list")
	@Fail("jsp:/czsp/common/fail")
	public Map<String, Object> toSignList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载案件
		UserInfo userInfo = SessionUtil.getCurrenUser();
		if (userInfo == null || StringUtils.isBlank(userInfo.getUserId())) {
			throw new Exception("用户未登录");
		}
		if (userInfo == null || StringUtils.isBlank(userInfo.getQxId())) {
			throw new Exception("用户未设置区县");
		}

		// 根据条件加载列表(下一用户未签收，当前用户是最后一个操作用户)
		VplanWfDetail planCondition = new VplanWfDetail();
		planCondition.setIfSign("0");
		planCondition.setStatus("1");
		planCondition.setTodoUserId(userInfo.getUserId());
		List infoList = planInfoService.getListByCondition(planCondition);

		map.put("infoList", infoList);
		map.put("planCondition", planCondition);
		map.put("dicUtil", DicUtil.getInstance());
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
	 * 全琛 2018年3月22日 编辑页面
	 */
	@At("/edit/?")
	@Ok("jsp:/czsp/plan/edit")
	public Map<String, Object> edit(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);
		Map dicQx = (TreeMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));

		map.put("planInfo", planInfo);
		map.put("dicQx", dicQx);
		return map;
	}

	/**
	 * 全琛 2018年3月24日 查询页面
	 */
	@At("/query")
	@Ok("jsp:/czsp/plan/query/query_plan_list")
	public Map<String, Object> queryPlan(@Param("..") VplanWfDetail planCondition) {
		Map<String, Object> map = new HashMap<String, Object>();
		List infoList = planInfoService.getListByCondition(planCondition);

		// 添加区县选项
		Map qxMap = (TreeMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("infoList", infoList);
		map.put("yearList", DateUtil.getYearList(5));
		map.put("planCondition", planCondition);
		map.put("qxList", qxList);
		map.put("dicUtil", DicUtil.getInstance());
		return map;
	}

	/**
	 * 全琛 2018年3月24日 案件详细信息页面
	 */
	@At("/detail/?")
	@Ok("jsp:/czsp/plan/show_plan_detail")
	public Map<String, Object> showDetail(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);
		List opinionList = planOpinionService.getListByPlanId(planId);

		map.put("dicUtil", DicUtil.getInstance());
		map.put("opinionList", opinionList);
		map.put("planInfo", planInfo);
		return map;
	}

	/**
	 * 全琛 2018年3月26日 审批列表页面
	 * 
	 * @throws Exception
	 */
	@At("/auditList")
	@Ok("jsp:/czsp/plan/audit_list")
	@Fail("jsp:/czsp/common/fail")
	public Map<String, Object> todoList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载环节
		List phases = wfDefineService.loadPhases();
		// 加载案件
		UserInfo userInfo = SessionUtil.getCurrenUser();
		if (userInfo == null || StringUtils.isBlank(userInfo.getUserId())) {
			throw new Exception("用户未登录");
		}
		if (userInfo == null || StringUtils.isBlank(userInfo.getQxId())) {
			throw new Exception("用户未设置区县");
		}
		//
		VplanWfDetail planCondition = new VplanWfDetail();
		planCondition.setStatus("1");
		planCondition.setTodoUserId(userInfo.getUserId());
		planCondition.setSignUserId(userInfo.getUserId());
		List infoList = planInfoService.getListByCondition(planCondition, null);
		planInfoService.getListByCondition(planCondition, null);

		map.put("phases", phases);
		map.put("infoList", infoList);
		return map;
	}

	/**
	 * 全琛 2018年3月26日 审核页面
	 */
	@At("/audit/?")
	@Ok("jsp:/czsp/plan/audit_plan")
	public Map<String, Object> audit(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlanInfo planInfo = planInfoService.getPlanInfoByPlanId(planId);
		PlanOpinion planOpinion = planOpinionService.getLatestOpinion(planInfo.getInstanceId(),
				planInfo.getPlanApp().getCurNode(),SessionUtil.getCurrenUserId(), "暂存");

		map.put("dicUtil", DicUtil.getInstance());
		map.put("planInfo", planInfo);
		map.put("planOpinion", planOpinion);
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
	 * 全琛 2018年4月2日 暂存用户意见
	 */
	@At("/save")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> save(@Param("..") PlanOpinion planOpinion) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (planOpinion.getOpinionId() == null) {
				if (StringUtils.isBlank(planOpinion.getCreateBy())) {
					map.put("result", "fail");
					map.put("message", "不存在当前用户信息");
				} else {
					planOpinionService.addOpinion(planOpinion);
					map.put("result", "success");
				}

			} else {
				planOpinionService.updateOpinion(planOpinion);
				map.put("result", "success");
			}

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}
}
