package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysDealerBo;
import com.obast.charer.system.dto.vo.SysDealerVo;
import com.obast.charer.system.service.system.ISysDealerService;
import com.obast.charer.qo.SysDealerQueryBo;
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

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：分帐成员管理
 */
@Api(tags = {"分帐成员管理"})
@Slf4j
@RestController
@RequestMapping("/admin/system/dealer")
public class SysDealerController extends BaseController {

    @Autowired
    private ISysDealerService sysDealerService;

    @ApiOperation("列表")
    @SaCheckPermission("system:dealer:list")
    @PostMapping("/list")
    public Paging<SysDealerVo> list(@RequestBody PageRequest<SysDealerQueryBo> pageRequest) {
        return sysDealerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("system:dealer:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<SysDealerBo> bo) {
        return sysDealerService.addDealer(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("system:dealer:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<SysDealerBo> bo) {
        return sysDealerService.updateDealer(bo.getData());
    }


    @ApiOperation("删除")
    @SaCheckPermission("system:dealer:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return sysDealerService.deleteDealer(request.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:dealer:edit")
    @Log(title = "代理商管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysDealerBo> bo) {
        SysDealerBo data = bo.getData();
        sysDealerService.updateStatus(data);
    }


    @ApiOperation("批量删除")
    @SaCheckPermission("system:dealer:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return sysDealerService.batchDeleteDealer(request.getData());
    }

    @ApiOperation("详细")
    @SaCheckPermission("system:dealer:query")
    @PostMapping("/detail")
    public SysDealerVo detail(@RequestBody Request<String> bo) {
        return sysDealerService.queryDetail(bo.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<SysDealerVo> optionSelect(@Validated @RequestBody PageRequest<SysDealerQueryBo> pageRequest) {
        return sysDealerService.queryList(pageRequest);
    }
}
