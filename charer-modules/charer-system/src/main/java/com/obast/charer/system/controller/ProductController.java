package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.qo.ProductQueryBo;
import com.obast.charer.system.dto.bo.ProductBo;
import com.obast.charer.system.dto.vo.product.ProductVo;
import com.obast.charer.system.service.platform.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @ Description：产品管理
 */
@Api(tags = {"产品"})
@Slf4j
@RestController
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @ApiOperation("列表")
    @SaCheckPermission("iot:product:query")
    @PostMapping("/list")
    public Paging<ProductVo> queryPageList(@Validated @RequestBody PageRequest<ProductQueryBo> request) {
        return productService.queryPageList(request);
    }

    @ApiOperation("新建")
    @SaCheckPermission("iot:product:add")
    @PostMapping(value = "/add")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    public ProductVo create(@Validated(AddGroup.class) @RequestBody Request<ProductBo> request) {
        return productService.add(request.getData());
    }

    @ApiOperation(value = "编辑")
    @SaCheckPermission("iot:product:edit")
    @PostMapping("/edit")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<ProductBo> request) {
        return productService.update(request.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:charger:edit")
    @Log(title = "充电桩管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<ProductBo> bo) {
        ProductBo data = bo.getData();
        productService.updateStatus(data);
    }

    @ApiOperation("详情")
    @SaCheckPermission("iot:product:query")
    @PostMapping(value = "/getDetail")
    public ProductVo getDetail(@RequestBody @Validated Request<String> request) {
        return productService.queryDetail(request.getData());
    }

    @ApiOperation("删除产品")
    @SaCheckPermission("iot:product:remove")
    @PostMapping(value = "/deleteProduct")
    public boolean deleteProduct(@RequestBody @Validated Request<String> request) {
        return productService.delete(request.getData());
    }

    /**
     * 选择框列表
     */
    @ApiOperation(value = "选择框列表", notes = "选择框列表")
    @PostMapping("/optionSelect")
    public List<ProductVo> optionSelect(@RequestBody @Validated(QueryGroup.class) PageRequest<ProductQueryBo> pageRequest) {
        return productService.queryList(pageRequest);
    }
}
