package com.obast.charer.system.controller;

import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.CacheConstants;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.model.UserOnlineDTO;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.utils.StreamUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.core.BaseController;

import com.obast.charer.system.dto.SysUserOnline;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：用户在线监控
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/admin/monitor/online")
@Api(tags = "用户在线监控")
public class MonitorOnlineController extends BaseController {

    /**
     * 获取在线用户监控列表
     */
    @ApiOperation("获取在线用户监控列表")
    @SaCheckPermission("monitor:online:list")
    @PostMapping("/list")
    public Paging<SysUserOnline> list(@RequestBody @Validated Request<SysUserOnline> request) {
        SysUserOnline data = request.getData();
        String ipaddr = data.getIpaddr();
        String userName = data.getUserName();
        // 获取所有未过期的 token
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        List<UserOnlineDTO> userOnlineDTOList = new ArrayList<>();
        for (String key : keys) {
            String token = StringUtils.substringAfterLast(key, ":");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < -1) {
                continue;
            }
            userOnlineDTOList.add(RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token));
        }
        if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                    StringUtils.equals(ipaddr, userOnline.getIpaddr()) &&
                            StringUtils.equals(userName, userOnline.getUserName())
            );
        } else if (StringUtils.isNotEmpty(ipaddr)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                    StringUtils.equals(ipaddr, userOnline.getIpaddr())
            );
        } else if (StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                    StringUtils.equals(userName, userOnline.getUserName())
            );
        }
        Collections.reverse(userOnlineDTOList);
        userOnlineDTOList.removeAll(Collections.singleton(null));
        List<SysUserOnline> userOnlineList = BeanUtil.copyToList(userOnlineDTOList, SysUserOnline.class);
        return new Paging<>(userOnlineList.size(), userOnlineList);
    }

    /**
     * 强退用户
     */
    @ApiOperation("强退用户")
    @SaCheckPermission("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @PostMapping("/kickoutByTokenValue")
    public void forceLogout(@RequestBody @Validated Request<String> bo) {
        StpUtil.kickoutByTokenValue(bo.getData());
    }
}
