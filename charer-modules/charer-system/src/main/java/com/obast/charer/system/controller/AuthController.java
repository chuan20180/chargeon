package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.constant.GlobalConstants;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.utils.ReflectUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.enums.CaptchaType;
import com.obast.charer.system.config.properties.CaptchaProperties;
import com.obast.charer.system.dto.*;
import com.obast.charer.system.service.system.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "认证")
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/auth")
public class AuthController {

    private final SysLoginService loginService;

    private final CaptchaProperties captchaProperties;

    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public LoginVo login(@Validated @RequestBody Request<LoginBody> body) {
        LoginBody loginBody = body.getData();
        LoginVo loginVo = new LoginVo();
        // 生成令牌
        String token = loginService.login(
                loginBody.getUsername(), loginBody.getPassword(),
                loginBody.getCode(), loginBody.getUuid());
        loginVo.setToken(token);
        return loginVo;
    }

    /**
     * 退出登录
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public void logout() {
        loginService.logout();

    }

    /**
     * 生成验证码
     */
    @ApiOperation(value = "生成验证码")
    @PostMapping("/code")
    public CaptchaVo getCode() {
        CaptchaVo captchaVo = new CaptchaVo();
        boolean captchaEnabled = captchaProperties.getEnable();
        if (!captchaEnabled) {
            captchaVo.setCaptchaEnabled(false);
            return captchaVo;
        }
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;
        // 生成验证码
        CaptchaType captchaType = captchaProperties.getType();
        boolean isMath = CaptchaType.MATH == captchaType;
        Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
        CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
        AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());
        captcha.setGenerator(codeGenerator);
        captcha.createCode();
        String code = captcha.getCode();
        if (isMath) {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
            code = exp.getValue(String.class);
        }
        RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        captchaVo.setUuid(uuid);
        captchaVo.setImg(captcha.getImageBase64());
        return captchaVo;
    }
}
