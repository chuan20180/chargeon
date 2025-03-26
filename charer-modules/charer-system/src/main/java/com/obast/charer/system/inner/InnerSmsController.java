package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.system.operate.ISmsOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：内部短信
 */
@Slf4j
@Api(tags = {"内部短信"})
@RestController
@RequestMapping("/inner/sms")
public class InnerSmsController {

    @Autowired
    private ISmsOperateService smsOperateService;

    @ApiOperation("发送验证码短信")
    @PostMapping("/sendVerificationCode")
    public Response<?> sendVerificationCode(@RequestParam("type") String type, @RequestParam("mobile") String mobile) {
        return Response.success(smsOperateService.sendVerifyCode(type, mobile));
    }

    @ApiOperation("验证验证码")
    @PostMapping("/verifyVerificationCode")
    public Response<?> verifyVerificationCode(@RequestParam("type") String type, @RequestParam("mobile") String mobile, @RequestParam("code") String code) {
        return Response.success(smsOperateService.verifyVerificationCode(type, mobile, code));
    }
}
