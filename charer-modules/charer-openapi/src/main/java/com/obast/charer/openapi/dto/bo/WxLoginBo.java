package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class WxLoginBo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String encryptedData;

    private String iv;

    private String sessionKey;

    private String openId;


}
