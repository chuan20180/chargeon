package com.obast.charer.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String userName;

    private String nickName;

    private CustomerTypeEnum type;

    private String mobile;

    private String sex;

    private String avatar;

    @JsonIgnore
    @JsonProperty
    private String password;

    private EnableStatusEnum status;

    private String loginIp;

    private Date loginDate;

    private String logicalCardNo;

    private String physicalCardNo;

    private BigDecimal balanceAmount;

    private BigDecimal giveAmount;

    private List<Quota> quotaAmount;

    private String note;

    @AllArgsConstructor
    @Data
    public static class Quota {

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal amount;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal discount;

        //找出最大的折扣
        public static Quota findMaxDiscount(List<Quota> quotas) {
            if(quotas == null || quotas.isEmpty()) {
                return null;
            }
            Quota maxQuota = null;
            for(Quota quota : quotas) {
                if(maxQuota == null) {
                    maxQuota = quota;
                    continue;
                }
                if(quota.discount.compareTo(new BigDecimal(1)) >= 0 || quota.amount.compareTo(new BigDecimal(0)) <= 0) {
                    continue;
                }
                if(quota.getAmount().multiply(new BigDecimal(1).subtract(quota.discount)).compareTo(maxQuota.getAmount().multiply(new BigDecimal(1).subtract(maxQuota.discount))) > 0) {
                    maxQuota = quota;
                }
            }
            return maxQuota;
        }

        public static List<Quota> add(Quota entity, List<Quota> quotas) {
            if(quotas == null || quotas.isEmpty()) {
                quotas = new ArrayList<>();
            }
            boolean save = false;
            for(Quota quota: quotas) {
                if(quota.getDiscount().compareTo(entity.getDiscount()) == 0) {
                    quota.setAmount(quota.getAmount().add(entity.getAmount()));
                    save = true;
                    break;
                }
            }
            if(!save) {
                quotas.add(entity);
            }

            return quotas;
        }

        public static List<Quota> subtract(Quota entity, List<Quota> quotas) {
            if(quotas == null || quotas.isEmpty()) {
                throw new BizException(ErrCode.CUSTOMER_BALANCE_QUOTA_LIST_EMPTY);
            }
            boolean save = false;
            for(Quota quota: quotas) {
                if(quota.getDiscount().compareTo(entity.getDiscount()) == 0) {
                    if(quota.getAmount().compareTo(entity.getAmount()) < 0) {
                        throw new BizException(ErrCode.CUSTOMER_BALANCE_QUOTA_AMOUNT_NOT_ENOUGH);
                    }
                    quota.setAmount(quota.getAmount().subtract(entity.getAmount()));
                    save = true;
                    break;
                }
            }
            if(!save) {
                throw new BizException(ErrCode.CUSTOMER_BALANCE_QUOTA_AMOUNT_NOT_FIND);
            }

            List<Quota> newQuotaList = new ArrayList<>();
            for(Quota quota: quotas) {
                if(quota.getAmount().compareTo(new BigDecimal(0)) > 0) {
                    newQuotaList.add(quota);
                }
            }
            return newQuotaList;
        }
    }
}
