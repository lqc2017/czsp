package czsp.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.user.dao.UserInfoDao;
import czsp.user.model.UserInfo;

@At("/user")
@IocBean
public class UserModule {

	@Inject
	private UserInfoDao userInfoDao;

	final Log log = Logs.getLog(MainSetup.class);

	@At("/showList")
	@Ok("jsp:/czsp/user/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserInfo> users = userInfoDao.getList();
		Map deptsMap = (HashMap)(DicUtil.getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());
		
		map.put("users", users);
		map.put("departments", departments);
		return map;
	}

	@At("/create")
	@Ok(">>:/user/showList")
	public Map<String, Object> create(@Param("..") UserInfo user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.debug("new instanceId:" + userInfoDao.addUser(user));
		} catch (Exception e) {
		}
		return map;
	}
}
