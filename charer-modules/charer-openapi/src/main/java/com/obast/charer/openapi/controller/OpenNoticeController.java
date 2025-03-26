package com.obast.charer.openapi.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.dto.vo.OpenNoticeVo;
import com.obast.charer.openapi.service.IOpenNoticeService;
import com.obast.charer.qo.NoticeQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-通知公告"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/notice")
public class OpenNoticeController {

    @Autowired
    private IOpenNoticeService openNoticeService;


    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenNoticeVo> list(@RequestBody @Validated PageRequest<NoticeQueryBo> pageRequest) {
        return openNoticeService.queryPage(pageRequest);
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenNoticeVo getDetail(@RequestBody @Validated Request<String> bo) {
        return openNoticeService.queryDetail(bo.getData());
    }
}
