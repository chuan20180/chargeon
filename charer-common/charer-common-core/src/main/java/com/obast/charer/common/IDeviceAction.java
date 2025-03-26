package com.obast.charer.common;

/**
 * 设备行为
 *
 * @author sjg
 */
public interface IDeviceAction<T> {

    /**
     * 获取唯一标识id
     *
     * @return id
     */
    String getId();

    /**
     * 设置id
     *
     * @param id id
     */
    void setId(String id);

    /**
     * 获取类型
     *
     * @return IActionType
     */
    ProductType getType();

    /**
     * 设备类型
     *
     * @param type type
     */
    void setType(ProductType type);

    /**
     * 获取产品
     */
    Enum getDirective();

    /**
     * 设置产品
     *
     */
    void setDirective(Enum<?> directive);

    /**
     * 获取设备DN
     *
     * @return DN
     */
    String getDn();

    /**
     * 设置设备DN
     *
     * @param deviceName dn
     */
    void setPk(String pk);

    /**
     * 获取设备DN
     *
     * @return DN
     */
    String getPk();

    /**
     * 设置设备DN
     *
     * @param deviceName dn
     */
    void setDn(String dn);

    /**
     * 获取时间
     *
     * @return timespan
     */
    Long getTime();


    /**
     * 设置时间
     *
     * @param time timestamp
     */
    void setTime(Long time);


    /**
     * 设置时间
     *
     */
    void setData(T pack);


    T getData();



}
