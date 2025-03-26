package com.obast.charer.openapi.dto.vo;

import com.obast.charer.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCodeVo implements Serializable {

    private Boolean hasAccount;

    private String accessToken;

    private Customer customer;


}
