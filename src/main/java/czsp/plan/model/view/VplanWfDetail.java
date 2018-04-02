package czsp.plan.model.view;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.View;

@View("V_PLAN_WF_DETAIL")
public class VplanWfDetail {
	@Name
	@Readonly
	@Column(value = "PLAN_ID")
	private String planId;

	@Column(value = "PLAN_NAME")
	@Readonly
	private String planName;

	@Column(value = "CREATE_TIME")
	@Readonly
	private Date createTime;

	@Column(value = "INSTANCE_ID")
	@Readonly
	private String instanceId;

	@Column(value = "APP_ID")
	@Readonly
	private String appId;

	@Column(value = "INSTANCE_NO")
	@Readonly
	private String instanceNo;

	@Column(value = "CUR_NODE")
	@Readonly
	private String curNode;

	@Column(value = "CUR_PHASE")
	@Readonly
	private String curPhase;

	@Column(value = "STATUS")
	@Readonly
	private String status;

	@Column(value = "LAST_OP_USER")
	@Readonly
	private String lastOpUser;

	@Column(value = "LAST_OP_TIME")
	@Readonly
	private Date lastOpTime;

	@Column(value = "OPED_USERS")
	@Readonly
	private String opedUsers;

	@Column(value = "PHASES")
	@Readonly
	private String phases;

	@Column(value = "TODO_USER_ID")
	@Readonly
	private String todoUserId;

	@Column(value = "SIGN_USER_ID")
	@Readonly
	private String signUserId;

	@Column(value = "SIGN_USER_NAME")
	@Readonly
	private String signUserName;

	@Column(value = "IF_SIGN")
	@Readonly
	private String ifSign;

	@Column(value = "TOWN_NAME")
	@Readonly
	private String townName;

	@Column(value = "QX_ID")
	@Readonly
	private String qxId;

	/* 以下字段只用于查询，不负责映射 */
	private String createYear;

	public String getPlanId() {
		return planId;
	}

	public String getPlanName() {
		return planName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getAppId() {
		return appId;
	}

	public String getInstanceNo() {
		return instanceNo;
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

	public String getLastOpUser() {
		return lastOpUser;
	}

	public Date getLastOpTime() {
		return lastOpTime;
	}

	public String getOpedUsers() {
		return opedUsers;
	}

	public String getPhases() {
		return phases;
	}

	public String getTodoUserId() {
		return todoUserId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTodoUserId(String todoUserId) {
		this.todoUserId = todoUserId;
	}

	public String getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getIfSign() {
		return ifSign;
	}

	public String getTownName() {
		return townName;
	}

	public String getCreateYear() {
		return createYear;
	}

	public void setCreateYear(String createYear) {
		this.createYear = createYear;
	}

	public String getQxId() {
		return qxId;
	}

	public void setQxId(String qxId) {
		this.qxId = qxId;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getSignUserName() {
		return signUserName;
	}

	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}

}
