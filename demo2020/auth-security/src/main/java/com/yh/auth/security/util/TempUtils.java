package com.yh.auth.security.util;

import com.google.common.collect.Maps;
import org.slf4j.Logger;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 本项目临时需要的公共方法工具类
 * 后续接入项目，替换后项目的统一工具类方法
 * @author yanghan
 * @date 2020/6/2
 */
public class TempUtils {

    public static void responseWriter(ServletResponse response, String ...msg) throws IOException {
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error by TempUtils\",\"msg\":\"");

        if(msg != null && msg.length > 0){
            for (String s : msg) {
                sb.append(s);
            }
        }

        sb.append("\"}");
        out.write(sb.toString());
        out.flush();
        out.close();
    }

    public static void logDebug(Logger log, String msg) {
        if(log.isDebugEnabled()){
            log.debug(msg);
        }
    }


    public static Map R2success(String msg, Object data){
        Map<String, Object> resultMsg = Maps.newHashMap();
        resultMsg.put("ok", true);
        resultMsg.put("msg", msg);
        if(null != data){
            resultMsg.put("data", data);
        }
        return resultMsg;
    }

}
