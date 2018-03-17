package czsp.user.service;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.user.dao.UserOperationDao;
import czsp.user.model.UserOperation;

@IocBean
public class UserOperationService {
	@Inject
	UserOperationDao userOperationDao;

	/**
	 * 全琛 2018年3月17日
	 */
	public void addOperation(UserOperation opertaion) {
		userOperationDao.addOperation(opertaion);
	}

	/**
	 * 全琛 2018年3月17日
	 */
	public UserOperation getLatestOperation(String instanceId, String opTypes) {
		return userOperationDao.getLatestOperation(instanceId, opTypes);
	}
}
