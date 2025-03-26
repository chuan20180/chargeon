package com.obast.charer.temporal.es.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Lazy
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.obast.charer.temporal.es.dao", includeFilters =
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
public class ElasticsearchConfiguration {
}