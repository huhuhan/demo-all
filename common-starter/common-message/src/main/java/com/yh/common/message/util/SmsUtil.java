package com.yh.common.message.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.yh.common.message.config.AppFactory;
import com.yh.common.message.config.SmsProperties;
import com.yh.common.message.model.dto.SmsTemplateDTO;
import com.yh.common.message.model.vo.SmsDataVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghan
 * @date 2021/7/6
 */
@Slf4j
public class SmsUtil {

    private volatile static Client client;
    private static SmsProperties smsConfig;
    /** 手机号正则 */
    private static final String MOBILE_REGEX = "^1[34578]\\d{9}$";

    private static SmsProperties smsConfig() throws Exception {
        if (null == smsConfig) {
            smsConfig = AppFactory.getBean(SmsProperties.class);
        }
        if (null == smsConfig || !smsConfig.isEnabled()) {
            throw new Exception("短信功能未开启，请检查配置！");
        }
        return smsConfig;
    }

    //初始化客户端配置

    private static Config initConfig() throws Exception {
        SmsProperties smsConfig = smsConfig();
        return new Config()
                .setEndpoint(smsConfig.getEndpoint())
                .setAccessKeyId(smsConfig.getAppId())
                .setAccessKeySecret(smsConfig.getAppKey());
    }

    //单例客户端

    private static Client getSingleClient() throws Exception {
        if (client == null) {
            synchronized (SmsUtil.class) {
                if (client == null) {
                    client = new Client(initConfig());
                }
            }
        }
        return client;
    }

    //多例客户端

    private static Client createClient() throws Exception {
        return new Client(initConfig());
    }


    /**
     * 短信功是否开启
     *
     * @param throwError 是否抛出异常
     * @author yanghan
     * @date 2021/8/25
     */
    public static boolean isEnable(boolean throwError) throws Exception {
        SmsProperties smsConfig = smsConfig();
        boolean isEnable = smsConfig != null || smsConfig.isEnabled() || CollectionUtil.isNotEmpty(smsConfig.getTemplates());
        if (!isEnable && throwError) {
            throw new Exception("短信通知功能：未开启！");
        }
        return isEnable;
    }

    /**
     * 选择短信功能的签名和模板
     *
     * @author yanghan
     * @date 2021/8/25
     */
    public static SmsTemplateDTO getTemplate(String key) throws Exception {
        return smsConfig().getTemplates().get(key);
    }

    /**
     * 发送短信，默认用单例客户端
     *
     * @author yanghan
     * @date 2021/8/25
     */
    public static void send(String mobile, SmsDataVO bo, SmsTemplateDTO smsTemplateDTO) throws Exception {
        send(true, mobile, bo, smsTemplateDTO);
    }

    /**
     * 发送短信
     *
     * @author yanghan
     * @date 2021/8/25
     */
    public static void send(boolean isSingleClient, String mobile, SmsDataVO bo, SmsTemplateDTO smsTemplateDTO) throws Exception {
        checkMobile(mobile, true);
        try {
            Client client = isSingleClient ? getSingleClient() : createClient();
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(mobile)
                    .setSignName(smsTemplateDTO.getSign())
                    .setTemplateCode(smsTemplateDTO.getCode())
                    .setTemplateParam(JSONUtil.toJsonStr(bo));
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            log.info("短信发送结果：{}", response.getBody().getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("短信发送异常，请联系管理员！");
        }
    }

    /**
     * 验证手机号是否符合规范
     *
     * @author yanghan
     * @date 2021/8/25
     */
    public static boolean checkMobile(String mobile, boolean throwError) throws Exception {
        boolean isStandard = StrUtil.isBlank(mobile) || ReUtil.isMatch(MOBILE_REGEX, mobile);
        if (!isStandard && throwError) {
            throw new Exception(String.format("手机号格式不正确：%s", mobile));
        }
        return isStandard;
    }

}