package com.yh.common.message;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.yh.common.message.model.constant.SmsConstant;
import com.yh.common.message.model.dto.SmsTemplateDTO;
import com.yh.common.message.model.vo.SmsCodeVO;
import com.yh.common.message.model.vo.SmsDataVO;
import com.yh.common.message.util.SmsUtil;
import com.yh.common.message.util.SystemEmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanghan
 * @date 2022/1/20
 */
@RunWith(SpringRunner.class)
// 为了测试读取配置，指定TestApplication，并扫描test包下的目录
@SpringBootTest(classes = TestApplication.class)
@ComponentScan
public class TestApplication {

    @Autowired
    public ApplicationContext context;


    @Test
    public void test() throws Exception {
        SystemEmailUtil.send(CollectionUtil.newArrayList("yh@xxxx.com"), "test", "test");
    }

    @Test
    public void test2() throws Exception {
        String mobile = "148429428451";
        String code = RandomUtil.randomNumbers(6);
        SmsDataVO bo = new SmsCodeVO();
        bo.setCode(code);
        SmsTemplateDTO templateDTO = SmsUtil.getTemplate(SmsConstant.MODIFY_PWD_TEMPLATE);
        SmsUtil.send(mobile, bo, templateDTO);
    }
}