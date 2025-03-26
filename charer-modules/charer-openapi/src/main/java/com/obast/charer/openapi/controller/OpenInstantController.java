package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.openapi.dto.vo.OpenTopupVo;
import com.obast.charer.openapi.service.IOpenTopupService;
import com.obast.charer.qo.TopupQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-充值列表"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/instant")
public class OpenInstantController {

    @Autowired
    private IOpenTopupService openTopupService;

    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenTopupVo> list(@RequestBody @Validated PageRequest<TopupQueryBo> pageRequest) {
        pageRequest.getData().setState(TopupStateEnum.Successful);
        pageRequest.getData().setSource(TopupSourceEnum.Online);
        String userName = LoginHelper.getUserName();
        pageRequest.getData().setUserName(userName);
        return openTopupService.queryPage(pageRequest);
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenTopupVo getDetail(@RequestBody @Validated Request<TopupQueryBo> pageRequest) {
        return openTopupService.queryDetail(pageRequest.getData().getId());
    }
}