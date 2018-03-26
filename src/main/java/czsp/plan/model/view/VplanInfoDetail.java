package czsp.plan.model.view;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.View;

@View("V_PLAN_INFO_DETAIL")
public class VplanInfoDetail {
	@Name
	@Column(hump = true)
	@Readonly
	private String planId;

	@Column(value = "APP_ID")
	@Readonly
	private String appId;

	@Column(value = "PLAN_NAME")
	@Readonly
	private String planName;

	@Column(value = "INSTANCE_ID")
	@Readonly
	private String instanceId;

	@Column(value = "CREATE_TIME")
	private Date createTime;

	@Column(value = "CREATE_USER_ID")
	@Readonly
	private String createUserId;

	@Column(value = "CUR_NODE")
	@Readonly
	private String curNode;

	@Column(value = "CUR_PHASE")
	@Readonly
	private String curPhase;

	@Column(value = "STATUS")
	@Readonly
	private String status;

	@Column(value = "TOWN_NAME")
	@Readonly
	private String townName;

	@Column(value = "CREATE_USER_NAME")
	@Readonly
	private String createUserName;

	@Column(value = "QX_ID")
	@Readonly
	private String qxId;

	@Column(value = "PLAN_AREA")
	@Readonly
	private String planArea;

	/* 以下字段查询用 */
	private String createYear;

	public String getPlanId() {
		return planId;
	}

	public String getAppId() {
		return appId;
	}

	public String getPlanName() {
		return planName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public String getCurNode() {
		return curNode;
	}

	public String getCurPhase() {
		return curPhase;
	}

	public String getStatus() {
		return status;
	}

	public String getTownName() {
		return townName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public String getQxId() {
		return qxId;
	}

	public String getPlanArea() {
		return planArea;
	}

	public String getCreateYear() {
		return createYear;
	}

	public void setCreateYear(String createYear) {
		this.createYear = createYear;
	}

}
