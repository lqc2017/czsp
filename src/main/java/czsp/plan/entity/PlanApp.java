package czsp.plan.entity;


import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("PLAN_APP")
public class PlanApp {
	@Name
	@Column(value = "APP_ID")
	private String appId;

	@Column(value = "INSTANCE_NO")
	private String instanceNo;

	@Column(value = "CUR_NODE")
	private String curNode;

	@Column(value = "CUR_PHASE")
	private String curPhase;

	@Column(value = "STATUS")
	private String status;

	@Column(value = "LAST_OP_USER")
	private String lastOpUser;

	@Column(value = "LAST_OP_TIME")
	private Date lastOpTime;

	@Column(value = "OPED_USERS")
	private String opedUsers;

	@Column(value = "PHASES")
	private String phases;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInstanceNo() {
		return instanceNo;
	}

	public void setInstanceNo(String instanceNo) {
		this.instanceNo = instanceNo;
	}

	public String getCurNode() {
		return curNode;
	}

	public void setCurNode(String curNode) {
		this.curNode = curNode;
	}

	public String getCurPhase() {
		return curPhase;
	}

	public void setCurPhase(String curPhase) {
		this.curPhase = curPhase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastOpUser() {
		return lastOpUser;
	}

	public void setLastOpUser(String lastOpUser) {
		this.lastOpUser = lastOpUser;
	}

	public Date getLastOpTime() {
		return lastOpTime;
	}

	public void setLastOpTime(Date lastOpTime) {
		this.lastOpTime = lastOpTime;
	}

	public String getOpedUsers() {
		return opedUsers;
	}

	public void setOpedUsers(String opedUsers) {
		this.opedUsers = opedUsers;
	}

	public String getPhases() {
		return phases;
	}

	public void setPhases(String phases) {
		this.phases = phases;
	}

}
