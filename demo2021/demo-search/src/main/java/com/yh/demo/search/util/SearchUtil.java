package com.yh.demo.search.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yh.demo.search.model.query.SearchQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/11/4
 */
public class SearchUtil {
    private static final String HIGHLIGHTER_PRE_TAGS = "<mark>";
    private static final String HIGHLIGHTER_POST_TAGS = "</mark>";

    public static Page<JSONObject> search(RestHighLevelClient client, SearchQuery query) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(query.getIndexName());
        searchRequest.source(searchSourceBuilder);

        // 查询关键字
        QueryBuilder queryBuilder;
        if (StrUtil.isNotEmpty(query.getQuery())) {
            queryBuilder = QueryBuilders.queryStringQuery(query.getQuery());
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        searchSourceBuilder.query(queryBuilder);

        //是否高亮
        if (BooleanUtil.isTrue(query.isHighlighter())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("*").preTags(HIGHLIGHTER_PRE_TAGS).postTags(HIGHLIGHTER_POST_TAGS);
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        //分页
        searchSourceBuilder.from((query.getPageNum() - 1) * query.getPageSize())
                .size(query.getPageSize());
        searchSourceBuilder.trackTotalHits(true);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        client.close();
        SearchHits searchHits = response.getHits();
        long totalCnt = searchHits.getTotalHits().value;
        List<JSONObject> list = getList(searchHits);

        Page<JSONObject> page = new Page<>();
        page.setRecords(list);
        page.setTotal(totalCnt);
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageNum());
        return page;
    }

    /**
     * 返回JSON列表数据
     */
    private static List<JSONObject> getList(SearchHits searchHits) {
        List<JSONObject> list = new ArrayList<>();
        if (searchHits != null) {
            searchHits.forEach(item -> {
                JSONObject obj = JSONUtil.parseObj(item.getSourceAsString());
                obj.set("id", item.getId());

                Map<String, HighlightField> highlightFields = item.getHighlightFields();
                if (highlightFields != null) {
                    populateHighLightedFields(obj, highlightFields);
                }
                list.add(obj);
            });
        }
        return list;
    }

    /**
     * 组装高亮字符
     *
     * @param result          目标对象
     * @param highlightFields 高亮配置
     */
    private static <T> void populateHighLightedFields(T result, Map<String, HighlightField> highlightFields) {
        for (HighlightField field : highlightFields.values()) {
            String name = field.getName();
            if (!name.endsWith(".keyword")) {
                if (result instanceof ObjectNode) {
                    ((ObjectNode) result).put(field.getName(), concat(field.fragments()));
                } else {
                    BeanUtil.setFieldValue(result, field.getName(), concat(field.fragments()));
                }
            }
        }
    }

    /**
     * 拼凑数组为字符串
     */
    private static String concat(Text[] texts) {
        StringBuffer sb = new StringBuffer();
        for (Text text : texts) {
            sb.append(text.toString());
        }
        return sb.toString();
    }
}
