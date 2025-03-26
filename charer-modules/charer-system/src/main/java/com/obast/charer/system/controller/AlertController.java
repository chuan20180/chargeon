package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.system.service.monitor.impl.AlertService;
import com.obast.charer.model.alert.AlertConfig;
import com.obast.charer.model.alert.AlertRecord;
import com.obast.charer.qo.AlertConfigQueryBo;
import com.obast.charer.qo.AlertRecordQueryBo;
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


@Api(tags = {"告警中心"})
@Slf4j
@RestController
@RequestMapping("/admin/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;


    @ApiOperation("查询告警消息分页")
    @SaCheckPermission("monitor:alert:list")
    @PostMapping("/selectAlertRecordPage")
    public Paging<AlertRecord> selectAlertRecordPage(@RequestBody @Validated PageRequest<AlertRecordQueryBo> request) {
        return alertService.selectAlertRecordPage(request);
    }


}
