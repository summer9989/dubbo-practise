package org.xplatform.dubbo.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xplatform.dubbo.consumer.service.ConsumerService;
import yunnex.common.core.dto.ApiResult;
import yunnex.push.dto.ClientCache;
import yunnex.push.facade.PushInnerFacade;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StubTest {
    @Autowired
    ConsumerService consumerService;

    @Autowired
    PushInnerFacade pushInnerFacade;

    ApiResult apiResult;

    @Before
    public void setUp() throws Exception {
        apiResult = ApiResult.build("success");
        when(pushInnerFacade.listVerifyInfo(anyList())).thenReturn(apiResult);
    }

    @Test
    public void testStub() {
        List<Long> clientList = new ArrayList<>();
        clientList.add(1L);
        clientList.add(2L);
        ApiResult<List<ClientCache>> listApiResult = consumerService.testStub(clientList);
        Assert.assertEquals(ApiResult.RESULT_CODE_FAIL, listApiResult.getCode());
    }

    @Test
    public void testMock() {
        List<Long> clientList = new ArrayList<>();
        clientList.add(1L);
        clientList.add(2L);
        ApiResult<List<ClientCache>> listApiResult = consumerService.testStub(clientList);
        Assert.assertEquals(apiResult.getCode(), listApiResult.getCode());
    }
}
