package czsp.plan.dao;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.plan.entity.PlanInfo;

@IocBean
public class PlanInfoDao {

	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().desc("CREATE_TIME");
		List list = dao.query(PlanInfo.class, cri);
		return list;
	}

	public PlanInfo add(PlanInfo planInfo) {
		// 初始化创建信息
		planInfo.setCreateTime(new Date());
		return dao.insert(planInfo);
	}

}
