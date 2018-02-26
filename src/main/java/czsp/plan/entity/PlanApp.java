package czsp.plan.entity;

import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Table;

@Table("PLAN_APP")
public class PlanApp {
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String appId;

	@Column(hump = true)
	private String instanceNo;

	@Column(hump = true)
	private String curNode;

	@Column(hump = true)
	private String curPhase;

	@Column(hump = true)
	private String status;

	@Column(hump = true)
	private String lastOpUser;

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

}
