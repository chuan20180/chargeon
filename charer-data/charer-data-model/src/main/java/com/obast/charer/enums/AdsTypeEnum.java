package com.obast.charer.enums;


import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：启用/停用枚举
 */

@Getter
public enum AdsTypeEnum implements IBaseEnum<String> {
    Image("image", "图片", "primary", "Y"),
    Html("html", "HTML内容", "success", "N"),
    OutLink("out_link", "外部链接", "info", "N")
    ;

    private final String code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    AdsTypeEnum(String code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<AdsTypeEnum, String> {

        public Converter() {
            super(AdsTypeEnum.class);
        }
    }
}