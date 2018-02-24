package czsp.workflow.dao;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ioc.loader.json.JsonLoader;

import czsp.workflow.entity.DicWfNode;

@IocBean
public class DicWfNodeDao {
	Ioc ioc = new NutIoc(new JsonLoader("ioc/"));
	Dao dao = ioc.get(Dao.class,"dao");
	
	public List getList(){
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("NODE_ID");
		Pager pager = dao.createPager(1, 10);
		List list = dao.query(DicWfNode.class, cri, pager);
		return list;
	}
}
