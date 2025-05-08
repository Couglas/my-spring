package com.spring.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * web工具类
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class WebUtils {
    public static Map<String, Object> getParametersStartWith(HttpServletRequest req, String prefix) {
        Enumeration<String> paramNames = req.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }

        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (prefix.isEmpty() || paramName.startsWith(prefix)) {
                String unPrefixed = paramName.substring(prefix.length());
                String value = req.getParameter(paramName);

                params.put(unPrefixed, value);
            }
        }
        return params;
    }
}
