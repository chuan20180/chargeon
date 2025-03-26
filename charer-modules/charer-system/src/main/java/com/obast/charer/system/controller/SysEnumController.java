package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysDictTypeBo;
import com.obast.charer.system.dto.vo.SysDictTypeVo;
import com.obast.charer.system.dto.vo.SysEnumDataVo;
import com.obast.charer.system.service.system.ISysDictTypeService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/enum")
public class SysEnumController extends BaseController {

    private final ISysDictTypeService dictTypeService;

    /**
     * 根据字典类型查询字典数据信息
     *
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息", notes = "根据字典类型查询字典数据信息")
    @PostMapping(value = "/type")
    public List<SysEnumDataVo> enumType(@Validated @RequestBody Request<String> bo) {
        String enumType = bo.getData();

        List<SysEnumDataVo> data = new ArrayList<>();
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.obast.charer.enums." + enumType);
        } catch (Exception e) {
            try {
                clazz = Class.forName("com.obast.charer.common.enums." + enumType);
            } catch (Exception es) {
                log.error("指定的Enum 不存在 {}", enumType);
            }
        }

        if(clazz != null) {
            try {
                //获取所有枚举实例
                Enum[] enumConstants = (Enum[]) clazz.getEnumConstants();
                //根据方法名获取方法
                Method getText = clazz.getMethod("getMsg");
                Method getListClass = clazz.getMethod("getListClass");
                Method getIsDefault = clazz.getMethod("getIsDefault");

                for (Enum enum1 : enumConstants) {

                    SysEnumDataVo enumModel = new SysEnumDataVo();

                    //执行枚举方法获得枚举实例对应的值
                    Object getTextInvoke = getText.invoke(enum1);
                    enumModel.setEnumLabel(getTextInvoke.toString());

                    String name = enum1.name();
                    enumModel.setEnumValue(name);


                    Object getListClassInvoke = getListClass.invoke(enum1);
                    if(getListClassInvoke != null) {
                        enumModel.setListClass(getListClassInvoke.toString());
                    }

                    Object getIsDefaultInvoke = getIsDefault.invoke(enum1);
                    if(getIsDefaultInvoke != null) {
                        enumModel.setIsDefault(getIsDefaultInvoke.toString());
                    }

                    data.add(enumModel);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return data;
    }

    /**
     * 查询字典类型列表
     */
    @ApiOperation(value = "查询字典类型列表", notes = "查询字典类型列表")
    @SaCheckPermission("system:dict:list")
    @PostMapping("/list")
    public Paging<SysDictTypeVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<SysDictTypeBo> query) {
        return dictTypeService.selectPageDictTypeList(query);
    }


    /**
     * 查询字典类型详细
     */
    @ApiOperation(value = "查询字典类型详细", notes = "查询字典类型详细")
    @SaCheckPermission("system:dict:query")
    @PostMapping(value = "/getById")
    public SysDictTypeVo getInfo(@Validated(QueryGroup.class) @RequestBody Request<SysDictTypeBo> bo) {
        String dictTypeId = bo.getData().getId();
        return dictTypeService.selectDictTypeById(dictTypeId);
    }

    /**
     * 刷新字典缓存
     */
    @ApiOperation(value = "刷新字典缓存", notes = "刷新字典缓存")
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @PostMapping("/refreshCache")
    public void refreshCache() {
        dictTypeService.resetDictCache();
    }

    /**
     * 获取字典选择框列表
     */
    @ApiOperation(value = "获取字典选择框列表", notes = "获取字典选择框列表")
    @PostMapping("/optionselect")
    public List<SysDictTypeVo> optionselect() {
        return dictTypeService.selectDictTypeAll();
    }
}
