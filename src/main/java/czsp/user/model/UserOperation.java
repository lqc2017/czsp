package czsp.user.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table("USER_OPERATION")
public class UserOperation {
	@Column(value = "OP_TYPE")
	private String opType;

	@Column(value = "OP_CREATE_TIME")
	private Date opCreateTime;

	@Column(value = "USER_ID")
	private String userId;

	@Column(value = "NODE_ID")
	private String nodeId;

	@Column(value = "PRE_INSTANCE_ID")
	private String preInstanceId;

	@Column(value = "INSTANCE_ID")
	private String instanceId;

	public UserOperation() {
	}

	public UserOperation(String opType, Date opCreateTime, String userId, String nodeId, String preInstanceId,
			String instanceId) {
		this.opType = opType;
		this.opCreateTime = opCreateTime;
		this.userId = userId;
		this.nodeId = nodeId;
		this.preInstanceId = preInstanceId;
		this.instanceId = instanceId;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Date getOpCreateTime() {
		return opCreateTime;
	}

	public void setOpCreateTime(Date opCreateTime) {
		this.opCreateTime = opCreateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getPreInstanceId() {
		return preInstanceId;
	}

	public void setPreInstanceId(String preInstanceId) {
		this.preInstanceId = preInstanceId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

}
