package czsp.workflow.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("WF_NODE")
public class WfNode {

	@Name
	@Column(value = "NODE_ID")
	private String nodeId;
	@Column(value = "NODE_NAME")
	private String nodeName;
	@Column(value = "PHASE_ID")
	private String phaseId;
	@Column(value = "WF_CUR_NODE")
	private String wfCurNode;
	@Column(value = "IS_START")
	private String isStart;
	@Column(value = "IS_END")
	private String isEnd;
	@Column(value = "ROLE_ID")
	private String roleId;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getWfCurNode() {
		return wfCurNode;
	}

	public void setWfCurNode(String wfCurNode) {
		this.wfCurNode = wfCurNode;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
