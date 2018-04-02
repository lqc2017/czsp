package czsp.plan.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("PLAN_OPINION")
public class PlanOpinion {
	@Name
	@Prev(els = @EL("uuid(32)"))
	@Column(value = "OPINION_ID")
	private String opinionId;

	@Column(value = "NODE_ID")
	private String nodeId;

	@Column(value = "CREATE_BY")
	private String createBy;

	@Column(value = "CREATE_TIME")
	private Date createTime;

	@Column(value = "OPINION_CONTENT")
	private String opinionContent;

	@Column(value = "PLAN_ID")
	private String planId;

	@Column(value = "OP_TYPE")
	private String opType;

	@Column(value = "PHASE_ID")
	private String phaseId;

	@Column(value = "INSTANCE_ID")
	private String instanceId;

	@Column(value = "UPDATE_TIME")
	private Date updateTime;

	public String getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOpinionContent() {
		return opinionContent;
	}

	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
