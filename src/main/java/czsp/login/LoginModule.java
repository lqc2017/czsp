package czsp.login;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
public class LoginModule {
	@At("/login")
	@Ok("jsp:/czsp/login")
	public Map<String, Object> dicManage() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
}
