package org.xplatform.dubbo.consumer.stub;

import yunnex.common.core.dto.ApiResult;
import yunnex.common.mybatis.PageResult;
import yunnex.push.dto.ClientCache;
import yunnex.push.dto.ClientQueryDto;
import yunnex.push.dto.MessageQueryResultDto;
import yunnex.push.dto.MessageStatResultDto;
import yunnex.push.dto.PushMessageRequestDto;
import yunnex.push.dto.PushStateQueryDto;
import yunnex.push.dto.RelationQueryDto;
import yunnex.push.facade.PushInnerFacade;

import java.util.List;

public class PushInnerFacadeStub implements PushInnerFacade {
    private PushInnerFacade pushInnerFacade;

    public PushInnerFacadeStub(PushInnerFacade pushInnerFacade) {
        this.pushInnerFacade = pushInnerFacade;
    }

    @Override
    public ApiResult allMessagesExpiredProcess() {
        return null;
    }

    @Override
    public ApiResult clientCleanProcess() {
        return null;
    }

    @Override
    public ApiResult messageCleanProcess() {
        return null;
    }

    @Override
    public ApiResult<PageResult<MessageQueryResultDto>> getPushStat(PushStateQueryDto dto) {
        return null;
    }

    @Override
    public ApiResult<List<MessageStatResultDto>> getMessageStat(PushStateQueryDto dto) {
        return null;
    }

    @Override
    public ApiResult<PageResult<ClientQueryDto>> queryClientByPage(ClientQueryDto dto) {
        return null;
    }

    @Override
    public ApiResult<PageResult<RelationQueryDto>> queryRelationByPage(RelationQueryDto dto) {
        return null;
    }

    @Override
    public ApiResult tempClientExpiredProcess() {
        return null;
    }

    @Override
    public ApiResult syncExpireTimeFromCacheToDb() {
        return null;
    }

    @Override
    public ApiResult pushProcess(PushMessageRequestDto pushMessageRequestDto) {
        return null;
    }

    @Override
    public ApiResult<List<ClientCache>> listVerifyInfo(List<Long> clientIds) {
        if (clientIds.size() == 2) {
            ApiResult<List<ClientCache>> apiResult = ApiResult.build();
            return apiResult.error(ApiResult.RESULT_CODE_FAIL);
        }
        ApiResult<List<ClientCache>> listApiResult = pushInnerFacade.listVerifyInfo(clientIds);
        return listApiResult;
    }

    @Override
    public ApiResult<Boolean> deleteVerifyInfo(List<Long> clientIds) {
        return null;
    }

    @Override
    public ApiResult<List<ClientCache>> listUnAckMessage(List<Long> clientIds) {
        return null;
    }

    @Override
    public ApiResult<Boolean> deleteUnAckMessage(List<Long> clientIds) {
        return null;
    }

    @Override
    public ApiResult<Boolean> deleteCache(List<String> keys) {
        return null;
    }
}
