package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.common.web.core.BaseController;

import com.obast.charer.system.dto.bo.SysOperLogBo;
import com.obast.charer.system.dto.vo.SysOperLogVo;
import com.obast.charer.system.service.system.ISysOperLogService;
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
@RequestMapping("/admin/monitor/operlog")
@Api(tags = "操作日志记录")
public class MonitorOperlogController extends BaseController {

    private final ISysOperLogService operLogService;

    /**
     * 获取操作日志记录列表
     */
    @ApiOperation("获取操作日志记录列表")
    @SaCheckPermission("monitor:operlog:list")
    @PostMapping("/list")
    public Paging<SysOperLogVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<SysOperLogBo> query) {
        return operLogService.selectPageOperLogList(query);
    }

    /**
     * 导出操作日志记录列表
     */
    @ApiOperation("导出操作日志记录列表")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:operlog:export")
    @PostMapping("/export")
    public void export(SysOperLogBo operLog, HttpServletResponse response) {
        List<SysOperLogVo> list = operLogService.selectOperLogList(operLog);
        ExcelUtil.exportExcel(list, "操作日志", SysOperLogVo.class, response);
    }

    /**
     * 批量删除操作日志记录
     */
    @ApiOperation("批量删除操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monitor:operlog:delete")
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<List<String>> bo) {
        List<String> operIds = bo.getData();
        operLogService.deleteOperLogByIds(operIds);
    }

    /**
     * 清理操作日志记录
     */
    @ApiOperation("清理操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @SaCheckPermission("monitor:operlog:delete")
    @PostMapping("/clean")
    public void clean() {
        operLogService.cleanOperLog();
    }
}
