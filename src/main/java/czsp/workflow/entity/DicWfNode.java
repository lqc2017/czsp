package czsp.workflow.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("DIC_WF_NODE")
public class DicWfNode {
	
	@Name
	private String nodeId;
	@Column(hump = true)
	private String nodeName;
	@Column(hump = true)
	private String phaseId;
	@Column(hump = true)
	private String wfCurNode;
	
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
	
}
