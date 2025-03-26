package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.qo.RefundBalanceQueryBo;
import com.obast.charer.system.dto.vo.refund.RefundBalanceVo;
import com.obast.charer.system.service.business.IRefundBalanceManagerService;
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
 * @ Description：退款记录管理
 */
@Api(tags = {"退款记录管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/refundBalance")
public class RefundBalanceController {

    private final IRefundBalanceManagerService refundManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:refund:list")
    @PostMapping("/list")
    public Paging<RefundBalanceVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<RefundBalanceQueryBo> pageRequest) {
        return refundManagerService.queryPageList(pageRequest);
    }
}
