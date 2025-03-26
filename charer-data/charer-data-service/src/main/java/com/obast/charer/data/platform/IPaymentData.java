package com.obast.charer.data.platform;

import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.payment.Payment;

/**
 * 租户数据接口
 *
 * @author tfd
 */
public interface IPaymentData extends ICommonData<Payment, String>, IJPACommonData<Payment, PaymentQueryBo, String> {

    Payment findByIdentifier(PaymentIdentifierEnum identifier);
}
