package com.obast.charer.data.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


/**
 * @ Author：chuan
 * @ Date：2024-09-29-11:19
 * @ Version：1.0
 * @ Description：JpaEjbInterceptor
 */
@Slf4j
@Component
public class JpaStatementInterceptor implements StatementInspector {

    private static final Pattern SQL_COMMENT_PATTERN = Pattern.compile("\\/\\*.*?\\*\\/\\s*");

    @Override
    public String inspect(String sql) {
        log.info("sql拦截\nExecuting SQL query: \n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" +
                "\n{}\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" +
                "", sql);

        return SQL_COMMENT_PATTERN
                .matcher(sql)
                .replaceAll("");
    }

}