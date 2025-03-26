package com.obast.charer.openapi.controller;

import com.obast.charer.common.api.Request;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.openapi.dto.bo.*;
import com.obast.charer.openapi.dto.vo.AccountLoginVo;
import com.obast.charer.openapi.dto.vo.LoginCodeVo;
import com.obast.charer.openapi.dto.vo.OpenLoginVo;
import com.obast.charer.openapi.dto.vo.OpenSysCountryVo;
import com.obast.charer.openapi.service.IOpenSysCountryService;
import com.obast.charer.openapi.service.OpenBaseService;
import com.obast.charer.qo.SysCountryQueryBo;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"openapi-基础"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/openapi/v1/")
public class OpenAuthController extends BaseController {

    @Autowired
    private OpenBaseService openBaseService;

    @Autowired
    private IOpenSysCountryService openSysCountryService;

    @SaIgnore
    @ApiOperation(value = "token获取", notes = "token获取", httpMethod = "POST")
    @PostMapping("/getToken")
    public Response<?> getToken(@RequestBody @Validated Request<TokenVerifyBo> request) {
       return Response.success(openBaseService.getToken(request.getData()));
    }

    @SaIgnore
    @ApiOperation(value = "手机号登陆", notes = "手机号登陆", httpMethod = "POST")
    @PostMapping("/mobileLogin")
    public Response<?> mobileLogin(@RequestBody Request<MobileLoginBo> request) {
        return Response.success(openBaseService.mobileLogin(request.getData()));
    }

    @SaIgnore
    @ApiOperation(value = "手机号登陆验证码", notes = "手机号登陆验证码", httpMethod = "POST")
    @PostMapping("/mobileCode")
    public Response<?> mobileCode(@RequestBody Request<MobileCodeBo> request) {
        LoginCodeVo loginCodeVo = openBaseService.mobileCode(request.getData());
        return Response.success(loginCodeVo);
    }

    @SaIgnore
    @ApiOperation(value = "手机号登陆用户信息", notes = "手机号登陆用户信息", httpMethod = "POST")
    @PostMapping("/mobileInfo")
    public Response<?> mobileInfo(@RequestBody Request<MobileInfoBo> request) {
        return Response.success(openBaseService.mobileInfo(request.getData()));
    }

    @SaIgnore
    @ApiOperation(value = "账号登陆", notes = "账号登陆", httpMethod = "POST")
    @PostMapping("/accountLogin")
    public Response<?> accountLogin(@RequestBody Request<AccountLoginBo> request) {
        AccountLoginVo accountLoginVo = openBaseService.accountLogin(request.getData());
        log.debug("微账号登陆结果: {}", accountLoginVo);
        return Response.success(accountLoginVo);
    }

    @SaIgnore
    @ApiOperation(value = "微信登陆", notes = "微信登陆", httpMethod = "POST")
    @PostMapping("/wxlogin")
    public Response<?> wxlogin(@RequestBody Request<WxLoginBo> request) {
        OpenLoginVo openLoginVo = openBaseService.wxLogin(request.getData());
        log.debug("微信登陆结果: {}", openLoginVo);
        return Response.success(openLoginVo);
    }

    @SaIgnore
    @ApiOperation(value = "微信登陆授权", notes = "微信登陆授权", httpMethod = "POST")
    @PostMapping("/wxauthorize")
    public Response<?> wxauthorize(@RequestBody Request<WxAuthorizeBo> request) {
        return Response.success(openBaseService.wxAuthorize(request.getData()));
    }

    @SaIgnore
    @ApiOperation(value = "国家列表", notes = "国家列表", httpMethod = "POST")
    @PostMapping("/country")
    public List<OpenSysCountryVo> queryPageList() {
        SysCountryQueryBo queryBo = new SysCountryQueryBo();
        queryBo.setStatus(EnableStatusEnum.Enabled);
        return openSysCountryService.queryList(queryBo);
    }
}