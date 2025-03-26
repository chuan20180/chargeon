package com.obast.charer.model.order;

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.enums.*;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends AgentModel implements Id<String>, Serializable {

    private String id;

    private ChargePayTypeEnum chargePayType;
    private ChargeStartTypeEnum chargeStartType;
    private ChargeStopTypeEnum chargeStopType;

    private String chargerName;
    private String chargerDn;
    private String customerId;
    private String customerLoginId;
    private String userName;
    private String gunNo;

    private String parkId;
    private String tranId;
    private String priceId;
    private PriceProperties priceProperties;
    private String stationId;
    private I18nField stationName;
    private I18nField stationAddress;
    private OrderTranTypeEnum tranType;
    private Date tranTime;
    private Date startTime;
    private Date endTime;
    private BigDecimal sharpPrice;
    private BigDecimal sharpQty;
    private BigDecimal sharpQuantity;
    private BigDecimal sharpAmount;
    private BigDecimal peakPrice;
    private BigDecimal peakQty;
    private BigDecimal peakQuantity;
    private BigDecimal peakAmount;
    private BigDecimal flatPrice;
    private BigDecimal flatQty;
    private BigDecimal flatQuantity;
    private BigDecimal flatAmount;
    private BigDecimal valleyPrice;
    private BigDecimal valleyQty;
    private BigDecimal valleyQuantity;
    private BigDecimal valleyAmount;
    private BigDecimal quantityStart;
    private BigDecimal quantityEnd;
    private BigDecimal totalQty;
    private BigDecimal totalQuantity;
    private BigDecimal totalAmount;
    private BigDecimal elecAmount;
    private BigDecimal serviceAmount;


    private BigDecimal orderAmount;
    private BigDecimal parkAmount;
    private BigDecimal debitAmount;

    private BigDecimal settledAmount;
    private BigDecimal settledElecAmount;
    private BigDecimal settledServiceAmount;
    private BigDecimal settledParkAmount;

    private BigDecimal discountAmount;
    private BigDecimal discountElecAmount;
    private BigDecimal discountServiceAmount;
    private BigDecimal discountParkAmount;

    private Float voltage;
    private Float current;
    private Float power;

    private String vin;

    private String stopReason;

    private String physicalCardNo;

    private OrderStateEnum state;

    private OrderDealEnum deal;

    private OrderNotifyEnum notify;


    private Short soc;

    private Short chargeMinute;

    private Short remainMinute;

    private String note;
}
