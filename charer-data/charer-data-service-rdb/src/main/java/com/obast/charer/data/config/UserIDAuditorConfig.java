package com.obast.charer.data.config;

import com.obast.charer.common.satoken.util.LoginHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @Author：tfd
 * @Date：2024/1/12 15:06
 */
@Configuration
public class UserIDAuditorConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = LoginHelper.getUserId();
        return Optional.of(userId == null ? "1" : userId);
    }
}
