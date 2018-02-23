package czsp;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;



@Modules(scanPackage = true)
@IocBy(args={"*org.nutz.ioc.loader.json.JsonLoader", "ioc/dao.js",   
	    "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","czsp.workflow"
        })
public class MainModule {
}
