package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.system.operate.IOrdersOperateService;
import com.obast.charer.system.operate.IParkOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：内部占位费服务实现
 */
@Slf4j
@Api(tags = {"内部占位费操作"})
@RestController
@RequestMapping("/inner/park")
public class InnerParkController {

    @Autowired
    private IParkOperateService parkOperateService;

    /**
     * 占位费结算
     */

    @ApiOperation("占位费结算")
    @GetMapping("/settle")
    public Response<?> settle(@RequestParam("parkId") String parkId) {
        parkOperateService.settle(parkId);
        return Response.success();
    }
}
