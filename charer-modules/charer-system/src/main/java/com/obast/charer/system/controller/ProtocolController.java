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
import com.obast.charer.qo.ProtocolQueryBo;
import com.obast.charer.system.dto.bo.ProtocolBo;
import com.obast.charer.system.dto.vo.ProtocolVo;
import com.obast.charer.system.service.platform.IProtocolService;
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
 * @ Description：协议管理
 */
@Api(tags = {"协议管理"})
@Slf4j
@RestController
@RequestMapping("/admin/protocol")
public class ProtocolController {

    @Autowired
    private IProtocolService productService;

    @ApiOperation("列表")
    @SaCheckPermission("protocol:query")
    @PostMapping("/list")
    public Paging<ProtocolVo> queryPageList(@Validated @RequestBody PageRequest<ProtocolQueryBo> request) {
        return productService.queryPageList(request);
    }

    @ApiOperation("新建")
    @SaCheckPermission("protocol:add")
    @PostMapping(value = "/add")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    public ProtocolVo create(@Validated(AddGroup.class) @RequestBody Request<ProtocolBo> request) {
        return productService.add(request.getData());
    }

    @ApiOperation(value = "编辑")
    @SaCheckPermission("protocol:edit")
    @PostMapping("/edit")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<ProtocolBo> request) {
        return productService.update(request.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("protocol:edit")
    @Log(title = "充电桩管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<ProtocolBo> bo) {
        ProtocolBo data = bo.getData();
        productService.updateStatus(data);
    }

    @ApiOperation("详情")
    @SaCheckPermission("protocol:query")
    @PostMapping(value = "/detail")
    public ProtocolVo detail(@RequestBody @Validated Request<String> request) {
        return productService.queryDetail(request.getData());
    }

    @ApiOperation("删除产品")
    @SaCheckPermission("protocol:delete")
    @PostMapping(value = "/delete")
    public boolean delete(@RequestBody @Validated Request<String> request) {
        return productService.delete(request.getData());
    }

    /**
     * 选择框列表
     */
    @ApiOperation(value = "选择框列表", notes = "选择框列表")
    @PostMapping("/optionSelect")
    public List<ProtocolVo> optionSelect(@RequestBody @Validated(QueryGroup.class) PageRequest<ProtocolQueryBo> pageRequest) {
        return productService.queryList(pageRequest);
    }
}
