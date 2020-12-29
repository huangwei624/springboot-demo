package com.middleyun.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private List<Twitter> twitters = Arrays.asList(
            new Twitter(1, "ThreadLocal 类源码解析2",
                    "这个一篇关于ThreadLocal的源码解析文档，读了这篇文档，你会对ThreadLocal有不一样的认识！",
                    100, 200, "张三",
                    Date.from(LocalDateTime.of(2020, 12, 20, 10, 12, 30)
                            .atZone(ZoneId.systemDefault()).toInstant())),
            new Twitter(2, "ElasticSearch 深入浅出",
                    "Elasticsearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java语言开发的，并作为Apache许可条款下的开放源码发布，是一种流行的企业级搜索引擎。Elasticsearch用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。官方客户端在Java、.NET（C#）、PHP、Python、Apache Groovy、Ruby和许多其他语言中都是可用的。根据DB-Engines的排名显示，Elasticsearch是最受欢迎的企业搜索引擎，其次是Apache Solr，也是基于Lucene。",
                    87, 300, "李四",
                    Date.from(LocalDateTime.of(2020, 12, 22, 13, 13, 30)
                            .atZone(ZoneId.systemDefault()).toInstant())),
            new Twitter(3, "中国说",
                    "我是一名中国人, 我爱我的国家！",
                    1090, 453, "王五",
                    Date.from(LocalDateTime.of(2020, 12, 23, 18, 12, 30)
                            .atZone(ZoneId.systemDefault()).toInstant())),
            new Twitter(4, "国际论",
                    "每个国家都有各自不同文化！！",
                    10110, 4513, "张三",
                    Date.from(LocalDateTime.of(2020, 12, 24, 19, 12, 30)
                            .atZone(ZoneId.systemDefault()).toInstant())));

    @SuppressWarnings("all")
    private final String INDEX_NAME = "twitter";

    @Test
    void createIndex() throws IOException {

        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (exists) {
            System.out.println("=== 索引已经存在了！");
            System.out.println("=== 删除索引");
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(new DeleteIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
            if (!delete.isAcknowledged()) {
                throw new RuntimeException("索引删除失败");
            }
            System.out.println("索引删除成功， 开始重建索引");
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        // setting 设置
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 1).build());

        // mapping 设置
        createIndexRequest.mapping(XContentFactory.jsonBuilder().startObject()
                .startObject("properties")
                    .startObject("id").field("type", "integer").endObject()
                    .startObject("pageViews").field("type", "integer").endObject()
                    .startObject("startNums").field("type", "integer").endObject()
                    .startObject("createTime").field("type", "date").endObject()
                    .startObject("title").field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .field("search_analyzer", "ik_smart").endObject()
                    .startObject("content").field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .field("search_analyzer", "ik_smart").endObject()
                    .startObject("createUser").field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .field("search_analyzer", "ik_smart").endObject()
                .endObject().endObject());
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        if (createIndexResponse.isAcknowledged()) {
            System.out.println("索引创建成功");
        } else {
            throw new RuntimeException("索引创建失败！");
        }
    }

    @Test
    void createDocument() {
        twitters.forEach(item -> {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
            indexRequest.id(String.valueOf(item.getId()))
                    .source(JSON.toJSONString(item), XContentType.JSON)
                    .timeout(TimeValue.timeValueSeconds(5));
            try {
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void deleteDocument() {
        IntStream.rangeClosed(1, 4).forEach(item -> {
            DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME);
            deleteRequest.id(String.valueOf(item));
            deleteRequest.timeout(TimeValue.timeValueSeconds(5));
            try {
                restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void bulkCreateDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest(INDEX_NAME);
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));
        IntStream.rangeClosed(1, 4).forEach(item -> {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.id(String.valueOf(item));
            indexRequest.source(JSON.toJSONString(twitters.get(item-1)), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Test
    void bulkDeleteDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest(INDEX_NAME);
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));
        IntStream.rangeClosed(1, 4).forEach(item -> {
            DeleteRequest deleteRequest = new DeleteRequest();
            deleteRequest.id(String.valueOf(item));
            bulkRequest.add(deleteRequest);
        });
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulkResponse.hasFailures()) {
            Stream.of(bulkResponse.getItems()).forEach(item -> {
                System.out.println(item.getFailureMessage());
            });
        }
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, String.valueOf(1));
        Twitter twitter = twitters.get(0);
        twitter.setTitle("ThreadLocal 类源码解析3");
        twitter.setCreateTime(new Date(System.currentTimeMillis()));
        updateRequest.doc(JSON.toJSONString(twitter), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        if (updateResponse.status().getStatus() == 200) {
            System.out.println("更新成功");
        }
    }

    @Test
    void matchQuery1() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "中国"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits().getHits().length > 0) {
            SearchHit[] hits = searchResponse.getHits().getHits();
            Stream.of(hits).forEach(item -> {
                Twitter twitter = JSONObject.parseObject(item.getSourceAsString(), Twitter.class);
                System.out.println(twitter);
            });
        } else {
            System.out.println("未查询到任何结果");
        }
    }

    @Test
    void matchQuery2() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("content", "国家"))
                .should(QueryBuilders.matchQuery("content", "语言"))
                .filter(QueryBuilders.rangeQuery("pageViews").gte(1000))).from(0).size(2)
                .sort(new ScoreSortBuilder().order(SortOrder.DESC))
                .sort(new FieldSortBuilder("pageViews").order(SortOrder.ASC));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits().getHits().length > 0) {
            SearchHit[] hits = searchResponse.getHits().getHits();
            Stream.of(hits).forEach(item -> {
                Twitter twitter = JSONObject.parseObject(item.getSourceAsString(), Twitter.class);
                System.out.println("score: "+item.getScore() +", " +twitter);
            });
        } else {
            System.out.println("未查询到任何结果");
        }
    }

    @Test
    void termQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("content", "我是一名中国人，我爱我的国家和语言！")));
//                .filter(QueryBuilders.rangeQuery("pageViews").gte(1000))).from(1).size(1);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits().getHits().length > 0) {
            SearchHit[] hits = searchResponse.getHits().getHits();
            Stream.of(hits).forEach(item -> {
                Twitter twitter = JSONObject.parseObject(item.getSourceAsString(), Twitter.class);
                System.out.println(twitter);
            });
        } else {
            System.out.println("未查询到任何结果");
        }
    }

    @Test
    void highLightQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));

        // 高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color: red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("title").field("content");

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("content", "国家"))
                .should(QueryBuilders.matchQuery("content", "语言"))
                .filter(QueryBuilders.rangeQuery("pageViews").gte(1000))).from(1).size(1)
                .highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits().getHits().length > 0) {
            SearchHit[] hits = searchResponse.getHits().getHits();
            Stream.of(hits).forEach(item -> {
                Twitter twitter = JSONObject.parseObject(item.getSourceAsString(), Twitter.class);
                System.out.println(twitter);
                Map<String, HighlightField> highlightFields = item.getHighlightFields();
                highlightFields.keySet().forEach(key -> {
                    System.out.println(key+": "+Arrays.toString(highlightFields.get(key).getFragments()));
                });
            });
        } else {
            System.out.println("未查询到任何结果");
        }
    }
}
