package czsp.common.bean;

import java.util.List;

import org.nutz.dao.pager.Pager;

public class Pagination<T> {
	private Pager pager;

	private List<T> list;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
