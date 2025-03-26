package com.obast.charer.model.ledger;

import com.obast.charer.model.DealerModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSettle extends DealerModel implements Id<String>, Serializable {

    private String id;

    private String tranId;

    private BigDecimal amount;


}
