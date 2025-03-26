package com.obast.charer.openapi.config.request;

import com.obast.charer.common.enums.PlatformTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：RequestLocale
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLocale {

    @Nullable
    private String apiId;

    @Nullable
    private String tenantId;

    @Nullable
    private String device;

    @Nullable
    private String os;

    @Nullable
    private PlatformTypeEnum platform;

    @Nullable
    private String version;

    @Nullable
    private String language;
}
