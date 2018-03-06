package czsp.plan.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("PLAN_INFO")
public class PlanInfo {
	@Name
	@Prev(els = @EL("uuid(32)"))
	@Column(value = "PLAN_ID")
	private String planId;

	@Prev(els = @EL("uuid(32)"))
	@Column(value = "APP_ID")
	private String appId;

	@Column(value = "PLAN_NAME")
	private String planName;

	@Column(value = "INSTANCE_ID")
	private String instanceId;

	@Column(value = "CREATE_TIME")
	private Date createTime;

	@Column(value = "CREATE_USER_ID")
	private String createUserId;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

}
