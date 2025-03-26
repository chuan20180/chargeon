package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.LedgerSettleDealerPaidBo;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleDealerVo;
import com.obast.charer.system.service.business.ILedgerSettleDealerManagerService;
import com.obast.charer.qo.LedgerSettleDealerQueryBo;
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
 * @ Description：分成结算列表管理
 */
@Api(tags = {"分成结算列表管理"})
@Slf4j
@RestController
@RequestMapping("/admin/ledgerSettleDealer")
public class LedgerSettleDealerController {

    @Autowired
    private ILedgerSettleDealerManagerService ledgerSettleDealerManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:ledger_settle_dealer:list")
    @PostMapping("/list")
    public Paging<LedgerSettleDealerVo> queryPageList(@Validated @RequestBody PageRequest<LedgerSettleDealerQueryBo> pageRequest) {
        return ledgerSettleDealerManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "付款", notes = "付款", httpMethod = "POST")
    @SaCheckPermission("business:ledger_settle_dealer:paid")
    @PostMapping("/paid")
    public void paid(@Validated @RequestBody PageRequest<LedgerSettleDealerPaidBo> request) {
        LedgerSettleDealerPaidBo bo = request.getData();
        ledgerSettleDealerManagerService.paid(bo.getId(), bo.getNote());
    }

    @ApiOperation("导出列表")
    @Log(title = "分润结算记录", businessType = BusinessType.EXPORT)
    @SaCheckPermission("business:ledger_settle_dealer:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<LedgerSettleDealerQueryBo> pageRequest, HttpServletResponse response) {
        List<LedgerSettleDealerVo> list = ledgerSettleDealerManagerService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "分润结算记录", LedgerSettleDealerVo.class, response);
    }
}
