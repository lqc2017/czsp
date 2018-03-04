package czsp.user.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import czsp.MainSetup;
import czsp.user.model.UserOperation;

@IocBean
public class UserOperationDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");
	
	final Log log = Logs.getLog(MainSetup.class);

	public void addOperation(UserOperation opertaion) {
		dao.insert(opertaion);
	}

	/**
	 * 全琛 2018年2月26日 根据提交方式查询最近一次相关操作
	 */
	public UserOperation getLatestOperation(String instanceId, String opTypes) {
		return dao.fetch(UserOperation.class,
				Cnd.where("instanceId", "=", instanceId).and("opType", "in", opTypes).desc("opCreateTime"));
	}
}
