package com.yh.common.message.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.yh.common.message.config.AppFactory;
import com.yh.common.message.config.EmailProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 系统邮箱工具类
 *
 * @author yanghan
 * @date 2022/1/20
 */
@Slf4j
public class SystemEmailUtil {

    private static EmailProperties emailPo;
    private static MailAccount systemAccount;

    private static EmailProperties initEmailPo() throws Exception {
        if (null == emailPo) {
            emailPo = AppFactory.getBean(EmailProperties.class);
        }

        if (null == emailPo || !emailPo.isEnabled()) {
            throw new Exception("邮箱功能未开启，请检查配置！");
        }
        return emailPo;
    }

    private static MailAccount systemAccount() throws Exception {
        EmailProperties emailPo = initEmailPo();
        if (systemAccount == null) {
            synchronized (SystemEmailUtil.class) {
                if (systemAccount == null) {
                    systemAccount = new MailAccount();
                    systemAccount.setHost(emailPo.getHost());
                    systemAccount.setPort(emailPo.getPort());
                    systemAccount.setFrom(emailPo.getNickName());
                    systemAccount.setUser(emailPo.getAddress());
                    systemAccount.setPass(emailPo.getPassword());
                    systemAccount.setSslEnable(true);
                }
            }
        }
        return systemAccount;
    }


    /**
     * 发送邮件，更多方法，参考{@link MailUtil}
     *
     * @param emails  目标邮箱地址集合
     * @param subject 主题，支持HTML
     * @param content 内容，支持HTML
     * @throws Exception e
     */
    public static void send(List<String> emails, String subject, String content) throws Exception {
        MailUtil.send(systemAccount(), emails, subject, content, true);
        log.info("邮件发送结果：ok");
    }


    /**
     * 发送验证码
     *
     * @param email  邮箱
     * @param code   验证码
     * @param minute 分钟
     * @author yanghan
     * @date 2021/8/24
     */
    public static void sendCaptcha(String email, String code, int minute) throws Exception {
        EmailProperties emailPo = initEmailPo();
        String subject = emailPo.getSystemTitle() + " 邮箱验证码";
        String content = String.format("<p>您的邮箱验证码为：%s</p><p>该验证码 %s 分钟内有效。为了保障您的账户安全，请勿向他人泄漏验证码信息。</p>", code, minute);
        send(CollectionUtil.newArrayList(email), subject, content);
    }
}
