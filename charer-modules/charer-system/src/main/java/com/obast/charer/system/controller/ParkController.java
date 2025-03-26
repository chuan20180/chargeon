package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.vo.park.ParkVo;
import com.obast.charer.system.service.business.IParkManagerService;
import com.obast.charer.qo.ParkQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：停车记录
 */
@Api(tags = {"停车记录"})
@Slf4j
@RestController
@RequestMapping("/admin/park")
public class ParkController {

    @Autowired
    private IParkManagerService parkManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:park:list")
    @PostMapping("/list")
    public Paging<ParkVo> list(@Validated @RequestBody PageRequest<ParkQueryBo> pageRequest) {
        return parkManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:park:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return parkManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:park:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return parkManagerService.batchDelete(request.getData());
    }

    @ApiOperation("导出列表")
    @Log(title = "停车记录", businessType = BusinessType.EXPORT)
    @SaCheckPermission("business:park:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<ParkQueryBo> pageRequest, HttpServletResponse response) {
        List<ParkVo> list = parkManagerService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "停车记录", ParkVo.class, response);
    }
}