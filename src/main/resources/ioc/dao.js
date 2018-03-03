var ioc = {
    /* 
     * 数据库连接池 
     */  
    dataSource : {
        type : "com.mchange.v2.c3p0.ComboPooledDataSource",
        fields : {
            driverClass : "oracle.jdbc.driver.OracleDriver",
            jdbcUrl : "jdbc:oracle:thin:@localhost:1521:orcl",
            user : "czsp",
            password : "czsp"
        }   
    },  
    /* 
     * 这个配置很好理解， args 表示这个对象构造函数的参数。显然，下面的注入方式将调用 new NutDao(dataSource) 
     */  
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [ {
            refer : "dataSource"
        } ]
    },
    
    /*dicWfNodeDao : {
    	type : "czsp.workflow.dao.DicWfNodeDao"
    }*/
};