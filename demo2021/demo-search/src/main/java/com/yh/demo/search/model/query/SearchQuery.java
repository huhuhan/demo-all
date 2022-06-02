package com.yh.demo.search.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("搜索参数对象")
public class SearchQuery {
    @ApiModelProperty("分页：第几页，默认第1页")
    private Integer pageNum;
    @ApiModelProperty("分页：每页大小，默认5个")
    private Integer pageSize;
    @NotNull
    @ApiModelProperty("索引")
    private String indexName;
    @ApiModelProperty("关键字")
    private String query;
    @ApiModelProperty("关键字高亮，默认返回")
    private Boolean highlighter;

    public Integer getPageNum() {
        if (null == pageNum) {
            return 1;
        }
        if (pageNum <= 0) {
            return 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (null == pageNum) {
            return 10;
        }
        if (pageNum <= 0) {
            return 5;
        }
        return pageSize;
    }

    public boolean isHighlighter() {
        if (null == highlighter) {
            return false;
        }
        return highlighter;
    }

    public String getIndexName() {
        if (null == indexName) {
            // 测试索引
            return "twitter";
        }
        return indexName;
    }
}
