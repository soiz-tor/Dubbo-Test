import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DubboTest {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(Level.ERROR);
        // 应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-test");
        application.setQosPort(22223);

        // 注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("nacos://192.168.220.128:8848");

        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("com.psyjournel.api.user.UserService");
        reference.setGeneric("true");

        // 获取远程服务代理
        GenericService genericService = reference.get();

        // 调用远程方法
        Object result = genericService.$invoke("loginService", new String[] {"java.lang.String"}, new Object[] {"world"});
        Map<String, Object> resultMap = (HashMap<String, Object>) result;
        JSONObject json = new JSONObject(resultMap);
        System.out.println(json);
    }
}
