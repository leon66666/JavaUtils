package zhongqiu.javautils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangzhongqiu on 2017/6/5.
 * Json工具类
 */
public class JsonUtil {

    /**
     * bean to json
     *
     *
     */
    public static String beanToJson(Object obj, boolean serializeNullValue) {
        if (obj == null) {
            return null;
        }
        // Bean -> Json
        Gson gson = serializeNullValue ? new GsonBuilder().serializeNulls().create() : new Gson();
        String json = gson.toJson(obj);
        return json;
    }

    /**
     * bean to json
     *
     *
     */
    public static String beanToJson(Object obj) {
        return JsonUtil.beanToJson(obj, false);
    }

    /**
     * bean to json 适用于毫秒
     *
     *
     */
    public static String beanWithDateToJson(Object obj, boolean serializeNullValue) {
        if (obj == null) {
            return null;
        }
        // date serializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = serializeNullValue ? gsonBuilder.serializeNulls()
                .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG)
                .registerTypeAdapter(java.math.BigDecimal.class, TypeAdapters.STRING_BUILDER_FACTORY)
                .create() : gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        String json = gson.toJson(obj);
        return json;
    }

    /**
     * @param obj
     * @param pattern 日期格式化方式
     * @return
     */
    public static String beanWithDateToJson(Object obj, String pattern) {
        if (obj == null) {
            return null;
        }
        String dfStr = pattern;
        if(StringUtil.isEmpty(pattern)){
            dfStr = StringUtil.sdf_time_pattern;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(dfStr);
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(obj);
        return json;
    }

    /**
     * @param obj
     * @param pattern 日期格式化方式
     * @return
     */
    public static String beanWithDateToJson(Object obj, String pattern, boolean serializeNullValue) {
        if (obj == null) {
            return null;
        }
        String dfStr = pattern;
        if(StringUtil.isEmpty(pattern)){
            dfStr = StringUtil.sdf_time_pattern;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (serializeNullValue) {
            gsonBuilder.serializeNulls();
        }
        Gson gson = gsonBuilder.setDateFormat(dfStr)
                .registerTypeAdapter(Date.class, new DateSerializerUtils())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalSerializerUtils())
                .create();
        String json = gson.toJson(obj);
        return json;
    }

    public static <T> T jsonToBean(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        StringReader strReader = new StringReader(json);
        JsonReader jsonReader = new JsonReader(strReader);
        return jsonToBean(jsonReader, clazz);
    }

    private static <T> T jsonToBean(JsonReader json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        // Json -> Bean
        Gson gson = new Gson();

        T bean = (T) gson.fromJson(json, clazz);
        return bean;
    }

    private static <T> T jsonWithDateToBean(JsonReader json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        // date deserializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateDeserializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        // Json -> Bean

        T b = (T) gson.fromJson(json, clazz);
        return b;
    }

    public static <T> T jsonWithDateToBean(String json, Class<T> clazz, final String pattern) {
        Object obj = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(json, clazz);
        }
        return (T) obj;
    }

    public static <T> T jsonWithDateToBean(JsonReader json, Class<T> clazz, final String pattern) {
        Object obj = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(json, clazz);
        }
        return (T) obj;
    }

    /**
     * list to json
     *
     *
     */

    public static <T> String listToJson(List<T> list, boolean serializeNullValue) {
        if (list == null) {
            return null;
        }
        // List -> Json
        Gson gson = serializeNullValue ? new GsonBuilder().serializeNulls().create() : new Gson();
        String json = gson.toJson(list);
        return json;
    }

    /**
     * list to json
     *
     *
     */

    public static <T> String listWithDateToJson(List<T> list, boolean serializeNullValue) {
        if (list == null) {
            return null;
        }
        // date serializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = serializeNullValue ? gsonBuilder.serializeNulls()
                .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG)
                .create() : gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        // List -> Json
        String json = gson.toJson(list);
        return json;
    }

    /**
     * json to list
     *
     *
     */

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        // json -> List
        StringReader strReader = new StringReader(json);
        List<T> list = null;
        try {
            list = readForList(strReader, false, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json to list
     *
     *
     */
    public static <T> List<T> jsonWithDateToList(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }

        // json -> List
        StringReader strReader = new StringReader(json);
        List<T> list = null;
        try {
            list = readForList(strReader, true, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json to list
     *
     *
     */
    public static <T> List<T> jsonWithDateToList(String json, Class<T> clazz, final String pattern) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }

        // json -> List
        StringReader strReader = new StringReader(json);
        List<T> list = null;
        try {
            list = readForList(strReader, true, clazz, pattern);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static <T> List<T> readForList(Reader reader, boolean hasDate, Class<T> clazz) throws IOException {
        JsonReader jsonReader = new JsonReader(reader);
        List<T> objs = new ArrayList<T>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            T obj = null;
            if (hasDate) {
                obj = jsonWithDateToBean(jsonReader, clazz);
            } else {
                obj = jsonToBean(jsonReader, clazz);
            }
            if (obj != null)
                objs.add(obj);
        }
        jsonReader.endArray();
        jsonReader.close();
        return objs;
    }

    private static <T> List<T> readForList(Reader reader, boolean hasDate, Class<T> clazz, final String pattern) throws IOException {
        JsonReader jsonReader = new JsonReader(reader);
        List<T> objs = new ArrayList<T>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            T obj = null;
            if (hasDate) {
                obj = jsonWithDateToBean(jsonReader, clazz, pattern);
            } else {
                obj = jsonToBean(jsonReader, clazz);
            }
            if (obj != null)
                objs.add(obj);
        }
        jsonReader.endArray();
        jsonReader.close();
        return objs;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @param cl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, final String pattern) {
        Object obj = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    /**
     * set to json
     *
     *
     */
    public static <T> String setToJson(Set<T> set, boolean serializeNullValue) {
        if (set == null) {
            return null;
        }
        // set -> Json
        Gson gson = serializeNullValue ? new GsonBuilder().serializeNulls().create() : new Gson();
        String json = gson.toJson(set);
        return json;
    }

    /**
     * set to json
     *
     *
     */
    public static <T> String setWithDateToJson(Set<T> set, boolean serializeNullValue) {
        if (set == null) {
            return null;
        }
        // date serializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = serializeNullValue ? gsonBuilder.serializeNulls()
                .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG)
                .create() : gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        // List -> Json
        String json = gson.toJson(set);
        return json;
    }

    /**
     * json to set
     *
     *
     */
    public static <T> Set<T> jsonToSet(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        // json -> set
        StringReader strReader = new StringReader(json);
        Set<T> set = null;
        try {
            set = readForSet(strReader, false, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * json to set
     *
     *
     */
    public static <T> Set<T> jsonWithDateToSet(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }

        // json -> set
        StringReader strReader = new StringReader(json);
        Set<T> set = null;
        try {
            set = readForSet(strReader, true, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    private static <T> Set<T> readForSet(Reader reader, boolean hasDate, Class<T> clazz) throws IOException {
        JsonReader jsonReader = new JsonReader(reader);
        Set<T> objs = new HashSet<T>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            T obj = null;
            if (hasDate) {
                obj = jsonWithDateToBean(jsonReader, clazz);
            } else {
                obj = jsonToBean(jsonReader, clazz);
            }
            if (obj != null)
                objs.add(obj);
        }
        jsonReader.endArray();
        jsonReader.close();
        return objs;
    }

    /**
     * map to json
     *
     *
     */
    public static <T> String mapToJson(Map<String, T> map, boolean serializeNullValue) {
        if (map == null) {
            return null;
        }
        // Map -> Json
        Gson gson = serializeNullValue ? new GsonBuilder().serializeNulls().create() : new Gson();
        String json = gson.toJson(map);
        return json;
    }

    /**
     * map to json
     *
     *
     */
    public static <T> String mapWithDateToJson(Map<String, T> map, boolean serializeNullValue) {
        if (map == null) {
            return null;
        }
        // date serializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = serializeNullValue ? gsonBuilder.serializeNulls()
                .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG)
                .create() : gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        // map -> json
        String json = gson.toJson(map);
        return json;
    }

    /**
     * map to json
     *
     *
     */
    public static <T> Map<String, T> jsonToMap(String json) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        // date serializable
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
                .setDateFormat(DateFormat.LONG).create();
        // map -> json
        Map<String, T> retMap = gson.fromJson(json, new TypeToken<Map<String, T>>() {}.getType());
        return retMap;
    }

    /**
     * 日期解序列实用工具类
     */
    static class DateSerializerUtils implements JsonSerializer<Date> {
        public JsonElement serialize(Date date, Type type, JsonSerializationContext content) {
            return new JsonPrimitive(date.getTime());
        }

    }

    static class BigDecimalSerializerUtils implements JsonSerializer<BigDecimal> {
        public JsonElement serialize(BigDecimal bigDecimal, Type type, JsonSerializationContext content) {
            return new JsonPrimitive(bigDecimal.toPlainString());
        }

    }

    /**
     * 日期序列化实用工具类
     */
    static class DateDeserializerUtils implements JsonDeserializer<java.util.Date> {
        public java.util.Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    /**
     * 格式化json
     *
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static void main(String[] args) {

    }
}
