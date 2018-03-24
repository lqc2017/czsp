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

	@Column(value = "IS_FINISHED")
	private String isFinished;

	@Column(value = "TOWN_ID")
	private String townId;

	@Column(value = "TOWN_NAME")
	private String townName;

	@Column(value = "PLAN_AREA")
	private String planArea;

	@Column(value = "DESIGN_DEPARTMENT")
	private String designDepartment;

	@Column(value = "DESIGN_CONTACT_NAME")
	private String designContactName;

	@Column(value = "DESIGN_CONTACT_WAY")
	private String designContactWay;

	@Column(value = "QX_ID")
	private String qxId;

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

	public String getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getPlanArea() {
		return planArea;
	}

	public void setPlanArea(String planArea) {
		this.planArea = planArea;
	}

	public String getDesignDepartment() {
		return designDepartment;
	}

	public void setDesignDepartment(String designDepartment) {
		this.designDepartment = designDepartment;
	}

	public String getDesignContactName() {
		return designContactName;
	}

	public void setDesignContactName(String designContactName) {
		this.designContactName = designContactName;
	}

	public String getDesignContactWay() {
		return designContactWay;
	}

	public void setDesignContactWay(String designContactWay) {
		this.designContactWay = designContactWay;
	}

	public String getQxId() {
		return qxId;
	}

	public void setQxId(String qxId) {
		this.qxId = qxId;
	}

}
