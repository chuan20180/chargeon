package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.qo.TopupQueryBo;
import com.obast.charer.system.dto.bo.TopupBo;
import com.obast.charer.system.dto.vo.topup.TopupVo;
import com.obast.charer.system.service.business.ITopupManagerService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录管理
 */
@Api(tags = {"充值记录"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/topup")
public class TopupController {

    @Autowired
    private ITopupManagerService topupManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:topup:list")
    @PostMapping("/list")
    public Paging<TopupVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<TopupQueryBo> pageRequest) {
        return topupManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "充值")
    @SaCheckPermission("business:topup:add")
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<TopupBo> bo) {
        topupManagerService.add(bo.getData());
    }

    @ApiOperation("导出列表")
    @Log(title = "充值记录", businessType = BusinessType.EXPORT)
    @SaCheckPermission("business:topup:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<TopupQueryBo> pageRequest, HttpServletResponse response) {
        List<TopupVo> list = topupManagerService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "充值记录", TopupVo.class, response);
    }
}
