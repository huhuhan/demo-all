package com.yh.demo.search.controller;

import cn.hutool.core.util.StrUtil;
import com.yh.demo.search.model.query.SearchQuery;
import com.yh.demo.search.util.SearchUtil;
import io.swagger.annotations.Api;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = "搜索接口")
@RequestMapping("/api/es")
@RestController
public class SearchController {

    @Value("${elasticsearch.ip:}")
    private String esIp;
    @Value("${elasticsearch.port:}")
    private Integer esPort;


    @PostMapping("/search")
    public Object strQuery(@Valid @RequestBody SearchQuery query) throws Exception {
        RestHighLevelClient client = getClient();
        return SearchUtil.search(client, query);
    }

    private RestHighLevelClient getClient() throws Exception {
        if (StrUtil.isNotBlank(esIp) && null != esPort) {
            return new RestHighLevelClient(RestClient.builder(new HttpHost(esIp, esPort, "http")));
        }
        throw new Exception("Elastic 服务不可用！请联系管理员");
    }


}

