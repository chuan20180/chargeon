package com.obast.charer.model.product;

import com.obast.charer.model.Owned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppDesign implements Owned<String> {

    private String id;

    private String productKey;

    private String template;

    private String uid;

    private Boolean state;

    private Long modifyAt;

}
