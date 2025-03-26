package com.obast.charer.payment.mbank.controller;

import com.obast.charer.common.api.ResponseXml;

import com.obast.charer.common.tenant.helper.TenantHelper;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.payment.mbank.MbankErrCode;
import com.obast.charer.payment.mbank.xml.*;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.data.business.IRechargeData;
import com.obast.charer.data.platform.IRechargeItemData;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.topup.Topup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：Mbank支付回调控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/mbank")
public class MbankController {

  @Autowired
  IRechargeData rechargeData;

  @Autowired
  IRechargeItemData rechargeItemData;

  @Autowired
  ICustomerData customerData;

  @Autowired
  ITopupData topupData;

  @RequestMapping(value ="/payment", consumes= "application/xml;charset=UTF-8", produces = "application/xml;charset=UTF-8")
  public Object payment(@RequestBody BaseXml<RequestPostPaymentBodyXml> requestXml) {
    log.debug("postPayment请求xml: {}", requestXml);

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      String dts = sdf.format(new Date());

      HeadXml requestHead = requestXml.getHead();
      requestHead.setDTS(dts);

      RequestPostPaymentBodyXml requestBody = requestXml.getBody();

      if(requestHead.getOP() == null) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.OP_INVALID.getValue())));
      }

      String op = requestHead.getOP();

      if(op.equals("QE11")) {
        if(requestBody.getParam1() == null) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.PARAM1_NULL.getValue())));
        }

        String topupTranId = requestBody.getParam1();
        if(StringUtils.isBlank(topupTranId)) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.PARAM1_BLANK.getValue())));
        }

        Topup topup = TenantHelper.ignore(()->topupData.findByTranId(topupTranId));
        if(topup == null) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.RECORD_NOT_EXIST.getValue())));
        }

        log.debug("Topup实体: {}", JsonUtils.toJsonString(topup));

        BaseXml<ResPostPaymentBodyXml> baseXml = new BaseXml<>();
        baseXml.setHead(requestHead);
        ResPostPaymentBodyXml resPostPaymentBodyXml = new ResPostPaymentBodyXml();
        resPostPaymentBodyXml.setStatus("200");
        resPostPaymentBodyXml.setSum(topup.getPaidAmount().setScale(2, RoundingMode.HALF_UP).toString());
        resPostPaymentBodyXml.setRequisite1(String.format("%s:%s", "Phone", topup.getUserName()));
        resPostPaymentBodyXml.setRequisite2(String.format("%s:%s", "Transaction Id", topup.getTranId()));

        baseXml.setBody(resPostPaymentBodyXml);
        return ResponseXml.success(baseXml);
      }

      else if(op.equals("QE10")) {
        if(requestBody.getSum() == null) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.SUM_NULL.getValue())));
        }

        BigDecimal amount = new BigDecimal(requestBody.getSum());
        if(amount.compareTo(new BigDecimal(0)) <= 0) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.SUM_BLANK.getValue())));
        }

        String topupTranId = requestBody.getParam1();
        if(StringUtils.isBlank(topupTranId)) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.PARAM1_BLANK.getValue())));
        }

        Topup topup = TenantHelper.ignore(()->topupData.findByTranId(topupTranId));
        if(topup == null) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.RECORD_NOT_EXIST.getValue())));
        }

        if(topup.getState().equals(TopupStateEnum.Successful)) {
          return ResponseXml.success(new BaseXml<>(requestHead, new ResSuccessBodyXml("250", MbankErrCode.PAYMENT_SUCCESSFUL.getValue())));
        }

        if(amount.compareTo(topup.getPaidAmount()) != 0) {
          return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.SUM_EXCEPTION.getValue())));
        }

        topup.setState(TopupStateEnum.Successful);
        topupData.save(topup);

        BaseXml<ResSuccessBodyXml> baseXml = new BaseXml<>();
        baseXml.setHead(requestHead);
        baseXml.setBody(new ResSuccessBodyXml("250", MbankErrCode.PAYMENT_SUCCESSFUL.getValue()));
        return ResponseXml.success(baseXml);
      } else {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.OP_INVALID.getValue())));
      }

    } catch (Exception e) {
      return ResponseXml.error(new BaseXml<>(new ResErrorBodyXml("424", e.getLocalizedMessage())));
    }
  }

  @RequestMapping(value ="/cancelPayment", consumes= "application/xml;charset=UTF-8", produces = "application/xml;charset=UTF-8")
  public Object cancelPayment(@RequestBody BaseXml<RequestCancelPaymentBodyXml> requestXml) {
    log.debug("请求xml: {}", requestXml);
    try {
      HeadXml requestHead = requestXml.getHead();
      RequestCancelPaymentBodyXml requestBody = requestXml.getBody();

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      String dts = sdf.format(new Date());
      requestHead.setDTS(dts);

      if(requestHead.getOP() == null) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.OP_NULL.getValue())));
      }

      if(!requestHead.getOP().equals("PR09")) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.OP_INVALID.getValue())));
      }

      String topupTranId = requestBody.getRequisite2();
      if(StringUtils.isBlank(topupTranId)) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.REQUISITE2_BLANK.getValue())));
      }

      Topup topup = TenantHelper.ignore(()->topupData.findByTranId(topupTranId));
      if(topup == null) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.RECORD_NOT_EXIST.getValue())));
      }

      if(!topup.getState().equals(TopupStateEnum.Pending)) {
        return ResponseXml.error(new BaseXml<>(requestHead, new ResErrorBodyXml("420", MbankErrCode.RECORD_STATE_EXCEPTION.getValue())));
      }

      topup.setState(TopupStateEnum.Fail);
      topupData.save(topup);

      BaseXml<ResSuccessBodyXml> baseXml = new BaseXml<>();
      baseXml.setHead(requestHead);
      baseXml.setBody(new ResSuccessBodyXml("250", MbankErrCode.CANCEL_SUCCESSFUL.getValue()));
      return ResponseXml.success(baseXml);
    } catch (Exception e) {
      return ResponseXml.error(new BaseXml<>(new ResErrorBodyXml("424", e.getMessage())));
    }
  }
}
