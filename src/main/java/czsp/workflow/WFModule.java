package czsp.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import czsp.workflow.dao.DicWfNodeDao;
import czsp.workflow.entity.DicWfNode;

@IocBean
public class WFModule {
	
	@Inject
	private DicWfNodeDao dicWfNodeDao;

	@At("/showList")
	@Ok("jsp:/czsp/workflow/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		//List<DicWfNode> wfNodes = ioc.get(DicWfNodeDao.class).getList();
		List<DicWfNode> wfNodes = dicWfNodeDao.getList();
		map.put("wfNodes", wfNodes);
		return map;
	}
	
	/*
	添加流程实例
	@At("/createInstance")
	@Ok("json")
	public Map<String,Object> createInstance() {
		Map<String,Object> map = new HashMap<String,Object>();
		wfOperation.initInstance();
		map.put("result", "success");
		return map;
	}*/
}
