package czsp.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import czsp.MainSetup;
import czsp.common.util.MessageUtil;
import czsp.workflow.dao.WfNodeDao;
import czsp.workflow.dao.WfRouteDao;
import czsp.workflow.model.WfCurInstance;
import czsp.workflow.model.WfHisInstance;
import czsp.workflow.model.WfNode;
import czsp.workflow.model.WfRoute;
import czsp.workflow.model.view.VwfNodeDetail;

@IocBean
public class WFModule {

	@Inject
	private WfNodeDao wfNodeDao;

	@Inject
	private WfRouteDao wfRouteDao;

	@Inject
	private WFOperation wfOperation;
	
	final Log log = Logs.getLog(MainSetup.class);

	@At("/showList")
	@Ok("jsp:/czsp/workflow/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		// List<WfNode> wfNodes = ioc.get(WfNodeDao.class).getList();
		List<WfNode> wfNodes = wfNodeDao.getList();
		List<WfCurInstance> wfCurInstances = wfOperation.getCurInstanceList();
		List<WfHisInstance> wfHisInstances = wfOperation.getHisInstanceList();
		map.put("wfNodes", wfNodes);
		map.put("wfCurInstances", wfCurInstances);
		map.put("wfHisInstances", wfHisInstances);
		return map;
	}

	/**
	 * 全琛 2018年2月24日 添加流程实例
	 */
	@At("/createInstance")
	@Ok("json")
	public Map<String, Object> createInstance() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			wfOperation.initInstance();
		} catch (Exception e) {
			map.put("result", "fail");
		}
		map.put("result", "success");
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
	@Ok("jsp:/czsp/workflow/selectNextNode")
	public Map<String, Object> nextNode(String instanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获得实例
		WfCurInstance instance = wfOperation.getInstanceByInstanceId(instanceId);
		// 查询节点信息
		VwfNodeDetail nodeDetail = wfNodeDao.getNodeDetailByNodeId(instance.getNodeId());
		// 查询路本届点路由
		List<WfRoute> routes = wfRouteDao.getListByCurNode(nodeDetail.getWfCurNode(), nodeDetail.getWfCode());

		// 查询节点信息
		map.put("instance", instance);
		map.put("nodeDetail", nodeDetail);
		map.put("routes", routes);
		return map;
	}
	
	/**
	 * 全琛 2018年2月25日 提交操作
	 */
	@At("/submit")
	@Ok("json")
	public Map<String, Object> submit(String routeId,String instanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WfRoute route = wfRouteDao.getRouteByRouteId(routeId);
			WfCurInstance curInstance = wfOperation.getInstanceByInstanceId(instanceId);
			if ("1".equals(route.getIsTesong())) {
				System.out.println("特送节点");
				wfOperation.submitWF(route,curInstance,"1");
			} else {
				System.out.println("默认节点");
				wfOperation.submitWF(route,curInstance,"0");
			}
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}
	
	/**
	 * 全琛 2018年2月25日 回退操作
	 */
	@At("/retreat")
	@Ok("json")
	public Map<String, Object> retreat() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.print("回退");
		} catch (Exception e) {
			map.put("result", "fail");
		}
		map.put("result", "success");
		return map;
	}
}
