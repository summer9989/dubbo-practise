package org.xplatform.dubbo.test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConsumerGenericTest {
    private String registryProtocol;
    private String address;
    private String protocal;
    private String interfaceFullName;
    private String methodName;
    private Integer timeout = 5000;
    private List<String> parameterTypesList = new ArrayList<>();
    private List<Object> parameterValuesList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        registryProtocol = null;
        address = "127.0.0.1:1460";
        protocal = "dubbo";
        interfaceFullName = "yunnex.push.facade.PushInnerFacade";
        methodName = "listVerifyInfo";
    }

    @Test
    public void testGeneric() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("generic-test");

        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);

        RegistryConfig registry = null;

        if (registryProtocol != null) {
            registry = new RegistryConfig();
            if (registryProtocol != null) {
                registry.setProtocol(registryProtocol);
            }
            registry.setAddress(address);
            reference.setProtocol(protocal);
            reference.setRegistry(registry);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(protocal).append("://").append(address);
            reference.setUrl(sb.toString());
        }

        reference.setVersion("1.0.0");
        reference.setInterface(interfaceFullName);
        reference.setTimeout(timeout);
        reference.setGeneric(true);

        GenericService genericService = (GenericService) reference.get();

        List<Long> clientIds = Lists.newArrayList();
        clientIds.add(1L);
        clientIds.add(2L);
        parameterValuesList.add(clientIds);
        parameterTypesList.add("java.util.List");

        String[] parameterTypes = parameterTypesList.toArray(new String[parameterTypesList.size()]);
        Object[] parameterValues = parameterValuesList.toArray(new Object[parameterValuesList.size()]);

        Object result = null;
        result = genericService.$invoke(methodName, parameterTypes, parameterValues);
        System.out.println(result);
    }
}
