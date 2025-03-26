package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.NoticeStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeQueryBo extends BaseDto {

    private String title;

    private String content;

    private NoticeStateEnum state;
}
