package com.obast.charer.model.product;

import com.obast.charer.model.Id;
import lombok.Data;

@Data
public class Category implements Id<String> {

    private String id;

    private String name;
}
