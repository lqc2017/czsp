package czsp.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.common.util.SessionUtil;
import czsp.plan.service.PlanInfoService;
import czsp.user.model.UserInfo;
import czsp.user.model.UserOperation;
import czsp.user.service.UserInfoService;
import czsp.user.service.UserOperationService;
import czsp.workflow.dao.WfInstanceDao;
import czsp.workflow.dao.WfNodeDao;
import czsp.workflow.dao.WfPhaseDao;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfCurInstance;
import czsp.workflow.model.WfHisInstance;
import czsp.workflow.model.WfNode;
import czsp.workflow.model.WfRoute;
import czsp.workflow.model.view.VwfNodeDetail;

@IocBean
@At("/wf")
public class WFModule {

	@Inject
	private WfNodeDao wfNodeDao;

	@Inject
	private WfPhaseDao wfPhaseDao;

	@Inject
	private WfRouteDao wfRouteDao;

	@Inject
	private WFOperation wfOperation;

	@Inject
	private WfInstanceDao wfInstanceDao;

	@Inject
	private UserOperationService userOperationService;

	@Inject
	private UserInfoService userInfoService;

	@Inject
	private PlanInfoService planInfoService;

	final Log log = Logs.getLog(MainSetup.class);

	@At("/list")
	@Ok("jsp:/czsp/workflow/show_list")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		// List<WfNode> wfNodes = ioc.get(WfNodeDao.class).getList();
		List<WfNode> wfNodes = wfNodeDao.getList();
		List<WfCurInstance> wfCurInstances = wfOperation.getCurInstanceList();
		List<WfHisInstance> wfHisInstances = wfOperation.getHisInstanceList();

		map.put("dicWfNode", DicUtil.getInstance().getDicMap().get(Constants.DIC_WF_NODE_NO));
		map.put("wfNodes", wfNodes);
		map.put("wfCurInstances", wfCurInstances);
		map.put("wfHisInstances", wfHisInstances);
		return map;
	}

	/**
	 * 全琛 2018年2月24日 添加流程实例(测试模块，待删)
	 */
	@At("/createInstance")
	@Ok("json")
	public Map<String, Object> createInstance() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = SessionUtil.loginAuth(map);
			if (userId != null) {
				wfOperation.initInstance("1101");
				map.put("result", "success");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message",MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年2月24日 删除流程
	 */
	@At("/deleteInstance/?")
	@Ok("json")
	public Map<String, Object> deleteInstance(String instanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			wfOperation.deleteInstance(instanceId);
		} catch (Exception e) {
			map.put("result", "fail");
		}
		map.put("result", "success");
		return map;
	}

	/**
	 * 全琛 2018年2月25日 选择下一节点
	 */
	@At("/selectNextNode")
	@Ok("jsp:/czsp/workflow/select_next_node")
	public Map<String, Object> nextNode(String instanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获得实例
		WfCurInstance instance = wfOperation.getInstanceByInstanceId(instanceId);
		// 检查签收状态(service)
		if (instance != null && "0".equals(instance.getIfSign()) && SessionUtil.getCurrenUserId() != null) {
			wfOperation.signWf(SessionUtil.getCurrenUserId(), instance);
		}
		// 查询节点信息
		VwfNodeDetail nodeDetail = wfNodeDao.getNodeDetailByNodeId(instance.getNodeId());
		// 查询路本节点路由
		List routes = wfRouteDao.getListByCurNode(nodeDetail.getWfCurNode(), nodeDetail.getPhaseId());
		// 查询最近一条历史提交操作记录
		UserOperation operation = userOperationService.getLatestOperation(instanceId, "'提交','特送'");
		WfHisInstance hisInstance = null;
		if (operation != null)
			hisInstance = wfInstanceDao.getHisInstanceByInstanceId(operation.getPreInstanceId());

		// 查询节点信息
		map.put("instance", instance);
		map.put("nodeDetail", nodeDetail);
		map.put("hisInstance", hisInstance);
		map.put("routes", routes);
		return map;
	}

	/**
	 * 全琛 2018年2月25日 提交操作
	 */
	@At("/submit")
	@Ok("json")
	public Map<String, Object> submit(String routeId, String curInstanceId, String todoUserId) {
		log.debug("todoUserId" + todoUserId);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String curUserId = SessionUtil.loginAuth(map);
			if (curUserId != null) {
				WfRoute route = wfRouteDao.getRouteByRouteId(routeId);
				WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(curInstanceId);
				if ("1".equals(route.getIsTesong())) {
					wfOperation.submitWF(route, curInstance, "特送", todoUserId);
				} else {
					wfOperation.submitWF(route, curInstance, "提交", todoUserId);
				}
				map.put("result", "success");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年2月25日 回退操作
	 */
	@At("/retreat")
	@Ok("json")
	public Map<String, Object> retreat(String hisInstanceId, String curInstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String curUserId = SessionUtil.loginAuth(map);
			if (curUserId != null) {
				WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(curInstanceId);
				WfHisInstance hisInstance = wfOperation.getHisInstanceByInstanceId(hisInstanceId);
				wfOperation.retreatWF(hisInstance, curInstance, "回退");
				map.put("result", "success");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年2月25日 流转操作
	 */
	@At("/circulate")
	@Ok("json")
	public Map<String, Object> circulate(String curInstanceId, String todoUserId) {
		log.debug("todoUserId" + todoUserId);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String curUserId = SessionUtil.loginAuth(map);
			if (curUserId != null) {
				WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(curInstanceId);
				wfOperation.circltWF(curInstance, "流转", todoUserId);
				map.put("result", "success");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	@At("/getNextUserList")
	@Ok("json")
	public Map<String, Object> getNextUserList(String routeId, String hisInstanceId, String curInstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserInfo> userInfos = new ArrayList<UserInfo>();
			// 正常&特送人员列表
			log.debug("routeId:" + routeId);
			log.debug("hisInstanceId:" + hisInstanceId);
			log.debug("curInstanceId:" + curInstanceId);
			if (routeId != null && StringUtils.isNotEmpty(routeId)) {
				// 根据路由获得下一节点名称
				WfRoute route = wfRouteDao.getRouteByRouteId(routeId);
				String nodeId = route.getPhaseId() + route.getNextNode();

				// 根据节点查询该节点的操作人员
				WfNode node = wfNodeDao.getNodeByNodeId(nodeId);
				userInfos.addAll(userInfoService.getListByRoleId(node.getRoleId()));
			}
			// 回退人员
			if (hisInstanceId != null && StringUtils.isNotEmpty(routeId)) {
				WfHisInstance hisInstance = wfOperation.getHisInstanceByInstanceId(hisInstanceId);
				userInfos.add(userInfoService.getUserInfoByUserId(hisInstance.getSignUserId()));
			}
			// 流转人员列表
			if (curInstanceId != null && StringUtils.isNotEmpty(curInstanceId)) {
				WfCurInstance curInstence = wfOperation.getInstanceByInstanceId(curInstanceId);
				// 根据节点查询该节点的操作人员
				WfNode node = wfNodeDao.getNodeByNodeId(curInstence.getNodeId());
				List<String> userIds = new ArrayList<String>() {
					{
						add(curInstence.getSignUserId());
					}
				};
				userInfos.addAll(userInfoService.getListByRoleId(node.getRoleId(), userIds));

			}
			map.put("userInfos", userInfos);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message",MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月4日 选择环节
	 */
	@At("/selectPhases")
	@Ok("jsp:/czsp/workflow/select_phases")
	public Map<String, Object> selectPhases(String phaseIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载链表
		List phases = wfPhaseDao.loadPhases();
		map.put("phaseIds", phaseIds);
		map.put("phases", phases);
		return map;
	}

	/**
	 * 全琛 2018年3月6日 查看流程实例
	 */
	@At("/showInstance/?")
	@Ok("jsp:/czsp/workflow/show_instance")
	@Fail("jsp:/czsp/common/fail")
	public Map<String, Object> showInstance(String planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载当前流程实例ID
		String curInstanceId = planInfoService.getPlanInfoByPlanId(planId).getInstanceId();
		// 加载当前流程实例
		WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(curInstanceId);
		if (curInstance == null)
			curInstance = new WfCurInstance();
		// 加载历史流程实例
		List<WfHisInstance> hisInstances = wfOperation.getHisInstanceByInstanceNo(curInstance.getInstanceNo());

		if (hisInstances != null) {
			// 防止hisInstances size为0的情况检测不到之前的环节
			int size = -1;
			// 遍历每一环节的历史流程
			while (size < hisInstances.size()) {
				size = hisInstances.size();
				UserOperation operation = null;
				if (size == 0)
					operation = userOperationService.getLatestOperation(curInstanceId, "'提交','特送'");
				else
					operation = userOperationService
							.getLatestOperation(hisInstances.get(hisInstances.size() - 1).getInstanceId(), "'提交','特送'");

				if (operation != null) {
					WfHisInstance instance = wfOperation.getHisInstanceByInstanceId(operation.getPreInstanceId());
					hisInstances.addAll(wfOperation.getHisInstanceByInstanceNo(instance.getInstanceNo()));
				}
			}
		}

		map.put("dicWfNode", DicUtil.getInstance().getDicMap().get(Constants.DIC_WF_NODE_NO));
		map.put("curInstance", curInstance);
		map.put("hisInstances", hisInstances);
		return map;
	}

}
