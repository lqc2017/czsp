package czsp.workflow.model.view;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.View;

@View("V_WF_NODE_DETAIL")
public class VwfNodeDetail {

	@Name
	@Column(hump = true)
	@Readonly
	private String nodeId;
	@Column(hump = true)
	@Readonly
	private String nodeName;
	@Column(hump = true)
	@Readonly
	private String phaseId;
	@Column(hump = true)
	@Readonly
	private String phaseName;
	@Column(hump = true)
	@Readonly
	private String wfCurNode;
	@Column(hump = true)
	@Readonly
	private String wfCode;
	@Column(value = "IS_START")
	@Readonly
	private String isStart;
	@Column(value = "IS_END")
	@Readonly
	private String isEnd;

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

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	public String getIsStart() {
		return isStart;
	}

	public String getIsEnd() {
		return isEnd;
	}

}
