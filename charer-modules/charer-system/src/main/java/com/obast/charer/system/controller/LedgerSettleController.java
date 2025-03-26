package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.QueryGroup;

import com.obast.charer.system.dto.bo.LedgerSettleBo;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleVo;
import com.obast.charer.system.service.business.ILedgerSettleDealerManagerService;
import com.obast.charer.system.service.business.ILedgerSettleManagerService;
import com.obast.charer.qo.LedgerSettleQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：分成结算管理
 */
@Api(tags = {"分成结算管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/ledgerSettle")
public class LedgerSettleController {

    private final ILedgerSettleManagerService ledgerSettleManagerService;

    private final ILedgerSettleDealerManagerService ledgerSettleDealerManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:ledger_settle:list")
    @PostMapping("/list")
    public Paging<LedgerSettleVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<LedgerSettleQueryBo> pageRequest) {
        return ledgerSettleManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:ledger_settle:add")
    @Log(title = "新建分成结算单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<LedgerSettleBo> bo) {
        return ledgerSettleManagerService.addLedgerSettle(bo.getData());
    }
}
