package czsp.workflow.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("WF_HIS_INSTANCE")
public class WfHisInstance {

	@Column(hump = true)
	private String instanceId;

	@Column(hump = true)
	private String instanceNo;

	@Column(hump = true)
	private String nodeId;

	@Column(hump = true)
	private Date createTime;

	@Column(hump = true)
	private Date finishTime;

	@Column(hump = true)
	private String userId;

	public WfHisInstance(WfCurInstance instance) {
		this.instanceId = instance.getInstanceId();
		this.instanceNo = instance.getInstanceNo();
		this.nodeId = instance.getNodeId();
		this.createTime = instance.getCreateTime();
		this.finishTime = new Date();
		this.userId = instance.getUserId();
	}

	public WfHisInstance() {

	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceNo() {
		return instanceNo;
	}

	public void setInstanceNo(String instanceNo) {
		this.instanceNo = instanceNo;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
