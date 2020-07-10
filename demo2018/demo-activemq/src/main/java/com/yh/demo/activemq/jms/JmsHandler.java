package com.yh.demo.activemq.jms;

import javax.jms.Message;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public interface JmsHandler {

    void handMessage(Message message);
}
