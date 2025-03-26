package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.price.Price;
import com.obast.charer.qo.ChargerQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.system.dto.bo.ChargerBo;
import com.obast.charer.system.dto.bo.ChargerStartBo;
import com.obast.charer.system.dto.vo.device.ChargerVo;
import com.obast.charer.system.operate.IChargerOperateService;
import com.obast.charer.system.service.business.IChargerManagerService;
import com.obast.charer.system.service.platform.IProductService;
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
 * @ Description：充电桩管理
 */
@Api(tags = {"充电桩管理"})
@Slf4j
@RestController
@RequestMapping("/admin/charger")
public class ChargerController {

    @Autowired
    IProductService productService;

    @Autowired
    private IChargerManagerService chargerManagerService;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IPriceData priceData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IChargerOperateService chargerOperateService;

    @ApiOperation(value = "充电桩列表", notes = "充电桩列表", httpMethod = "POST")
    @SaCheckPermission("business:charger:list")
    @PostMapping("/list")
    public Paging<ChargerVo> getDevices(@Validated @RequestBody PageRequest<ChargerQueryBo> pageRequest) {
        return chargerManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建充电桩")
    @SaCheckPermission("business:charger:add")
    @PostMapping("/add")
    public void createDevice(@RequestBody @Validated Request<ChargerBo> bo) {
        chargerManagerService.addCharger(bo.getData());
    }

    @ApiOperation(value = "修改充电桩")
    @SaCheckPermission("business:charger:edit")
    @PostMapping("/edit")
    public void editDevice(@RequestBody @Validated Request<ChargerBo> bo) {
        chargerManagerService.updateCharger(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:charger:edit")
    @Log(title = "充电桩管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<ChargerBo> bo) {
        ChargerBo data = bo.getData();
        chargerManagerService.updateStatus(data);
    }

    @ApiOperation("获取充电桩详情")
    @SaCheckPermission("business:charger:query")
    @PostMapping("/detail")
    public ChargerVo getDetail(@RequestBody @Validated Request<String> request) {
        return chargerManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除充电桩")
    @SaCheckPermission("business:charger:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return chargerManagerService.deleteCharger(request.getData());
    }

    @ApiOperation("批量删除充电桩")
    @SaCheckPermission("business:charger:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return chargerManagerService.batchDeleteCharger(request.getData());
    }

    @ApiOperation("充电桩日志")
    @SaCheckPermission("business:charger:query")
    @PostMapping("/logs")
    public Paging<ThingModelMessage> logs(@Validated @RequestBody PageRequest<DeviceLogQueryBo> request) {
        return chargerManagerService.logs(request);
    }

    @ApiOperation("启动充电")
    @SaCheckPermission("business:charger:operate")
    @PostMapping("/startCharge")
    public void startCharge(@Validated @RequestBody PageRequest<ChargerStartBo> request) {
        ChargerStartBo bo = request.getData();
        Charger charger = chargerData.findById(bo.getId());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        ChargerGun chargerGun = chargerGunData.findById(bo.getGunId());

        if(chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }

        Customer customer = customerData.findById("1");
        if (customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        chargerOperateService.startCharge(ChargePayTypeEnum.Balance, ChargeStartTypeEnum.Now, ChargeStopTypeEnum.FullStop, charger.getDn(), chargerGun.getNo(), customer.getId(), null);
    }

    @ApiOperation("校时")
    @SaCheckPermission("business:charger:operate")
    @PostMapping("/timeConfig")
    public void timeConfig(@Validated @RequestBody PageRequest<String> request) {

        Charger charger = chargerData.findById(request.getData());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        chargerOperateService.timingConfig(charger.getId());
    }

    @ApiOperation("下发二维码")
    @SaCheckPermission("business:charger:operate")
    @PostMapping("/qrcodeConfig")
    public void qrcodeConfig(@Validated @RequestBody PageRequest<String> request) {

        Charger charger = chargerData.findById(request.getData());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        chargerOperateService.qrcodeConfig(charger.getId());
    }

    @ApiOperation("下发计价模型")
    @SaCheckPermission("business:charger:operate")
    @PostMapping("/priceConfig")
    public void priceConfig(@Validated @RequestBody PageRequest<String> request) {

        Charger charger = chargerData.findById(request.getData());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        Price price = priceData.findById(charger.getPriceId());
        if (price == null) {
            throw new BizException(ErrCode.PRICE_NOT_FOUND);
        }

        chargerOperateService.priceConfig(charger.getId(), price);
    }

    @ApiOperation("重起")
    @SaCheckPermission("business:charger:operate")
    @PostMapping("/reboot")
    public void reboot(@Validated @RequestBody PageRequest<String> request) {
        Charger charger = chargerData.findById(request.getData());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }
        chargerOperateService.reboot(charger.getId());
    }
}