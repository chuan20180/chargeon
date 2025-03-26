package com.obast.charer.openapi.controller;

import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.openapi.dto.vo.OpenAdsVo;
import com.obast.charer.openapi.dto.vo.OpenStationVo;
import com.obast.charer.openapi.service.IOpenAdsService;
import com.obast.charer.openapi.service.IOpenStationService;
import com.obast.charer.qo.AdsQueryBo;
import com.obast.charer.qo.StationQueryBo;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"openapi-首页"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/index")
public class OpenIndexController extends BaseController {

    @Autowired
    private IOpenAdsService adsService;

    @Autowired
    private IOpenStationService stationService;


    @SaIgnore
    @ApiOperation("首页")
    @PostMapping("/index")
    public Map<?,?> index() {

        Map<String, Object> result = new HashMap<>();

        AdsQueryBo bo = new AdsQueryBo();
        bo.setStatus(EnableStatusEnum.Enabled);
        List<OpenAdsVo> ads = adsService.queryList(bo);
        result.put("ads", ads);

        StationQueryBo stationQueryBo = new StationQueryBo();
        stationQueryBo.setStatus(EnableStatusEnum.Enabled);
        List<OpenStationVo> stations = stationService.queryList(stationQueryBo);
        result.put("stations", stations);

        return result;
    }
}
