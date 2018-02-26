package czsp;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;



@Modules(scanPackage = true)
@SetupBy(MainSetup.class)
@IocBy(args={"*org.nutz.ioc.loader.json.JsonLoader", "ioc/dao.js",   
	    "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","czsp"
        })
public class MainModule {
}
