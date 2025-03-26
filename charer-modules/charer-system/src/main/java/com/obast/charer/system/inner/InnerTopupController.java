package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.common.model.PaymentNotify;
import com.obast.charer.system.service.business.ITopupManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Slf4j
@Api(tags = {"内部充值"})
@RestController
@RequestMapping("/inner/topup")
public class InnerTopupController {

    @Autowired
    private ITopupManagerService topupManagerService;

    /**
     * 充值完成
     */
    @ApiOperation("充值完成")
    @PostMapping("/complete")
    public Response<?> complete(@RequestBody PaymentNotify paymentNotify) {
        topupManagerService.complete(paymentNotify);
        return Response.success();
    }
}
