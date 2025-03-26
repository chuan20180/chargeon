package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.RefundBo;
import com.obast.charer.system.dto.vo.refund.RefundVo;
import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.system.service.business.IRefundManagerService;
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

import java.util.List;

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
@RequestMapping("/admin/refund")
public class RefundController {

    private final IRefundManagerService refundManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:refund:list")
    @PostMapping("/list")
    public Paging<RefundVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<RefundQueryBo> pageRequest) {
        return refundManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:refund:add")
    @Log(title = "退款记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<RefundBo> bo) {
        return refundManagerService.addRefund(bo.getData());
    }

    @ApiOperation(value = "退款")
    @SaCheckPermission("business:refund:refund")
    @Log(title = "退款", businessType = BusinessType.UPDATE)
    @PostMapping("/refund")
    public boolean refund(@Validated(EditGroup.class) @RequestBody Request<String> bo) {
        return refundManagerService.doRefund(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:refund:delete")
    @Log(title = "退款记录", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public boolean remove(@Validated @RequestBody Request<String> request) {
        return refundManagerService.removeRefund(request.getData());
    }
}
