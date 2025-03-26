package com.obast.charer.openapi.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMobileVo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String messageId;

    private String mobile;

    private String tel;



}
