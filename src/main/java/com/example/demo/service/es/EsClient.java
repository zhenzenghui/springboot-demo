package com.example.demo.service.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.recycler.Recycler;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zzh
 * @date 2021/5/26
 */
@Component
public class EsClient {
    @Resource
    private RestHighLevelClient restHighLevelClient;
    private final String table = "jd_table";

    public String createIndex(Recycler.V value) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(table);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest,
                RequestOptions.DEFAULT);
        return createIndexResponse.index();
    }

    /**
     * 保存
     *
     * @param value
     * @return
     * @throws IOException
     */
    public Object save(Object value) throws IOException {
        IndexRequest indexRequest = new IndexRequest(table);
        IndexRequest source = indexRequest.source(JSON.toJSONString(value), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return index.getId();
    }

    public boolean existsIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(table);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return exists;

    }

    public Object getDocById(String id) throws IOException {
        GetRequest getRequest = new GetRequest(table, id);
        //不获取_source上下文
        //  getRequest.fetchSourceContext(new FetchSourceContext(false));
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return documentFields.getSource() != null ? documentFields.getSource() : null;
    }

    public String updateDocById(Object obj, String id) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(table, id);
        updateRequest.doc(JSON.toJSONString(obj), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.getId() != null ? updateResponse.getId() : null;
    }

    public String deletDocById(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(table, id);
        DeleteResponse updateResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return updateResponse.getId() != null ? updateResponse.getId() : null;
    }

    public boolean saveBulk(List<Object> values) throws IOException {
        BulkRequest bulkRequest = new BulkRequest(table);
        for (int i = 0; i < values.size(); i++) {
            bulkRequest.add(new IndexRequest(table).id("" + i).source(JSON.toJSONString(values.get(i)),
                    XContentType.JSON));
        }
        bulkRequest.timeout("30s");
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    public <T> List<T> searchTerm(String keyword, String fild, Class<T> claz) throws IOException {
        SearchRequest request = new SearchRequest(table);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(fild, keyword);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.query(termQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<T> lists = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            T result = JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap), claz);
            lists.add(result);
        }
        return lists;
    }

    public <T> List<T> searchMatch(String keyword, String fild, Integer form, Integer size, Class<T> claz) throws IOException {
        SearchRequest request = new SearchRequest(table);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(size);
        searchSourceBuilder.from(form);
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fild, keyword);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.query(matchQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<T> lists = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            T result = JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap), claz);
            lists.add(result);
        }
        return lists;
    }


    public <T> List<T> searchMatchHeightLight(String keyword, String fild, Integer form, Integer size, Class<T> claz) throws IOException {
        SearchRequest request = new SearchRequest(table);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.size(size);
        searchSourceBuilder.from(form);

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fild, keyword);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.query(matchQueryBuilder);


        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(fild);
        highlightBuilder.preTags("<span style='colo : red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        request.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<T> lists = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField fields = highlightFields.get(fild);
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (fields != null) {
                Text[] fragments = fields.fragments();
                StringBuilder n_fields = new StringBuilder();
                for (Text text : fragments) {
                    n_fields.append(text);
                }
                sourceAsMap.put(fild, n_fields);
            }
            T result = JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap), claz);

            lists.add(result);
        }
        return lists;
    }

    public <T> List<T> searchMatchAll(Class<T> claz) throws IOException {
        SearchRequest request = new SearchRequest(table);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.query(matchAllQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<T> lists = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            T result = JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap), claz);
            lists.add(result);
        }
        return lists;
    }

}
