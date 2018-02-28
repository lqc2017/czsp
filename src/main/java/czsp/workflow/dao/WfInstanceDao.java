package czsp.workflow.dao;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.workflow.model.WfCurInstance;
import czsp.workflow.model.WfHisInstance;

@IocBean
public class WfInstanceDao {
	protected Ioc ioc = Mvcs.getIoc();
	protected Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年2月24日 创建流程
	 */
	public void initInstance(String userId) {
		WfCurInstance curInstance = new WfCurInstance();
		curInstance.setIfSign("0");
		curInstance.setIfRetrieve("1");
		curInstance.setIfValid("1");
		curInstance.setCreateTime(new Date());
		curInstance.setNodeId("110100");
		dao.insert(curInstance);
	}

	/**
	 * 全琛 2018年2月24日 获取流转中实例列表
	 */
	public List getCurInstanceList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().desc("CREATE_TIME");
		List list = dao.query(WfCurInstance.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年2月25日 获取已提交的实例列表
	 */
	public List<WfHisInstance> getHisInstanceList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().desc("FINISH_TIME");
		List list = dao.query(WfHisInstance.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年2月24日 删除实例
	 */
	public void deleteInstance(String instanceId) {
		dao.delete(WfCurInstance.class, instanceId);
	}

	/**
	 * 全琛 2018年2月24日 根据主键获得当前实例
	 */
	public WfCurInstance getInstanceByInstanceId(String instanceId) {
		return dao.fetch(WfCurInstance.class, instanceId);
	}
	
	
	/**
	 * 全琛
	 * 2018年2月26日
	 * 根据主键获得历史流程记录
	 */
	public WfHisInstance getHisInstanceByInstanceId(String instanceId) {
		return dao.fetch(WfHisInstance.class, Cnd.where("instanceId","=",instanceId));
	}

	/**
	 * 全琛 2018年2月26日 根据前驱节点获得历史流程记录
	 */
	public WfHisInstance getLatestHisInstanceByPreNodes(List<String> preNodes) {
		return dao.fetch(WfHisInstance.class, Cnd.where("nodeId", "in", preNodes).orderBy("finishTime", "desc"));
	}
}
