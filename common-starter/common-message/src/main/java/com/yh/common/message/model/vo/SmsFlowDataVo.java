package com.yh.common.message.model.vo;

import lombok.Data;

/**
 * 短信模板【流程待办通知】变量参数对象
 *
 * @author yanghan
 * @date 2021/7/6
 */
@Data
public class SmsFlowDataVo implements SmsDataVo {
    /** 流程标题 */
    private String name;
    /** 上个节点 */
    private String taskName;
    /** 处理人 */
    private String userName;
    /** 处理结果 */
    private String taskResult;
    /** 流程标题 */
    private String subject;

    public void setName(String name) {
        this.name = name;
        this.subject = name;
    }

    public void setSubject(String subject) {
        this.name = subject;
        this.subject = name;
    }

    @Override
    public void setCode(String code) {

    }
}
