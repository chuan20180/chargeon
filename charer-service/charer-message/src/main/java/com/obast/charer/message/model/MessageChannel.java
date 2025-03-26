package com.obast.charer.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * author: 石恒
 * date: 2023-05-08 15:58
 * description:
 **/
@Data
@AllArgsConstructor
public class MessageChannel implements Serializable {

    private String identifier;

    private String properties;

}
