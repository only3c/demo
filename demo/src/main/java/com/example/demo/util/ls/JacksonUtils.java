package com.example.demo.util.ls;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Map;

// TODO 需要注意时间的转换问题
// TODO 未完整测试，基础转换接口良好

/**
 * JSON 工具类。
 */
public class JacksonUtils {

    /** Map type */
    public static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };
    /** 普通对象 Mapper */
    private static ObjectMapper mapper;
    /** 可过滤的对象 Mapper */
    private static ObjectMapper filterMapper;

    /**
     * 私有构造函数。
     */
    private JacksonUtils() {
    }

    /**
     * 对象实例转JSON字符串。
     *
     * @param pojo 对象实例
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJson(final T pojo) {
        if (pojo == null) {
            return null;
        }
        try {
            final String json = JacksonUtils.getMapper().writeValueAsString(pojo);
            return json;
        } catch (final IOException e) {
            throw new RuntimeException("Failed to convert Object2JSONString. ", e);
        }
    }

    /**
     * JSON字符串转对象实例。
     *
     * @param json JSON串
     * @param pojoClass 对象类型
     * @param <T> 对象类型
     * @return 转换的对象实例
     */
    public static <T> T jsonToPojo(final String json, final Class<T> pojoClass) {
        if ((json == null) || json.trim().equals("")) {
            return null;
        }
        try {
            return JacksonUtils.getMapper().readValue(json, pojoClass);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to convert JSONString2Object. ", e);
        }
    }

    /**
     * JSON字符串转对象实例。
     *
     * @param pojoClass 对象类型
     * @param <T> 对象类型
     * @return 转换的对象实例
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public static <T> T jsonToPojo(final Reader src, final Class<T> pojoClass) throws
            IOException {
        return JacksonUtils.getMapper().readValue(src, pojoClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonToPojo(final String json, final TypeReference<T> valueTypeRef) {
        if ((json == null) || json.trim().equals("")) {
            return null;
        }
        try {
            return (T) JacksonUtils.getMapper().readValue(json, valueTypeRef);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to convert JSONString2Object. ", e);
        }
    }

    /**
     * JSON字符串转Map。
     *
     * @param json JSON串
     * @return 转换的Map实例
     */
    public static Map<String, Object> jsonToMap(final String json) {
        if ((json == null) || json.trim().equals("")) {
            return null;
        }
        try {
            return JacksonUtils.getMapper().readValue(json, JacksonUtils.MAP_TYPE);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to convert JSONString2Map. ", e);
        }
    }

    /**
     * 对象之间的转换。
     *
     * @param sourceObject 源对象
     * @param targetType 目标对象
     * @param <T> 对象类型
     * @return 转换的对象实例
     */
    public static <T> T pojoToPojo(final T sourceObject, final Class<T> targetType) {
        if (sourceObject == null) {
            return null;
        }
        return JacksonUtils.getMapper().convertValue(sourceObject, targetType);
    }

    /**
     * 对象实例转Map。
     *
     * @param pojo 对象实例
     * @param <T> 对象类型
     * @return 转换的Map实例
     */
    public static <T> Map<String, Object> pojoToMap(final T pojo) {
        if (pojo == null) {
            return null;
        }
        return JacksonUtils.getMapper().convertValue(pojo, JacksonUtils.MAP_TYPE);
    }

    /**
     * 按指定项目将对象实例转为Map。
     *
     * @param pojo 对象实例
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的Map实例
     */
    public static <T> Map<String, Object> pojoToMapWithSpecifiedFields(final T pojo, final String... fields) {
        final String json = JacksonUtils.pojoToJsonWithSpecifiedFields(pojo, fields);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 按过滤条件将对象实例转为Map。
     *
     * @param pojo 对象实例
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的Map实例
     */
    public static <T> Map<String, Object> pojoToMapWithFilterFields(final T pojo, final String... fields) {
        final String json = JacksonUtils.pojoToJsonWithFilterFields(pojo, fields);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 按指定项目将对象实例转为Map。
     *
     * @param pojo 对象实例
     * @param filterId 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的Map实例
     */
    public static <T> Map<String, Object> pojoToMapWithSpecifiedFields(final T pojo, final String filterId,
            final String... fields) {
        final String json = JacksonUtils.pojoToJsonWithSpecifiedFields(pojo, filterId, fields);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 按过滤条件将对象实例转为Map。
     *
     * @param pojo 对象实例
     * @param filterId 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param fields 要过滤的项目
     * @param <T> 对象类型
     * @return 转换的Map实例
     */
    public static <T> Map<String, Object> pojoToMapWithFilterFields(final T pojo, final String filterId,
            final String... fields) {
        final String json = JacksonUtils.pojoToJsonWithFilterFields(pojo, filterId, fields);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 按指定项目将对象实例转为JSON字符串。
     *
     * @param pojo 对象实例
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJsonWithSpecifiedFields(final T pojo, final String... fields) {
        if (pojo == null) {
            return null;
        }
        return JacksonUtils.pojoToJsonWithSpecifiedFields(pojo, pojo.getClass().getName(), fields);
    }

    /**
     * 按过滤条件将对象实例转为JSON字符串。
     *
     * @param pojo 对象实例
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJsonWithFilterFields(final T pojo, final String... fields) {
        if (pojo == null) {
            return null;
        }
        return JacksonUtils.pojoToJsonWithFilterFields(pojo, pojo.getClass().getName(), fields);
    }

    /**
     * 按指定项目将对象实例转为JSON字符串。
     *
     * @param pojo 对象实例
     * @param filterName 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param fields 要转换的项目
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJsonWithSpecifiedFields(final T pojo, String filterName, final String... fields) {
        if (pojo == null) {
            return null;
        }
        if ((filterName == null) || filterName.equals("")) {
            filterName = pojo.getClass().getName();
        }
        return JacksonUtils.pojoToJson(pojo, filterName, SimpleBeanPropertyFilter.filterOutAllExcept(fields));
    }

    /**
     * 按过滤条件将对象实例转为JSON字符串。
     *
     * @param pojo 对象实例
     * @param filterName 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param fields 要过滤掉的项目
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJsonWithFilterFields(final T pojo, final String filterName, final String... fields) {
        if (pojo == null) {
            return null;
        }
        String filter = filterName;
        if ((filterName == null) || filterName.equals("")) {
            filter = pojo.getClass().getName();
        }
        return JacksonUtils.pojoToJson(pojo, filter, SimpleBeanPropertyFilter.serializeAllExcept(fields));
    }

    /**
     * 根据指定的过滤器将对象实例转换为Map。
     *
     * @param pojo 对象实例
     * @param filter Bean属性过滤器
     * @param <T> 对象类型
     * @return 转换的Map
     */
    public static <T> Map<String, Object> pojoToMap(final T pojo, final PropertyFilter filter) {
        if (pojo == null) {
            return null;
        }
        final String json = JacksonUtils.pojoToJson(pojo, pojo.getClass().getName(), filter);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 根据指定的过滤器将对象实例转换为Map。
     *
     * @param pojo 对象实例
     * @param filterName 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param filter Bean属性过滤器
     * @param <T> 对象类型
     * @return 转换的Map
     */
    public static <T> Map<String, Object> pojoToMap(final T pojo, final String filterName,
            final PropertyFilter filter) {
        if (pojo == null) {
            return null;
        }
        final String json = JacksonUtils.pojoToJson(pojo, filterName, filter);
        return JacksonUtils.jsonToMap(json);
    }

    /**
     * 根据指定的过滤器将对象实例转换为JSON串。
     *
     * @param pojo 对象实例
     * @param filter Bean属性过滤器
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJson(final T pojo, final PropertyFilter filter) {
        if (pojo == null) {
            return null;
        }
        return JacksonUtils.pojoToJson(pojo, pojo.getClass().getName(), filter);
    }

    /**
     * 根据指定的过滤器将对象实例转换为JSON串。
     *
     * @param pojo 对象实例
     * @param filterName 过滤器Id（若设定Id，则Id必须跟POJO类的注解@JsonFilter的Id一致）
     * @param filter Bean属性过滤器
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJson(final T pojo, String filterName, final PropertyFilter filter) {
        if ((filterName == null) || filterName.equals("")) {
            filterName = pojo.getClass().getName();
        }
        final FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
        try {
            final String json = JacksonUtils.getFilterMapper().writer(filters).writeValueAsString(pojo);

            return json;
        } catch (final IOException e) {
            throw new RuntimeException("Failed to convert Object2JSONString. ", e);
        }
    }

    /**
     * 获取 ObjectMapper 实例。
     *
     * @return ObjectMapper实例
     */
    private static ObjectMapper getMapper() {
        if (JacksonUtils.mapper != null) {
            return JacksonUtils.mapper;
        }
        synchronized (JacksonUtils.class) {
            if (JacksonUtils.mapper != null) {
                return JacksonUtils.mapper;
            }
            JacksonUtils.mapper = new ObjectMapper();
            JacksonUtils.mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return JacksonUtils.mapper;
        }
    }

    /**
     * 获取可过滤的 ObjectMapper 实例。
     *
     * @return ObjectMapper实例
     */
    private static ObjectMapper getFilterMapper() {
        if (JacksonUtils.filterMapper != null) {
            return JacksonUtils.filterMapper;
        }
        synchronized (JacksonUtils.class) {
            if (JacksonUtils.filterMapper != null) {
                return JacksonUtils.filterMapper;
            }
            JacksonUtils.filterMapper = new ObjectMapper();
            JacksonUtils.filterMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return JacksonUtils.filterMapper;
        }
    }
}
