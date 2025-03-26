package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.system.dto.vo.customer.CustomerVo;
import com.obast.charer.system.service.business.ICustomerManagerService;
import com.obast.charer.qo.CustomerQueryBo;
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

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：客户管理
 */
@Api(tags = {"客户管理"})
@Slf4j
@RestController
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private ICustomerManagerService customerManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:customer:list")
    @PostMapping("/list")
    public Paging<CustomerVo> list(@Validated @RequestBody PageRequest<CustomerQueryBo> pageRequest) {
        return customerManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("根据id获取详细信息")
    @SaCheckPermission("business:customer:query")
    @PostMapping(value = {"/getDetail"})
    public CustomerVo getDetail(@Validated @RequestBody Request<String> request) {
        return customerManagerService.queryDetail(request.getData());
    }
}