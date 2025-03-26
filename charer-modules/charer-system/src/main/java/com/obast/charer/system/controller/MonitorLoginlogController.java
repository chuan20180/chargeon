package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.GlobalConstants;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.common.web.core.BaseController;

import com.obast.charer.system.dto.bo.SysLoginInfoBo;
import com.obast.charer.system.dto.vo.SysLoginInfoVo;
import com.obast.charer.system.service.system.ISysLoginInfoService;
import com.obast.charer.qo.SysLoginInfoQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/monitor/loginlog")
@Api(tags = "系统访问记录")
public class MonitorLoginlogController extends BaseController {

    private final ISysLoginInfoService logininforService;

    /**
     * 获取系统访问记录列表
     */

    @ApiOperation("获取系统访问记录列表")
    @SaCheckPermission("monitor:loginlog:list")
    @PostMapping("/list")
    public Paging<SysLoginInfoVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<SysLoginInfoBo> query) {
        return logininforService.findAll(query);
    }

    /**
     * 导出系统访问记录列表
     */
    @ApiOperation("获取系统访问记录列表")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:loginlog:export")
    @PostMapping("/export")
    public void export(SysLoginInfoQueryBo logininfor, HttpServletResponse response) {
        List<SysLoginInfoVo> list = logininforService.queryList(logininfor);
        ExcelUtil.exportExcel(list, "登录日志", SysLoginInfoVo.class, response);
    }

    /**
     * 批量删除登录日志

     */
    @ApiOperation("批量删除登录日志")
    @SaCheckPermission("monitor:loginlog:delete")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@RequestBody @Validated Request<List<String>> bo) {
        logininforService.deleteLogininforByIds(bo.getData());
    }

    /**
     * 清理系统访问记录
     */
    @ApiOperation("清理系统访问记录")
    @SaCheckPermission("monitor:loginlog:delete")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    public void clean() {
        return;
    }

    @ApiOperation("账户解锁")
    @SaCheckPermission("monitor:loginlog:unlock")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping("/unlockByUserName")
    public void unlock(@Validated @RequestBody Request<String> bo) {
        String loginName = GlobalConstants.PWD_ERR_CNT_KEY + bo.getData();
        if (Boolean.TRUE.equals(RedisUtils.hasKey(loginName))) {
            RedisUtils.deleteObject(loginName);
        }
    }
}
