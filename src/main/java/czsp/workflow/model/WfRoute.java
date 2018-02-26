package czsp.workflow.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("WF_ROUTE")
public class WfRoute {

	@Name
	@Column(value="ROUTE_ID")
	private String routeId;

	@Column(value="WF_CODE")
	private String wfCode;

	@Column(value="CUR_NODE")
	private String curNode;

	@Column(value="NEXT_NODE")
	private String nextNode;

	@Column(value="PRE_NODE")
	private String preNode;

	@Column(value="IS_TESONG")
	private String isTesong;

	@Column(value="DISPLAY_ORDER")
	private String displayOrder;

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getwfCode() {
		return wfCode;
	}

	public void setwfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	public String getCurNode() {
		return curNode;
	}

	public void setCurNode(String curNode) {
		this.curNode = curNode;
	}

	public String getNextNode() {
		return nextNode;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public String getPreNode() {
		return preNode;
	}

	public void setPreNode(String preNode) {
		this.preNode = preNode;
	}

	public String getIsTesong() {
		return isTesong;
	}

	public void setIsTesong(String isTesong) {
		this.isTesong = isTesong;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

}
