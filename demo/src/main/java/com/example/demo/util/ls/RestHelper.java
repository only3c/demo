package com.example.demo.util.ls;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RestHelper {

    /**
     * 判断rest请求的结果是否正确
     *
     * @param map
     */
    @SuppressWarnings("rawtypes")
    public static boolean isSuccess(final Map map) {
        return ((map.get("status") != null) && "success".equals(map.get("status").toString()));
    }

    /**
     * 生成请求体
     *
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static HttpEntity<String> generateEntity(final Map params) {
        //final HttpEntity<String> formEntity;
        if (params == null) {
            //formEntity = new HttpEntity<>("");
        } else {
            final HttpHeaders headers = new HttpHeaders();
            final MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            //formEntity = new HttpEntity<>(JsonUtils.pojoToJson(params), headers);
        }
        return null;
    }

    /**
     * 生成请求体
     *
     * @param params
     * @return
     */
    public static HttpEntity<String> generateEntity(final String params) {
        final HttpEntity<String> formEntity;
        if (StringUtils.isBlank(params)) {
            formEntity = new HttpEntity<>("");
        } else {
            final HttpHeaders headers = new HttpHeaders();
            final MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            formEntity = new HttpEntity<>(params, headers);
        }
        return formEntity;
    }

    /**
     * 生成请求体
     *
     * @return
     */
    public static HttpEntity<String> generateEntity() {
        return RestHelper.generateEntity("");
    }
}
