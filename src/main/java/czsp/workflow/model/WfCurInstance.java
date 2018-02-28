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
	private String todoUserId;

	@Column(hump = true)
	private String signUserId;

	public WfCurInstance() {
	}

	/**
	 * @param instanceId
	 *            实例id
	 * @param instanceNo
	 *            实例编号一个编号对应一个环节
	 * @param nodeId
	 *            节点id
	 * @param ifRetrieve
	 *            是否可回收
	 * @param ifSign
	 *            是否已签收
	 * @param ifValid
	 *            是否合法
	 * @param createTime
	 *            创建时间
	 * @param todoUserId
	 *            待办人id
	 * @param signUserId
	 *            签收人id
	 */
	public WfCurInstance(String instanceId, String instanceNo, String nodeId, String ifRetrieve, String ifSign,
			String ifValid, Date createTime, String todoUserId, String signUserId) {
		super();
		this.instanceId = instanceId;
		this.instanceNo = instanceNo;
		this.nodeId = nodeId;
		this.ifRetrieve = ifRetrieve;
		this.ifSign = ifSign;
		this.ifValid = ifValid;
		this.createTime = createTime;
		this.todoUserId = todoUserId;
		this.signUserId = signUserId;
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

	public String getTodoUserId() {
		return todoUserId;
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

}
