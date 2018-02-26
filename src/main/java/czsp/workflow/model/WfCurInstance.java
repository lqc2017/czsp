package czsp.workflow.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("WF_CUR_INSTANCE")
public class WfCurInstance {

	@Name
	@Column(hump = true)
	@Prev(els = @EL("uuid(32)"))
	private String instanceId;

	@Column(hump = true)
	@Prev(els = @EL("uuid(32)"))
	private String instanceNo;

	@Column(hump = true)
	private String nodeId;

	@Column(hump = true)
	private String ifRetrieve;

	@Column(hump = true)
	private String ifSign;

	@Column(hump = true)
	private String ifValid;

	@Column(hump = true)
	private Date createTime;

	@Column(hump = true)
	private String userId;

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

	public String getIfRetrieve() {
		return ifRetrieve;
	}

	public void setIfRetrieve(String ifRetrieve) {
		this.ifRetrieve = ifRetrieve;
	}

	public String getIfSign() {
		return ifSign;
	}

	public void setIfSign(String ifSign) {
		this.ifSign = ifSign;
	}

	public String getIfValid() {
		return ifValid;
	}

	public void setIfValid(String ifValid) {
		this.ifValid = ifValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}