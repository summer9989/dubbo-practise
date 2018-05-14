package org.xplatform.dubbo.test;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboInvoker;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.junit.Before;
import org.junit.Test;
import org.xplatform.dubbo.utils.MavenDependencyUtil;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsumerWithInterfaceTest {
    private static final DubboProtocol PROTOCOL = DubboProtocol.getDubboProtocol();
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();
    String interfaceFullName;
    String methodName;
    String providerUrl;
    String dependencyLibLocation;
    List<Long> clientList;
    Class interfaceClass;
    Method method;

    @Before
    public void setUp() throws Exception {
        interfaceFullName = "yunnex.push.facade.PushInnerFacade";
        methodName = "listVerifyInfo";
        dependencyLibLocation = "D:\\WorkProgram\\project\\workspace_yunnex\\yunnex-push-service\\push-mod-notification\\pom.xml";
        addDependencyLib(dependencyLibLocation);

        interfaceClass = loader.loadClass(interfaceFullName);

        method = interfaceClass.getMethod(methodName, List.class);

        clientList = new ArrayList<>();
        clientList.add(1L);
        clientList.add(2L);
    }

    @Test
    public void testRegisterInvoke() throws Exception {
        providerUrl = "dubbo://192.168.10.210:1460/yunnex.push.facade.PushInnerFacade?anyhost=true&application=app-push-mod-notification&default.accepts=1000&default.loadbalance=leastactive&default.retries=0&default.serialization=hessian2&default.threadpool=cached&default.threads=1000&default.timeout=30000&default.version=1.0.0&dubbo=2.8.4&generic=false&interface=yunnex.push.facade.PushInnerFacade&methods=allMessagesExpiredProcess,messageCleanProcess,deleteUnAckMessage,pushProcess,getMessageStat,tempClientExpiredProcess,deleteVerifyInfo,getPushStat,listUnAckMessage,listVerifyInfo,queryRelationByPage,deleteCache,queryClientByPage,clientCleanProcess,syncExpireTimeFromCacheToDb&mock=yunnex.push.facade.mock.PushInnerFacadeErrorHandler&owner=xiaqiang&pid=1956&side=provider&timestamp=1525857164939";

        Object result = invoke(interfaceClass, method, providerUrl, clientList);
        System.out.println(result);
    }

    @Test
    public void testPerToPerInvoke() throws Exception {
        String providerAddress = "dubbo://127.0.0.1:1460";
        Map dubboParamMap = buildDubboParam(interfaceClass);
        com.alibaba.dubbo.common.URL url = com.alibaba.dubbo.common.URL.valueOf(providerAddress);
        url = url.setPath(interfaceFullName).addParameters(dubboParamMap);
        providerUrl = url.toString();
        Object result = invoke(interfaceClass, method, providerUrl, clientList);
        System.out.println(result);
    }

    public void addDependencyLib(String dependencyLibLocation) throws Exception {
        ArrayList<URL> urls = MavenDependencyUtil.analysisPOM(dependencyLibLocation);
        for (URL url : urls) {
            addURL(url);
        }
    }

    private Map buildDubboParam(Class interfaceClass) {
        Map<String, String> params = new HashMap<>();
        params.put("application", "test-client");
        params.put("side", "consumer");
        params.put("method", getMethodNames(interfaceClass));
        params.put("dubbo", "2.8.4");
        params.put("interface", interfaceClass.getName());
        params.put("pid", String.valueOf(ConfigUtils.getPid()));
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));

        params.put("default.version", "1.0.0");
        params.put("default.timeout", "30000");
        //        params.put("owner", "xiaqiang");
        //        params.put("default.check", "false");
        //        params.put("default.reference.filter", "activelimit");
        //        params.put("default.loadbalance", "gray");
        return params;
    }

    private String getMethodNames(Class interfaceClass) {
        Method[] methods = interfaceClass.getMethods();

        List<String> methodNames = new ArrayList<>(methods.length);
        for (Method method : methods) {
            methodNames.add(method.getName());
        }
        return methodNames.stream().collect(Collectors.joining(","));
    }

    /**
     * 为当前ClassLoader添加classPath(provider的interface工程)
     *
     * @param url
     * @throws Exception
     */
    private void addURL(URL url) throws Exception {
        Class[] parameters = new Class[] {URL.class};
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;
        Method method = sysclass.getDeclaredMethod("addURL", parameters);
        method.setAccessible(true);
        method.invoke(sysloader, new Object[] {url});
    }

    /**
     * 调用方法
     *
     * @param c
     * @param method
     * @param fullUrl
     * @param args
     * @return0
     * @throws Exception
     */
    private Object invoke(Class c, Method method, String fullUrl, Object... args) throws Exception {
        DubboInvoker<?> invoker = (DubboInvoker<?>) PROTOCOL.refer(c, com.alibaba.dubbo.common.URL.valueOf(fullUrl));
        if (invoker.isAvailable()) {
            Invocation inv = new RpcInvocation(method, args);
            Result ret = invoker.invoke(inv);
            PROTOCOL.destroy();
            return JSON.json(ret.getValue());
        }
        return null;
    }

}
