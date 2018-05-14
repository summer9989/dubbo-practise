package org.xplatform.dubbo.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yunnex.common.core.dto.ApiResult;
import yunnex.push.dto.ClientCache;
import yunnex.push.facade.PushInnerFacade;

import java.util.List;

@Service
public class ConsumerService {
    @Autowired
    PushInnerFacade pushInnerFacade;

    public ApiResult<List<ClientCache>> testStub(List<Long> clientList) {
        ApiResult<List<ClientCache>> listApiResult = pushInnerFacade.listVerifyInfo(clientList);
        return listApiResult;
    }
}
