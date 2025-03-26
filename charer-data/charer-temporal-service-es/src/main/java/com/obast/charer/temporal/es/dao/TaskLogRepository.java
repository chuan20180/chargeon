package com.obast.charer.temporal.es.dao;

import com.obast.charer.temporal.es.document.DocTaskLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskLogRepository extends ElasticsearchRepository<DocTaskLog, String> {

    void deleteByTaskId(String taskId);

    Page<DocTaskLog> findByTaskIdOrderByLogAtDesc(String taskId, Pageable pageable);

}
