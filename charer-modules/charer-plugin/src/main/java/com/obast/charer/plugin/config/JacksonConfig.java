package com.obast.charer.plugin.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.properties.I18nLocaleProperties;
import com.obast.charer.common.web.serializer.BigNumberSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * jackson 配置
 */
@Slf4j
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class JacksonConfig {

    @Autowired
    CharerProperties charerProperties;

    public TimeZone getTimeZone() {
        String language = charerProperties.getSystem().getI18n().getLanguage();
        I18nLocaleProperties i18nLocale = Arrays.stream(charerProperties.getSystem().getI18n().getLocales()).filter(item->item.getLocale().equals(language)).findFirst().orElse(null);
        TimeZone timeZone = TimeZone.getTimeZone(Constants.TIMEZONE);
        if(i18nLocale != null) {
            timeZone = TimeZone.getTimeZone(i18nLocale.getTimezone());
        }
        return timeZone;
    }

    public String getDateFormat() {
        String language = charerProperties.getSystem().getI18n().getLanguage();
        I18nLocaleProperties i18nLocale = Arrays.stream(charerProperties.getSystem().getI18n().getLocales()).filter(item->item.getLocale().equals(language)).findFirst().orElse(null);
        String dateFormat = Constants.DATE_FORMAT;
        if(i18nLocale != null) {
            dateFormat = i18nLocale.getDateFormat();
        }
        return dateFormat;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 全局配置序列化返回 JSON 处理
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
            javaTimeModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
            javaTimeModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
            javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormat());
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            builder.modules(javaTimeModule);
            builder.timeZone(getTimeZone());
        };
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(getTimeZone());
        objectMapper.setDateFormat(sdf);

        //忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Bean
    public void setDefaultTimeZone() {
        TimeZone.setDefault(getTimeZone());
    }
}
