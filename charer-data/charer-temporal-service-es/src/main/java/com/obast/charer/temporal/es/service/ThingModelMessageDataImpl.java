
package com.obast.charer.temporal.es.service;

import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.model.stats.TimeData;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.temporal.IThingModelMessageData;
import com.obast.charer.temporal.es.dao.ThingModelMessageRepository;
import com.obast.charer.temporal.es.document.DocThingModelMessage;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThingModelMessageDataImpl implements IThingModelMessageData {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private ThingModelMessageRepository thingModelMessageRepository;

    @Override
    public Paging<ThingModelMessage> findPage(com.obast.charer.common.api.PageRequest<DeviceLogQueryBo> request) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        DeviceLogQueryBo queryBo = request.getData();
        if(queryBo != null) {
            if(StringUtils.isNoneBlank(queryBo.getInvoke())) {
                builder.must(QueryBuilders.matchPhraseQuery("invoke", queryBo.getInvoke()));
            }

            if(StringUtils.isNoneBlank(queryBo.getDeviceDn())) {
                builder.must(QueryBuilders.matchPhraseQuery("deviceName", queryBo.getDeviceDn()));
            }

            if(StringUtils.isNoneBlank(queryBo.getIdentifier())) {
                builder.must(QueryBuilders.matchPhraseQuery("identifier", queryBo.getIdentifier()));
            }

            if(StringUtils.isNoneBlank(queryBo.getType())) {
                builder.must(QueryBuilders.matchPhraseQuery("type", queryBo.getType()));
            }
        }

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).withPageable(PageRequest.of(request.getPageNum() - 1, request.getPageSize(), Sort.by(Sort.Order.desc("time")))).build();
        SearchHits<DocThingModelMessage> result = template.search(query, DocThingModelMessage.class);
        return new Paging<>(result.getTotalHits(), result.getSearchHits().stream()
                .map(m -> MapstructUtils.convert(m.getContent(), ThingModelMessage.class))
                .collect(Collectors.toList()));

    }

    @Override
    public Paging<ThingModelMessage> findByTypeAndIdentifier(String deviceId, String type, String identifier, int page, int size) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(StringUtils.isNoneBlank()) {
            builder.must(QueryBuilders.termQuery("deviceId", deviceId));
        }

        if (StringUtils.isNotBlank(type)) {
            builder.must(QueryBuilders.termQuery("type", type));
        }
        if (StringUtils.isNotBlank(identifier)) {
            builder.must(QueryBuilders.matchPhraseQuery("identifier", identifier));
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).withPageable(PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("time")))).build();
        SearchHits<DocThingModelMessage> result = template.search(query, DocThingModelMessage.class);
        return new Paging<>(result.getTotalHits(), result.getSearchHits().stream()
                .map(m -> MapstructUtils.convert(m.getContent(), ThingModelMessage.class))
                .collect(Collectors.toList()));
    }

    @Override
    public Paging<ThingModelMessage> findByTypeAndDeviceIds(List<String> deviceIds, String type,
                                                             String identifier,
                                                             int page, int size) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("type", type));
        if (deviceIds.size()>0) {
            builder.must(QueryBuilders.termsQuery("deviceId", deviceIds));
        }
        if (StringUtils.isNotBlank(identifier)) {
            builder.must(QueryBuilders.matchPhraseQuery("identifier", identifier));
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder)
                .withPageable(PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("time"))))
                .build();
        SearchHits<DocThingModelMessage> result = template.search(query, DocThingModelMessage.class);
        return new Paging<>(result.getTotalHits(), result.getSearchHits().stream()
                .map(m -> MapstructUtils.convert(m.getContent(), ThingModelMessage.class))
                .collect(Collectors.toList()));
    }

    @Override
    public List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("time")
                        .from(start, true).to(end, true));
        if (uid != null) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        //按小时统计消息数量
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            queryBuilder.must(QueryBuilders.rangeQuery("time")
                    .from(start, true).to(end, true));
        }

        if ( ObjectUtil.isNotEmpty(uid) ) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        // 查询字段type='property' and identifier='report', 或者 type='push' 的数据
        queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("type", "property"))
                        .must(QueryBuilders.termQuery("identifier", "report")))
                .should(QueryBuilders.termQuery("type", "push")));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            queryBuilder.must(QueryBuilders.rangeQuery("time")
                    .from(start, true).to(end, true));
        }

        if ( ObjectUtil.isNotEmpty(uid) ) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        // 查询字段type='property' and identifie!='report',  或者 type='service' 或者 type= 'config'
        queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("type", "property"))
                        .must(QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.termQuery("identifier", "report"))))
                .should(QueryBuilders.termQuery("type", "service"))
                .should(QueryBuilders.termQuery("type", "config")));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public void add(ThingModelMessage msg) {
        thingModelMessageRepository.save(MapstructUtils.convert(msg, DocThingModelMessage.class));
    }

    @Override
    public long count() {
        return thingModelMessageRepository.count();
    }
}
