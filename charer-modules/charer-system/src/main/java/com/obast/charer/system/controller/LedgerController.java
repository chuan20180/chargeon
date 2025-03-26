package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.system.dto.vo.ledger.LedgerVo;
import com.obast.charer.system.service.business.ILedgerManagerService;
import com.obast.charer.qo.LedgerQueryBo;
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
 * @ Description：分润记录
 */
@Api(tags = {"分润记录"})
@Slf4j
@RestController
@RequestMapping("/admin/ledger")
public class LedgerController {

    @Autowired
    private ILedgerManagerService ledgerManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:ledger:list")
    @PostMapping("/list")
    public Paging<LedgerVo> list(@Validated @RequestBody PageRequest<LedgerQueryBo> pageRequest) {

        log.debug("LoginUser: {}", LoginHelper.getLoginUser());
        return ledgerManagerService.queryPageList(pageRequest);
    }


    @ApiOperation("导出列表")
    @Log(title = "分润记录", businessType = BusinessType.EXPORT)
    @SaCheckPermission("business:ledger:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<LedgerQueryBo> pageRequest, HttpServletResponse response) {
        List<LedgerVo> list = ledgerManagerService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "分润记录", LedgerVo.class, response);
    }
}
