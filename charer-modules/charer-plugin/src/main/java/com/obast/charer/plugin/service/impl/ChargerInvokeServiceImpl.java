package com.obast.charer.plugin.service.impl;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.message.*;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerDirectiveData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.platform.IProtocolData;
import com.obast.charer.enums.ChargerActionEnum;
import com.obast.charer.enums.ChargerDirectiveResultEnum;
import com.obast.charer.enums.ChargerDirectiveStateEnum;
import com.obast.charer.enums.ChargerDirectiveTypeEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerDirective;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.product.Product;
import com.obast.charer.plugin.service.IChargerCtrlService;
import com.obast.charer.plugin.service.IChargerInvokeService;
import com.obast.charer.qo.ChargerDirectiveQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author sjg
 */
@Slf4j
@Service
public class ChargerInvokeServiceImpl implements IChargerInvokeService {

    @Autowired
    private IProductData productData;

    @Autowired
    private IProtocolData protocolData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerCtrlService chargerCtrlService;

    @Autowired
    private IChargerDirectiveData chargerDirectiveData;


    /**
     * 启动充电指令下发
     */
    @Override
    public ActionResult<?> startCharge(String chargerDn, String chargerGunNo, String orderTranId, String logicalCardNo, String physicalCardNo, BigDecimal balance ) {
        log.debug("[服务器端调试]启动充电指令下发 dn={}, chargerGunNo={}, orderTranId={}, logicalCardNo={}, physicalCardNo={}, balance={}", chargerDn, chargerGunNo, orderTranId, logicalCardNo, physicalCardNo, balance);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.START_CHARGE_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setType(ChargerDirectiveTypeEnum.Order);
        chargerDirective.setRelateId(orderTranId);
        chargerDirective.setResult(ChargerDirectiveResultEnum.Pending);
        chargerDirective.setDirective(directive.name());

        StartChargeInvokeMessage message = new StartChargeInvokeMessage();
        message.setTranId(orderTranId);
        message.setDeviceName(chargerDn);
        message.setGunNo(chargerGunNo);
        message.setLogicalCardNo(logicalCardNo);
        message.setPhysicalCardNo(physicalCardNo);

        //balance = new BigDecimal("2.00");
        message.setBalance(balance);

        chargerDirective.setData(JsonUtils.toJsonString(message));

        ChargerDirective savedChargerDirective = chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for(int i = 0; i< 30; i++) {
            int finalI = i;
            Future<ActionResult<?>> future = executorService.submit(() -> {
                log.info("[设备调试]启动充电结果查询 第{}次", finalI);
                ChargerDirective checkChargerDirective = chargerDirectiveData.findById(savedChargerDirective.getId());
                if(checkChargerDirective.getState().equals(ChargerDirectiveStateEnum.Finished)) {
                    if(checkChargerDirective.getResult().equals(ChargerDirectiveResultEnum.Successful)) {
                        return new ActionResult<>(0);
                    }
                    return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), savedChargerDirective.getReason());
                }
                return new ActionResult<>(-1);
            });
            try {
                // 获取任务的返回结果
                ActionResult<?> result = future.get();
                if(result.getCode() == 0) {
                    return result;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("[设备调试]启动充电指令执行异常 [{}]", e.getLocalizedMessage());
            }
        }
        executorService.shutdown();
        return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), "连接设备超时");

    }

    /**
     * 停止充电指令下发
     */
    @Override
    public ActionResult<?> stopCharge(String chargerDn, String chargerGunNo) {
        log.debug("[服务器端调试]停止充电指令下发 dn={}, chargerGunNo={}", chargerDn, chargerGunNo);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.STOP_CHARGE_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());
        chargerDirective.setType(ChargerDirectiveTypeEnum.Order);

        StopChargeInvokeMessage message = new StopChargeInvokeMessage(chargerGunNo);

        chargerDirective.setData(JsonUtils.toJsonString(message));

        ChargerDirective savedChargerDirective = chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for(int i = 0; i< 30; i++) {
            int finalI = i;
            Future<ActionResult<?>> future = executorService.submit(() -> {
                log.info("[设备调试]停止充电结果查询 第{}次", finalI);
                ChargerDirective checkChargerDirective = chargerDirectiveData.findById(savedChargerDirective.getId());
                if(checkChargerDirective.getState().equals(ChargerDirectiveStateEnum.Finished)) {
                    if(checkChargerDirective.getResult().equals(ChargerDirectiveResultEnum.Successful)) {
                        return new ActionResult<>(0);
                    }
                    return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), savedChargerDirective.getReason());
                }
                return new ActionResult<>(-1);
            });
            try {
                // 获取任务的返回结果
                ActionResult<?> result = future.get();
                if(result.getCode() == 0) {
                    return result;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("[设备调试]执行指令异常 [{}]", e.getLocalizedMessage());
            }
        }
        executorService.shutdown();
        return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), "连接设备超时");
    }

    /**
     * 余额更新指令下发
     */
    @Override
    public ActionResult<?> balanceUpdate(String chargerDn, String chargerGunNo, String physicalCardNo, BigDecimal balance) {
        log.debug("[服务器端调试]余额更新指令下发 dn={}, chargerGunNo={}, physicalCardNo={}, balance={}", chargerDn, chargerGunNo, physicalCardNo, balance);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.BALANCE_UPDATE_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        BalanceUpdateInvokeMessage message = new BalanceUpdateInvokeMessage(chargerGunNo, physicalCardNo, balance);

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }

        return new ActionResult<>(0);
    }

    /**
     * 校时
     */
    @Override
    public ActionResult<?> timingConfig(String chargerDn) {
        log.debug("[服务器端调试]校时指令下发 dn={}", chargerDn);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.TIME_CONFIG_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        TimingConfigInvokeMessage message = new TimingConfigInvokeMessage(LocalDateTime.now());

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }


        return new ActionResult<>(0);
    }


    /**
     * 参数配置指令下发
     */
    @Override
    public ActionResult<?> paramConfig(String chargerDn, short status, short maxPower) {
        log.debug("[服务器端调试]参数配置指令下发 dn={}, status={}, maxPower={}", chargerDn, status, maxPower);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.PARAM_CONFIG_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        ParamConfigInvokeMessage message = new ParamConfigInvokeMessage(status, maxPower);

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }


        return new ActionResult<>(0);
    }

    /**
     * 计价模型指令下发
     */
    @Override
    public ActionResult<?> priceConfig(String chargerDn, Price price) {
        log.debug("[服务器端调试]计价模型指令下发 dn={}, price={}", chargerDn, price);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.PRICE_CONFIG_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        PriceConfigInvokeMessage message = new PriceConfigInvokeMessage(price.getNo(), price.getProperties());

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }


        return new ActionResult<>(0);
    }

    /**
     * 重起指令下发
     */
    @Override
    public ActionResult<?> reboot(String chargerDn) {
        log.debug("[服务器端调试]重起指令下发 dn={}", chargerDn);
        ChargerDirectiveEnum directive = ChargerDirectiveEnum.REBOOT_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        RebootInvokeMessage message = new RebootInvokeMessage((short) 1);

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }


        return new ActionResult<>(0);
    }

    /**
     * 二维码指令下发
     */
    @Override
    public ActionResult<?> qrcodeConfig(String chargerDn) {

        Charger charger = chargerData.findByDn(chargerDn);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //https://cdz.haocheting.com?t=Charger&p=ykc&n=3605814393415901
        String template = "https://cdz.haocheting.com?p=%s&k=%s&a=%s&n=";
        Product product = productData.findByProductKey(charger.getProductKey());
        if(product == null) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }

        String prefix = String.format(template, product.getType().name(), product.getProductKey(), ChargerActionEnum.StartCharge.name());

        log.debug("[服务器端调试]二维码指令下发 dn={}, prefix={}", chargerDn, prefix);

        ChargerDirectiveEnum directive = ChargerDirectiveEnum.QRCODE_INVOKE;

        //检查旧指令未完成的设为失败状态
        ChargerDirectiveQueryBo chargerDirectiveQueryBo = new ChargerDirectiveQueryBo();
        chargerDirectiveQueryBo.setDn(chargerDn);
        chargerDirectiveQueryBo.setDirective(directive.name());
        chargerDirectiveQueryBo.setState(ChargerDirectiveStateEnum.Pending);
        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findList(chargerDirectiveQueryBo);
        for(ChargerDirective chargerDirective: chargerDirectives) {
            chargerDirective.setState(ChargerDirectiveStateEnum.Fail);
        }
        chargerDirectiveData.batchSave(chargerDirectives);

        //写入设备指令
        ChargerDirective chargerDirective = new ChargerDirective();
        chargerDirective.setDn(chargerDn);
        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        chargerDirective.setDirective(directive.name());

        QrcodeInvokeMessage message = new QrcodeInvokeMessage((byte) 1, prefix);

        chargerDirective.setData(JsonUtils.toJsonString(message));
        chargerDirectiveData.add(chargerDirective);

        try {
            //执行指令
            chargerCtrlService.invokeService(chargerDn, chargerDirective.getSerial().shortValue(), directive, message);
        } catch (BizException e) {
            return new ActionResult<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("执行指令异常: {}", e.getLocalizedMessage());
        }


        return new ActionResult<>(0);
    }
}
