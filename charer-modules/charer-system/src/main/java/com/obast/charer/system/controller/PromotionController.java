package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.PromotionBo;
import com.obast.charer.system.dto.vo.promotion.PromotionVo;
import com.obast.charer.system.service.business.IPromotionManagerService;
import com.obast.charer.qo.PromotionQueryBo;
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
 * @ Description：活动管理
 */
@Api(tags = {"活动管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/promotion")
public class PromotionController {

    private final IPromotionManagerService promotionManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:promotion:list")
    @PostMapping("/list")
    public Paging<PromotionVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<PromotionQueryBo> pageRequest) {
        return promotionManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:promotion:add")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<PromotionBo> bo) {
        return promotionManagerService.addPromotion(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:promotion:edit")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<PromotionBo> bo) {
        return promotionManagerService.updatePromotion(bo.getData());
    }


    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:promotion:edit")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PromotionBo> bo) {
        PromotionBo data = bo.getData();
        promotionManagerService.updateStatus(data);
    }


    @ApiOperation("删除")
    @SaCheckPermission("business:promotion:delete")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public boolean remove(@Validated @RequestBody Request<String> request) {
        return promotionManagerService.removePromotion(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:promotion:delete")
    @PostMapping("/batchRemove")
    public boolean batchRemove(@Validated @RequestBody Request<List<String>> request) {
        return promotionManagerService.batchRemovePromotion(request.getData());
    }
}
