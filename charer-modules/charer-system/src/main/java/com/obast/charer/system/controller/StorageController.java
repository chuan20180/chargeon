package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.qo.StorageQueryBo;
import com.obast.charer.system.dto.bo.StorageBatchBo;
import com.obast.charer.system.dto.bo.StorageBo;
import com.obast.charer.system.dto.bo.StorageStoreBo;
import com.obast.charer.system.dto.vo.StorageVo;
import com.obast.charer.system.service.business.IStorageManagerService;
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
 * @ Description：充电桩库管理
 */
@Api(tags = {"桩号管理"})
@Slf4j
@RestController
@RequestMapping("/admin/storage")
public class StorageController {

    @Autowired
    private IStorageManagerService chargerStorageManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:storage:list")
    @PostMapping("/list")
    public Paging<StorageVo> queryPageList(@Validated @RequestBody PageRequest<StorageQueryBo> pageRequest) {
        return chargerStorageManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "添加")
    @SaCheckPermission("system:storage:add")
    @PostMapping("/add")
    public void add(@RequestBody @Validated Request<StorageBo> bo) {
        chargerStorageManagerService.add(bo.getData());
    }

    @ApiOperation(value = "批量生成")
    @SaCheckPermission("system:storage:add")
    @PostMapping("/batchAdd")
    public void batchAdd(@RequestBody @Validated Request<StorageBatchBo> bo) {
        chargerStorageManagerService.batchAdd(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("system:storage:edit")
    @PostMapping("/edit")
    public void edit(@RequestBody @Validated Request<StorageBo> bo) {
        chargerStorageManagerService.update(bo.getData());
    }

    @ApiOperation("详情")
    @SaCheckPermission("system:storage:query")
    @PostMapping("/detail")
    public StorageVo detail(@RequestBody @Validated Request<String> request) {
        return chargerStorageManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("system:storage:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return chargerStorageManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("system:storage:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return chargerStorageManagerService.batchDelete(request.getData());
    }

    @ApiOperation("批量导入")
    @SaCheckPermission("system:storage:store")
    @PostMapping("/batchStore")
    public boolean batchStore(@Validated @RequestBody Request<StorageStoreBo> request) {
        return chargerStorageManagerService.batchStore(request.getData());
    }


    @ApiOperation(value = "选项列表", notes = "选项列表", httpMethod = "POST")
    @PostMapping("/optionSelect")
    public List<StorageVo> optionSelect(@Validated @RequestBody PageRequest<StorageQueryBo> pageRequest) {
        return chargerStorageManagerService.queryList(pageRequest);
    }
}