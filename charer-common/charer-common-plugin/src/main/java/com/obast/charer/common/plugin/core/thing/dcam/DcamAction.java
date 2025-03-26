package com.obast.charer.common.plugin.core.thing.dcam;

import com.obast.charer.common.IDeviceAction;
import com.obast.charer.common.ProductType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author sjg
 */
@Data
@SuperBuilder
@ToString(callSuper = true)
public abstract class DcamAction<T> implements IDeviceAction<T> {

    protected String id;

    protected String dn;

    protected  String pk;

    protected ProductType type;

    protected short serial;

    protected Enum<DcamDirectiveEnum> directive;

    protected Long time;

    protected T data;

    @Override
    public void setDirective(Enum<?> directive) {

    }

    @Override
    public ProductType getType() {
        return ProductType.DCAM;
    }
}
