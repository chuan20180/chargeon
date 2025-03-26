package com.obast.charer.temporal.es.dao;

import com.obast.charer.temporal.es.document.DocThingModelMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ThingModelMessageRepository extends ElasticsearchRepository<DocThingModelMessage, String> {
}
