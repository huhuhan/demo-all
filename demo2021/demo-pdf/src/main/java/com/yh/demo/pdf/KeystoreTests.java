package com.yh.demo.pdf;

import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.MakeSignature;

import javax.swing.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * 测试类
 *
 * @author yanghan
 * @date 2021/5/20
 */
public class KeystoreTests {

    private static final String KEYSTORE = "demo2021/demo-pdf/sign/test.keystore";
    /** keystore密码 */
    private static final char[] PASSWORD = "111111".toCharArray();
    /** 需要签名的PDF路径 */
    private static final String SRC = "demo2021/demo-pdf/sign/test.pdf";
    /** 完成签名的PDF路径 */
    private static final String OUTPUT_SRC = "demo2021/demo-pdf/sign/test-sign.pdf";
    /** 签章图片 */
    private static final String IMG = "demo2021/demo-pdf/sign/logo.jpg";


    public static void main(String[] args) {
        System.out.println("相对根目录：" + System.getProperty("user.dir"));
        try {
            //读取keystore ，获得私钥和证书链
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(KEYSTORE), PASSWORD);
            String alias = keyStore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, PASSWORD);
            Certificate[] chain = keyStore.getCertificateChain(alias);
            // 合成新pdf
            KeystoreUtils keystoreUtils = new KeystoreUtils();
            keystoreUtils.sign(SRC, OUTPUT_SRC, IMG, chain, privateKey, DigestAlgorithms.SHA1, null, MakeSignature.CryptoStandard.CMS, "Test", "Ghent");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
}
