package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.SysDictDataBo;
import com.obast.charer.system.dto.vo.SysDictDataVo;
import com.obast.charer.system.service.system.ISysDictDataService;
import com.obast.charer.system.service.system.ISysDictTypeService;
import com.obast.charer.qo.SysDictDataQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/dict/data")
public class SysDictDataController {

    private final ISysDictDataService dictDataService;
    private final ISysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @SaCheckPermission("system:dict:list")
    @ApiOperation(value = "查询字典数据列表", notes = "查询字典数据列表")
    @PostMapping("/list")
    public Paging<SysDictDataVo> list(@Validated @RequestBody  PageRequest<SysDictDataQueryBo> pageRequest) {
        return dictDataService.queryPageList( pageRequest);
    }

    /**
     * 导出字典数据列表
     */
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @ApiOperation(value = "导出字典数据列表", notes = "导出字典数据列表")
    @PostMapping("/export")
    public void export(@Validated @RequestBody  PageRequest<SysDictDataQueryBo> pageRequest, HttpServletResponse response) {
        List<SysDictDataVo> list = dictDataService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "字典数据", SysDictDataVo.class, response);
    }

    /**
     * 查询字典数据详细
     *

     */
    @SaCheckPermission("system:dict:query")
    @ApiOperation(value = "查询字典数据详细", notes = "查询字典数据详细")
    @PostMapping(value = "/getDetail")
    public SysDictDataVo getInfo(@Validated @RequestBody Request<String> bo) {
        return dictDataService.selectDictDataById(bo.getData());
    }

    /**
     * 根据字典类型查询字典数据信息
     *

     */
    @ApiOperation(value = "根据字典类型查询字典数据信息", notes = "根据字典类型查询字典数据信息")
    @PostMapping(value = "/type")
    public List<SysDictDataVo> dictType(@Validated @RequestBody Request<String> bo) {
        String dictType = bo.getData();
        List<SysDictDataVo> data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return data;
    }

    /**
     * 新增字典类型
     */
    @ApiOperation(value = "新增字典类型", notes = "新增字典类型")
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<SysDictDataBo> bo) {
        dictDataService.insertDictData(bo.getData());
    }

    /**
     * 修改保存字典类型
     */
    @ApiOperation(value = "修改保存字典类型", notes = "修改保存字典类型")
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public void edit(@Validated @RequestBody Request<SysDictDataBo> bo) {
        dictDataService.updateDictData(bo.getData());
    }

    /**
     * 删除字典类型
     *
     */
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型")
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<String[]> bo) {
        dictDataService.deleteDictDataByIds(bo.getData());
    }
}
