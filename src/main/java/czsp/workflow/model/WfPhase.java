package czsp.workflow.model;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("WF_Phase")
public class WfPhase {
	@Name
	@Column(value = "PHASE_ID")
	private String phaseId;

	@Column(value = "PHASE_NAME")
	private String phaseName;

	@Column(value = "PRE_PHASE_ID")
	private String prePhaseId;

	@Column(value = "NEXT_PHASE_ID")
	private String nextPhaseId;

	@Column(value = "IS_START")
	private String isStart;

	@Column(value = "IS_END")
	private String isEnd;

	@Column(value = "WF_CODE")
	private String wfCode;

	private List<WfNode> nodeList;

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getPrePhaseId() {
		return prePhaseId;
	}

	public void setPrePhaseId(String prePhaseId) {
		this.prePhaseId = prePhaseId;
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

	public String getNextPhaseId() {
		return nextPhaseId;
	}

	public void setNextPhaseId(String nextPhaseId) {
		this.nextPhaseId = nextPhaseId;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	public List<WfNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<WfNode> nodeList) {
		this.nodeList = nodeList;
	}

}
