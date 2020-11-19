package com.yh.demo.ureport.provider;

import com.bstek.ureport.provider.image.ImageProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo: 待测试
 * 获取图片流
 */
@Component
@Slf4j
public class HttpImageProvider implements ImageProvider {

    @Override
    public InputStream getImage(String imageUrl) {
        Pattern pattern = Pattern.compile("/");
        Matcher findMatcher = pattern.matcher(imageUrl);
        int number = 0;
        while (findMatcher.find()) {
            number++;
            //当“/”第二次出现时停止
            if (number == 3) {
                break;
            }
        }
        int start = findMatcher.start();
        String preUrl = imageUrl.substring(0, start + 1);
        String file = imageUrl.substring(start + 1, imageUrl.length());
        HttpURLConnection connection = null;
        ByteArrayInputStream bio = null;
        ByteArrayOutputStream output;
        try {
            String url = preUrl + URLEncoder.encode(file, "utf-8");
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            log.info("正在解析图片流,图片地址:{}", url);
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStream inputStream = connection.getInputStream();
                output = new ByteArrayOutputStream();
                IOUtils.copy(inputStream, output);
                bio = new ByteArrayInputStream(output.toByteArray());
            } else {
                log.info("解析图片错误,图片地址:{}", url);
                output = new ByteArrayOutputStream();
                InputStream page404 = get404Page();
                if (page404 != null) {
                    IOUtils.copy(page404, output);
                    bio = new ByteArrayInputStream(output.toByteArray());
                }
            }
            return bio;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            output = new ByteArrayOutputStream();
            InputStream page404 = get404Page();
            if (page404 != null) {
                IOUtils.copy(page404, output);
                bio = new ByteArrayInputStream(output.toByteArray());
                return bio;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ureport支持当前的provider
     *
     * @param path path
     * @return true
     */
    @Override
    public boolean support(String path) {
        return true;
    }

    /**
     * 当获取不到图片时候，返回404page页面
     *
     * @return InputStream
     */
    private InputStream get404Page() {
        ClassPathResource classPathResource = new ClassPathResource("static/404.png");
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
