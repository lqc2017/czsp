package czsp.workflow;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ioc.loader.json.JsonLoader;

@IocBean(name="wfOperation")
public class WFOperation {
	Ioc ioc = new NutIoc(new JsonLoader("ioc/"));
	Dao dao = ioc.get(Dao.class,"dao");
	
	public void initInstance(){
		
	}
	
}
