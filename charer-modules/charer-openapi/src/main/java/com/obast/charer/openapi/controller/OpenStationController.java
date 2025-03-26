package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.dto.vo.OpenPromotionVo;
import com.obast.charer.openapi.dto.vo.OpenStationFavoriteVo;
import com.obast.charer.openapi.dto.vo.OpenStationVo;
import com.obast.charer.openapi.service.IOpenPromotionService;
import com.obast.charer.openapi.service.IOpenStationFavoriteService;
import com.obast.charer.openapi.service.IOpenStationService;
import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.qo.StationQueryBo;
import cn.dev33.satoken.annotation.SaIgnore;
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

@Api(tags = {"openapi-场站"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/station")
public class OpenStationController {

    @Autowired
    private IOpenStationService openStationService;

    @Autowired
    private IOpenStationFavoriteService openStationFavoriteService;

    @Autowired
    private IOpenPromotionService openPromotionService;

    @SaIgnore
    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenStationVo> list(@RequestBody @Validated PageRequest<StationQueryBo> pageRequest) {
        return openStationService.queryPage(pageRequest);
    }

    @SaIgnore
    @ApiOperation("详情")
    @PostMapping("/detail")
    public Object getDetail(@RequestBody @Validated Request<StationQueryBo> bo) {
        JSONObject result = new JSONObject();

        OpenStationVo station = openStationService.queryDetail(bo.getData().getId());
        result.put("station", station);

        List<OpenPromotionVo> promotions = openPromotionService.queryAvailableListById(station.getId());
        result.put("promotions", promotions);
        return result;
    }

    @ApiOperation("收藏")
    @PostMapping("/favorite")
    public Paging<OpenStationFavoriteVo> favorite(@RequestBody @Validated PageRequest<StationFavoriteQueryBo> pageRequest) {
        return openStationFavoriteService.queryPage(pageRequest);
    }
}
