package zhongqiu.javautils;

import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangzhongqiu on 2017/6/5.
 * 字符串操作工具类
 */
public class StringUtil extends org.apache.commons.lang.StringUtils {

    public static final String sdf_time_pattern = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat sdf_time = new SimpleDateFormat(sdf_time_pattern);
    public static final SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf_timeonly = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat sdf_time_digit = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat sdf_time_yyyyMM = new SimpleDateFormat("yyyyMM");

    public static final String df_number_pattern = "#.#";
    public static final DecimalFormat df = new DecimalFormat(df_number_pattern);

    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println("result[" + StringUtil.trimToNull("\nd d\n") + "]");
//        System.out.println("result[" + StringUtil.trimToNull("　\t\n d d \r  ") + "]");
//        System.out.println(StringUtil.toString(new BigDecimal("-1.00")));
//        System.out.println(StringUtil.toString(new BigDecimal("-01.00")));
//        System.out.println(StringUtil.toString(new BigDecimal("01.000000001")));
//        System.out.println(StringUtil.toString(new BigDecimal("100000000.0000000000001")));
//        System.out.println(StringUtil.toString(new BigDecimal("-100000000.0000000000001")));
//        System.out.println(StringUtil.isNegativeDecimal("1"));
//        System.out.println(StringUtil.isNegativeDecimal("1.1"));
//        System.out.println(StringUtil.isNegativeDecimal("0"));
//        System.out.println(StringUtil.isNegativeDecimal("+1"));
//        System.out.println(StringUtil.isNegativeDecimal("1000"));
//        System.out.println(StringUtil.isNegativeDecimal("0.001"));
//        System.out.println(StringUtil.isNegativeDecimal("-1"));
//        System.out.println(StringUtil.isNegativeDecimal("-0.1"));
    }

    /**
     * 判断字符串是否是空、空串或者“null”
     *
     * @return true/false
     */
    public static boolean emptyOrNull(String str) {

        if (!empty(str) && !"null".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是空、空串或者“null”
     *
     * @return true/false
     */
    public static boolean empty(String str) {

        if (str != null && !"".equals(str)) {
            return false;
        }
        return true;
    }

    public static String formatTimeOnlyDigit(Date time) {
        return sdf_time_digit.format(time);
    }

    public static String formatTime(Date time) {
        return sdf_time.format(time);
    }

    public static String formatDate(Date date) {
        return sdf_date.format(date);
    }

    /**
     * 打印数组
     */
    public static String print(Object[] c) {
        if (StringUtil.isEmpty(c)) {
            return StringUtil.BLANK;
        }
        return StringUtil.concatWithDelimiter(c, ",", "[", "]", StringUtil.BLANK);
    }

    /**
     * 打印数组
     */

    public static String print(byte[] c) {
        if (StringUtil.isEmpty(c)) {
            return StringUtil.BLANK;
        }
        return StringUtil.concatWithDelimiter(c, ",", "[", "]", StringUtil.BLANK);
    }

    public static String print(Collection c) {
        if (StringUtil.isEmpty(c)) {
            return StringUtil.BLANK;
        }
        return StringUtil.print(c.toArray(new Object[]{}));

    }

    public static String print(Serializable obj) {
        if (StringUtil.isEmpty(obj)) {
            return StringUtil.BLANK;
        }
        return JsonUtil.beanWithDateToJson(obj, StringUtil.sdf_time_pattern);

    }

    /**
     * 将object数组转为ArrayList<String>
     *
     * @param s
     * @return
     */
    public static ArrayList<String> methodArrayToStringArrayList(Method[] s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        ArrayList<String> a = new ArrayList<String>();
        try {
            for (Method i : s) {
                a.add(String.valueOf(i.getName()));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将object数组转为ArrayList<String>
     *
     * @param s
     * @return
     */
    public static ArrayList<String> objectArrayToStringArrayList(Object[] s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        ArrayList<String> a = new ArrayList<String>();
        try {
            for (Object i : s) {
                a.add(String.valueOf(i));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将object数组转为ArrayList<Integer>
     *
     * @param s
     * @return
     */
    public static ArrayList<Integer> objectArrayToIntegerArrayList(Object[] s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        ArrayList<Integer> a = new ArrayList<Integer>();
        try {
            for (Object i : s) {
                a.add(Integer.parseInt(String.valueOf(i)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将string数组转为ArrayList<Integer>
     *
     * @param s
     * @return
     */
    public static Integer[] objectArrayToIntegerArray(Object[] s) {
        return StringUtil.objectArrayToIntegerArrayList(s).toArray(new Integer[0]);
    }

    /**
     * 将string数组转为ArrayList<Integer>
     *
     * @param s
     * @return
     */
    public static ArrayList<Integer> stringArrayToIntegerArrayList(String[] s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        ArrayList<Integer> a = new ArrayList<Integer>();
        try {
            for (String i : s) {
                a.add(Integer.parseInt(i));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将string数组转为ArrayList<Integer>
     *
     * @param c
     * @return
     */
    public static Integer[] stringArrayToIntegerArray(String[] s) {
        return StringUtil.stringArrayToIntegerArrayList(s).toArray(new Integer[0]);
    }

    /**
     * 将string数组转为ArrayList<Long>
     *
     * @param c
     * @return
     */
    public static ArrayList<Long> stringArrayToLongArrayList(String[] s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        ArrayList<Long> a = new ArrayList<Long>();
        try {
            for (String i : s) {
                a.add(Long.parseLong(i));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将string数组转为ArrayList<Long>
     *
     * @param c
     * @return
     */
    public static Long[] stringArrayToLongArray(String[] s) {
        return StringUtil.stringArrayToLongArrayList(s).toArray(new Long[0]);
    }

    /**
     * 判断一个字符串是否为null或trim后为""
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || StringUtil.BLANK.equals(StringUtil.trim_(str))
                || "undefined".equalsIgnoreCase(StringUtil.trim_(str))
                || "null".equalsIgnoreCase(StringUtil.trim_(str))) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是否为null或trim后为""
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return StringUtil.isEmpty((String) obj);
        }
        if (obj instanceof Collection) {
            return isEmpty((Collection) obj);
        }
        if (obj instanceof Object[]) {
            return isEmpty((Object[]) obj);
        }
        if (obj instanceof Map) {
            return isEmpty((Map) obj);
        }
        if (obj instanceof StringBuffer) {
            return isEmpty((StringBuffer) obj);
        }
        return false;
    }

    /**
     * 判断一个字符串是否为null或trim后为""
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtil.isEmpty(str);
    }

    /**
     * 判断一个字符串是否为null或trim后为""
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !StringUtil.isEmpty(obj);
    }

    /**
     * 判断一个集合是否为null或空集合
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c) {
        if (c == null || c.size() == 0 || (c.size() == 1 && c.toArray()[0] == null)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个集合是否为null或空集合
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Map m) {
        if (m == null || m.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个数组是否为null或空集合
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Object[] c) {
        if (c == null || c.length == 0 || (c.length == 1 && c[0] == null)) {
            return true;
        }
        return false;
    }

    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[ |　]", " ").trim();
            return dest;
        }
        return null;
    }

    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     * 如果结果为空串则return null
     *
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 判断一个集合是否为null或空集合
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection c) {
        return !StringUtil.isEmpty(c);
    }

    /**
     * 判断一个数组是否为null或空集合
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object[] c) {
        return !StringUtil.isEmpty(c);
    }

    /**
     * 由数组转成分隔符分隔的字符串 StringUtil.concatWithDelimiter(new Integer[]{1,2,3}, "_",
     * "[", "]", null); => [1_2_3]
     *
     * @param objectIds
     * @param delimiter
     * @param beginDelimiter
     * @param endDelimiter
     * @param defaultValue
     * @return
     */
    public static String concatWithDelimiter(Object[] objectIds, String delimiter, String beginDelimiter,
                                             String endDelimiter, String defaultValue) {
        if (StringUtil.isEmpty(objectIds)) {
            return defaultValue;
        }
        StringBuffer s = new StringBuffer(beginDelimiter != null ? beginDelimiter : StringUtil.BLANK);
        for (Object id : objectIds) {
            s.append(id).append(delimiter);
        }
        return endDelimiter == null ? s.toString().substring(0, s.toString().length() - 1) : (s.toString().substring(0,
                s.toString().length() - 1)) + endDelimiter;
    }

    public static String concatWithDelimiter(byte[] objectIds, String delimiter, String beginDelimiter,
                                             String endDelimiter, String defaultValue) {
        if (StringUtil.isEmpty(objectIds)) {
            return defaultValue;
        }
        StringBuffer s = new StringBuffer(beginDelimiter != null ? beginDelimiter : StringUtil.BLANK);
        for (byte id : objectIds) {
            s.append(id).append(delimiter);
        }
        return endDelimiter == null ? s.toString().substring(0, s.toString().length() - 1) : (s.toString().substring(0,
                s.toString().length() - 1)) + endDelimiter;
    }

    /**
     * 由数组转成逗号分隔的字符串 array[1,2,3] => 1,2,3
     *
     * @param objectIds
     * @return
     */
    public static String concatWithComma(Object[] objectIds) {
        return StringUtil.concatWithDelimiter(objectIds, ",", null, null, null);
    }

    /**
     * 由数组转成逗号分隔的字符串(前后带逗号) array[1,2,3] => ,1,2,3,
     *
     * @param objectIds
     * @return
     */
    public static String concatWithCommaEmbraced(Object[] objectIds) {
        return StringUtil.concatWithDelimiter(objectIds, ",", ",", ",", null);
    }

    /**
     * 由数组转成逗号分隔的字符串 array[1,2,3] => 1,2,3
     *
     * @param objectIds
     * @return
     */
    public static String concatWithComma(Collection objectIds) {
        return StringUtil.concatWithDelimiter(objectIds, ",", null, null, null);
    }

    /**
     * 由数组转成逗号分隔的字符串(前后带逗号) array[1,2,3] => ,1,2,3,
     *
     * @param objectIds
     * @return
     */
    public static String concatWithCommaEmbraced(Collection objectIds) {
        return StringUtil.concatWithDelimiter(objectIds, ",", ",", ",", null);
    }

    /**
     * 由数组转成分隔符分隔的字符串 StringUtil.concatWithDelimiter(new ArrayList[]{1,2,3},
     * "_", true, false, null); => _1_2_3
     *
     * @param c
     * @return
     */
    public static String concatWithDelimiter(Collection c, String delimiter, String beginDelimiter,
                                             String endDelimiter, String defaultValue) {
        if (c == null || c.isEmpty()) {
            return defaultValue;
        }
        return StringUtil.concatWithDelimiter(c.toArray(), delimiter, beginDelimiter, endDelimiter, defaultValue);
    }

    public static final boolean DEFAULT_BOOLEAN = false;

    public static final boolean[] DEFAULT_BOOLEAN_VALUES = new boolean[0];

    public static final Double DEFAULT_DOUBLE = null;

    public static final Double[] DEFAULT_DOUBLE_VALUES = new Double[0];

    public static final Float DEFAULT_FLOAT = null;

    public static final Float[] DEFAULT_FLOAT_VALUES = new Float[0];

    public static final Integer DEFAULT_INTEGER = null;

    public static final Integer[] DEFAULT_INTEGER_VALUES = new Integer[0];

    public static final Long DEFAULT_LONG = null;

    public static final Long[] DEFAULT_LONG_VALUES = new Long[0];

    public static final Short DEFAULT_SHORT = null;

    public static final Short[] DEFAULT_SHORT_VALUES = new Short[0];

    public static final String BLANK = "";

    public static String[] BOOLEANS = {"true", "t", "y", "on", "1"};

    public static boolean getBoolean(String value) {
        return getBoolean(value, DEFAULT_BOOLEAN);
    }

    public static boolean getBoolean(String value, boolean defaultValue) {
        return get(value, defaultValue);
    }

    public static boolean[] getBooleanValues(String[] values) {
        return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
    }

    public static boolean[] getBooleanValues(String[] values, boolean[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        boolean[] booleanValues = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            booleanValues[i] = getBoolean(values[i]);
        }

        return booleanValues;
    }

    public static Date getDate(String value, DateFormat df) {
        return getDate(value, df, new Date());
    }

    public static Date getDate(String value, DateFormat df, Date defaultValue) {
        return get(value, df, defaultValue);
    }

    public static Double getDouble(String value) {
        return getDouble(value, DEFAULT_DOUBLE);
    }

    public static Double getDouble(String value, Double defaultValue) {
        return get(value, defaultValue);
    }

    public static Double[] getDoubleValues(String[] values) {
        return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
    }

    public static Double[] getDoubleValues(String[] values, Double[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Double[] DoubleValues = new Double[values.length];

        for (int i = 0; i < values.length; i++) {
            DoubleValues[i] = getDouble(values[i]);
        }

        return DoubleValues;
    }

    public static Float getFloat(String value) {
        return getFloat(value, DEFAULT_FLOAT);
    }

    public static Float getFloat(String value, Float defaultValue) {
        return get(value, defaultValue);
    }

    public static Float[] getFloatValues(String[] values) {
        return getFloatValues(values, DEFAULT_FLOAT_VALUES);
    }

    public static Float[] getFloatValues(String[] values, Float[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Float[] FloatValues = new Float[values.length];

        for (int i = 0; i < values.length; i++) {
            FloatValues[i] = getFloat(values[i]);
        }

        return FloatValues;
    }

    public static Integer getInteger(String value) {
        return getInteger(value, DEFAULT_INTEGER);
    }

    public static Integer getInteger(String value, Integer defaultValue) {
        return get(value, defaultValue);
    }

    public static Long getLong(String value) {
        return getLong(value, DEFAULT_LONG);
    }

    public static Long getLong(String value, Long defaultValue) {
        return get(value, defaultValue);
    }

    public static Long[] getLongValues(String[] values) {
        return getLongValues(values, DEFAULT_LONG_VALUES);
    }

    public static Long[] getLongValues(String[] values, Long[] defaultValue) {
        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Long[] LongValues = new Long[values.length];

        for (int i = 0; i < values.length; i++) {
            LongValues[i] = getLong(values[i]);
        }

        return LongValues;
    }

    public static Short getShort(String value) {
        return getShort(value, DEFAULT_SHORT);
    }

    public static Short getShort(String value, Short defaultValue) {
        return get(value, defaultValue);
    }

    public static Short[] getShortValues(String[] values) {
        return getShortValues(values, DEFAULT_SHORT_VALUES);
    }

    public static Short[] getShortValues(String[] values, Short[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Short[] ShortValues = new Short[values.length];

        for (int i = 0; i < values.length; i++) {
            ShortValues[i] = getShort(values[i]);
        }

        return ShortValues;
    }

    public static String getString(Object value) {
        return getString(value, StringUtil.BLANK);
    }

    public static String getString(Object value, String defaultValue) {
        return get(value, defaultValue);
    }

    public static String[] getStringValues(Object[] values) {
        if (StringUtil.isEmpty(values)) {
            return null;
        }
        String[] stringValues = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            stringValues[i] = getString(stringValues[i]);
        }

        return stringValues;
    }

    public static boolean getBoolean(Object value) {
        return getBoolean(StringUtil.toString(value), DEFAULT_BOOLEAN);
    }

    public static boolean getBoolean(Object value, boolean defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static boolean[] getBooleanValues(Object[] values) {
        return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
    }

    public static boolean[] getBooleanValues(Object[] values, boolean[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        boolean[] booleanValues = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            booleanValues[i] = getBoolean(values[i]);
        }

        return booleanValues;
    }

    public static Date getDate(Object value, DateFormat df) {
        return getDate(StringUtil.toString(value), df, new Date());
    }

    public static Date getDate(Object value, DateFormat df, Date defaultValue) {
        return get(StringUtil.toString(value), df, defaultValue);
    }

    public static Double getDouble(Object value) {
        return getDouble(StringUtil.toString(value), DEFAULT_DOUBLE);
    }

    public static Double getDouble(Object value, Double defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static Double[] getDoubleValues(Object[] values) {
        return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
    }

    public static Double[] getDoubleValues(Object[] values, Double[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Double[] DoubleValues = new Double[values.length];

        for (int i = 0; i < values.length; i++) {
            DoubleValues[i] = getDouble(values[i]);
        }

        return DoubleValues;
    }

    public static Float getFloat(Object value) {
        return getFloat(StringUtil.toString(value), DEFAULT_FLOAT);
    }

    public static Float getFloat(Object value, Float defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static Float[] getFloatValues(Object[] values) {
        return getFloatValues(values, DEFAULT_FLOAT_VALUES);
    }

    public static Float[] getFloatValues(Object[] values, Float[] defaultValue) {

        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Float[] FloatValues = new Float[values.length];

        for (int i = 0; i < values.length; i++) {
            FloatValues[i] = getFloat(values[i]);
        }

        return FloatValues;
    }

    public static Integer getInteger(Object value) {
        return getInteger(StringUtil.toString(value), DEFAULT_INTEGER);
    }

    public static Integer getInteger(Object value, Integer defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static Integer[] getIntegerValues(Object[] values) {
        return getIntegerValues(values, DEFAULT_INTEGER_VALUES);
    }

    public static Integer[] getIntegerValues(Object[] values, Integer[] defaultValue) {
        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Integer[] intValues = new Integer[values.length];

        for (int i = 0; i < values.length; i++) {
            intValues[i] = getInteger(values[i]);
        }

        return intValues;
    }

    public static Long getLong(Object value) {
        return getLong(StringUtil.toString(value), DEFAULT_LONG);
    }

    public static Long getLong(Object value, Long defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static Long[] getLongValues(Object[] values) {
        return getLongValues(values, DEFAULT_LONG_VALUES);
    }

    public static Long[] getLongValues(Object[] values, Long[] defaultValue) {
        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Long[] LongValues = new Long[values.length];

        for (int i = 0; i < values.length; i++) {
            LongValues[i] = getLong(values[i]);
        }

        return LongValues;
    }

    public static Short getShort(Object value) {
        return getShort(StringUtil.toString(value), DEFAULT_SHORT);
    }

    public static Short getShort(Object value, Short defaultValue) {
        return get(StringUtil.toString(value), defaultValue);
    }

    public static Short[] getShortValues(Object[] values) {
        return getShortValues(values, DEFAULT_SHORT_VALUES);
    }

    public static Short[] getShortValues(Object[] values, Short[] defaultValue) {
        if (StringUtil.isEmpty(values)) {
            return defaultValue;
        }

        Short[] ShortValues = new Short[values.length];

        for (int i = 0; i < values.length; i++) {
            ShortValues[i] = getShort(values[i]);
        }

        return ShortValues;
    }

    private static boolean get(String value, boolean defaultValue) {
        if (value != null) {
            try {
                value = StringUtil.trim_(value);

                if (value.equalsIgnoreCase(BOOLEANS[0]) || value.equalsIgnoreCase(BOOLEANS[1])
                        || value.equalsIgnoreCase(BOOLEANS[2]) || value.equalsIgnoreCase(BOOLEANS[3])
                        || value.equalsIgnoreCase(BOOLEANS[4])) {

                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
            }
        }

        return defaultValue;
    }

    private static Date get(String value, DateFormat df, Date defaultValue) {
        try {
            Date date = df.parse(StringUtil.trim_(value));

            if (date != null) {
                return date;
            }
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static Double get(String value, Double defaultValue) {
        try {
            return Double.parseDouble(trim_(value));
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static Float get(String value, Float defaultValue) {
        try {
            return Float.parseFloat(trim_(value));
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static Integer get(String value, Integer defaultValue) {
        try {
            return Integer.parseInt(trim_(value));
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static Long get(String value, Long defaultValue) {
        try {
            return Long.parseLong(trim_(value));
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static Short get(String value, Short defaultValue) {
        try {
            return Short.parseShort(trim_(value));
        } catch (Exception e) {
        }

        return defaultValue;
    }

    private static String get(Object value, String defaultValue) {
        String strvalue = StringUtil.toString(value);
        if (StringUtil.isNotEmpty(value)) {
            if (value instanceof Timestamp) {
                return new SimpleDateFormat("yyyy-MM-dd").format(((Timestamp) value));
            } else if (value instanceof Date) {
                return new SimpleDateFormat("yyyy-MM-dd").format(((Date) value));
            }
            if ("null".equals(strvalue) || "undefined".equals(strvalue)) {
                return null;
            }
            return StringUtil.trim_(strvalue);
        }
        return defaultValue;
    }

    private static String trim_(String str) {
        if (str != null) {
            return trim(str);
        }
        return StringUtil.BLANK;
    }

    public static String camelToUnderline(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    public static String underlineToCamel(String param) {
        Pattern p = Pattern.compile("[a-z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    // public static <T> T requestParameterToBean(HttpServletRequest request,
    // Class<T> beanClass) {
    // try {
    // T bean = beanClass.newInstance();
    // Map map = request.getParameterMap();
    // BeanUtils.populate(bean, map);
    // return bean;
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // }

    /**
     * toString方法, 如果为""则返回null
     *
     * @param obj
     * @return
     */
    public static String toStringDefaultNull(Object obj) {
        String result = StringUtil.toString(obj);
        return result.equals(StringUtil.BLANK) ? null : result;
    }

    /**
     * toString方法, 默认返回""
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (StringUtil.isEmpty(obj)) {
            return StringUtil.BLANK;
        }
        try {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.applyPattern("yyyy-MM-dd");
                return sdf.format(obj);
            } else if (obj instanceof BigDecimal) {
                String result = ((BigDecimal) obj).stripTrailingZeros().toPlainString();
                if (((BigDecimal) obj).compareTo(new BigDecimal(result)) == 0) {
                    return result;
                }
                return ((BigDecimal) obj).toPlainString();
            } else {
                return obj.toString();
            }
        } catch (Exception e) {
            return StringUtil.BLANK;
        }
    }

    public static String fmtNum(BigDecimal amount) {
        String result = "";
        if (isNotEmpty(amount)) {
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("0,000");
            String number = amount.toString();
            if (number.length() > 3) {
                result = df.format(Double.parseDouble(number));
            } else {
                result = number;
            }

            if (!result.contains("."))
                result = result + ".00";
        } else {
            result = "";
        }

        return result;
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value
     * @return 字符串的长度
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (StringUtil.isEmpty(value)) {

        } else {
            for (int i = 0; i < value.length(); i++) {
                /* 获取一个字符 */
                String temp = value.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    /* 中文字符长度为2 */
                    valueLength += 2;
                } else {
                    /* 其他字符长度为1 */
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (StringUtil.isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     *
     * @param str
     * @return
     */
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     *
     * @param str
     * @return
     */
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str.matches("\\d*")) {
            return true;
        }
        return false;
    }

    /**
     * 是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(char c) {
        return StringUtil.isNumeric((c + ""));
    }

    /**
     * 是否拼配正则表达式
     *
     * @param regex
     * @param orginal
     * @return
     */
    public static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 是否正整数或零
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 是否负整数
     *
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 是否为整数(可正负)
     *
     * @param orginal
     * @return
     */
    public static boolean isInteger(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 是否非负浮点数
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("^\\d+(\\.\\d+)?$", orginal);
    }

    /**
     * 是否非正浮点数
     *
     * @param orginal
     * @return
     */
    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$", orginal);
    }

    /**
     * 是否为浮点数(可正负)
     *
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal) {
        return isMatch("^(-?\\d+)(\\.\\d+)?$", orginal);
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    public static boolean isDigital(String str) {
        return StringUtil.isEmpty(str) ? false : str.matches("^[0-9]*$");
    }

    /**
     * 是否为英文字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[a-zA-Z]+");
        java.util.regex.Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * 是否为英文字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(char c) {
        return StringUtil.isLetter((c + ""));
    }

    private final static String[] hex = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C",
            "0D", "0E", "0F", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E",
            "1F", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", "40", "41", "42",
            "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52", "53", "54",
            "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63", "64", "65", "66",
            "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77", "78",
            "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A",
            "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C",
            "9D", "9E", "9F", "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE",
            "AF", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1", "D2",
            "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4",
            "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6",
            "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"};
    private final static byte[] val = {0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00,
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B,
            0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F};

    public static String escapeUTF8(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') { // space : map to '+'
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch <= 0x007F) { // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else { // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }

    public static String unescapeUTF8(String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') { // + : map to ' '
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) { // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else { // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            }
            i++;
        }
        return sbuf.toString();
    }

    /**
     * 获取系统唯一UUID
     *
     * @return
     */
    // public static String getUUID() {
    // return uu.generate(null, null).toString();
    // }

    // private static UUIDHexGenerator uu = new UUIDHexGenerator();

    /**
     * 当前年度
     */
    public static final Integer CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    /**
     * 当前月份
     */
    public static final Integer CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;

    /**
     * 当前日期
     */
    public static final Integer CURRENT_DATE = Calendar.getInstance().get(Calendar.DATE);

    /**
     * 将javaBean中的属性值替换字符串中的内容，该内容由{}括起来，如果该属性无法找到，则为空
     *
     * @param srcRemarks
     * @param obj
     * @return
     */
    // public static String parser(String srcRemarks, Object obj) {
    // TimeRecordUtil.recordTime("", StringUtil.class.getName(), "parser", "",
    // "解析开始");
    // String regEx = "\\{\\w+\\}";
    // Pattern pattern = Pattern.compile(regEx);
    // Matcher matcher = pattern.matcher(srcRemarks);
    // String remarks = srcRemarks;
    // while (matcher.find()){
    // String group = matcher.group();
    // Object value = null;
    // try {
    // value = PropertyUtils.getProperty(obj,
    // group.substring(1,group.length()-1));
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (InvocationTargetException e) {
    // e.printStackTrace();
    // } catch (NoSuchMethodException e) {
    // e.printStackTrace();
    // }
    // if (value == null) {
    // value = "";
    // }
    // remarks = remarks.replace(group, value.toString());
    // }
    // TimeRecordUtil.recordTime("", StringUtil.class.getName(), "parser", "",
    // "解析结束");
    // return remarks;
    // }

    /**
     * 由数组转成分隔符分隔的字符串
     *
     * @param objectIds
     * @param delimiter
     * @param beginWithDelimiter
     * @param endWithDelimiter
     * @param defaultValue
     * @return
     */
    public static String concatWithDelimiter(Object[] objectIds, String delimiter, boolean beginWithDelimiter,
                                             boolean endWithDelimiter, String defaultValue) {
        if (StringUtil.isEmpty(objectIds)) {
            return defaultValue;
        }
        StringBuffer s = new StringBuffer(beginWithDelimiter ? delimiter : "");
        for (Object id : objectIds) {
            s.append(id).append(delimiter);
        }
        return endWithDelimiter ? s.toString() : s.toString().substring(0, s.toString().length() - 1);
    }

    /**
     * 由数组转成分隔符分隔的字符串
     *
     * @param c
     * @return
     */
    public static String concatWithDelimiter(Collection c, String delimiter, boolean beginWithDelimiter,
                                             boolean endWithDelimiter, String defaultValue) {
        if (c == null || c.isEmpty()) {
            return defaultValue;
        }
        return StringUtil.concatWithDelimiter(c.toArray(), delimiter, beginWithDelimiter, endWithDelimiter,
                defaultValue);
    }

    /**
     * @param inStr
     * @return null to empty str ""
     */

    public static String filteNull(String inStr) {
        if (inStr == null || "null".equals(inStr)) {
            return "";
        } else {
            return (inStr);
        }
    }

    /**
     * @param in
     * @return in or ""
     */
    public static String filteNull(Number in) {
        if (in == null) {
            return "";
        } else if (in instanceof Double) {
            return "" + in.doubleValue();
        } else if (in instanceof Float) {
            return "" + in.floatValue();
        } else if (in instanceof Long) {
            return "" + in.longValue();
        } else {
            return "" + in.intValue();
        }
    }

    /**
     * 判断StringBuffer是否为null或空集合
     *
     * @param sbf
     * @return boolean
     */
    public static boolean isEmpty(StringBuffer sbf) {
        if (sbf == null || sbf.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 把printStarckTrace的内容转换为字符串
     *
     * @param t
     * @return
     */
    public static String getExceptionTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    /**
     * <<<<<<< HEAD <<<<<<< HEAD MD5 ======= <<<<<<< HEAD MD5 >>>>>>>
     * refs/remotes/origin/dev_planb
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    /**
     * MD5
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * MD5
     */
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * 创建x位的随机字符
     * 为了防止拼出来的订单号太长看错位, 保证第一位是英文
     *
     * @param digit
     */
    public static String buildRandom(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        String charAndDigit = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                int number = random.nextInt(chars.length());
                sb.append(chars.charAt(number));
            } else {
                int number = random.nextInt(charAndDigit.length());
                sb.append(charAndDigit.charAt(number));
            }
        }
        return sb.toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandomDigit(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String toFirstLetterUpperCase(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        String firstLetter = str.substring(0, 1).toUpperCase();
        return firstLetter + str.substring(1, str.length());
    }

    /**
     * 处理手机号
     *
     * @param mobile
     * @return
     */
    public static String dealMobile(String mobile) {
        if (mobile == null || mobile.length() <= 4) {
            return mobile;
        }
        String last4 = mobile.substring(mobile.length() - 4);
        if (mobile.length() <= 8) {
            return ("****" + last4).substring(8 - mobile.length());
        }
        String pre = mobile.substring(0, mobile.length() - 8);
        return pre + "****" + last4;
    }

    /**
     * 处理身份证号
     *
     * @param idNo
     * @return
     */
    public static String dealIdCard(String idNo) {
        if (StringUtils.isEmpty(idNo)) {
            return "";
        }
        return idNo.substring(0, 2) + "****************";
    }

    /**
     * 处理真实姓名
     *
     * @param name
     * @return
     */
    public static String dealRealname(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        return "*" + name.substring(1);
    }

    public static String dealLastRealName(String name) {
        if (StringUtil.isEmpty(name)) {
            return "";
        }
        return name.substring(0, 1) + "*";
    }

    /**
     * 处理电子邮箱
     *
     * @param mail
     * @return
     */
    public static String dealEmail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            return "";
        }
        mail = mail.replaceAll("^(.{2}).*(?=@)", "$1****");
        return mail;
    }

    /**
     * 处理银行卡号码
     *
     * @param cardId
     * @return
     */
    public static String dealCardId(String cardId) {

        if (StringUtil.emptyOrNull(cardId)) {
            return "";
        }

        int length = cardId.length();

        if (length <= 8) {
            return cardId;
        }

        String start = cardId.substring(0, 4);
        String end = cardId.substring(length - 4);

        return start + " **** **** " + end;
    }

    /**
     * 处理银行卡号码
     *
     * @param cardId
     * @return
     */
    public static String dealCardId2(String cardId) {

        if (StringUtil.emptyOrNull(cardId)) {
            return "";
        }

        int length = cardId.length();

        if (length <= 8) {
            return cardId;
        }

        String end = cardId.substring(length - 4);

        return "尾号 " + end;
    }

    public static boolean isMobile(String mobile) {
        if (StringUtil.isEmpty(mobile) || mobile.length() != 11) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        return pattern.matcher(mobile).matches();
    }

    /**
     * 深层拷贝
     *
     * @param <T>
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T> T deepcopy(T obj) {
        return (T) JsonUtil.jsonToBean(JsonUtil.beanToJson(obj, false), obj.getClass());
    }

    /**
     * 深层拷贝 - 需要类继承序列化接口
     *
     * @param <T>
     * @param obj
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepcopySerializable(T obj) throws Exception {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;

        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        Object o = null;
        // 如果子类没有继承该接口，这一步会报错
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);

            o = ois.readObject();
            return (T) o;
        } catch (Exception e) {
            throw new Exception("对象中包含没有继承序列化的对象");
        } finally {
            try {
                baos.close();
                oos.close();
                bais.close();
                ois.close();
            } catch (Exception e2) {
                // 这里报错不需要处理
            }
        }
    }

    /**
     * timestamp转时分秒
     *
     * @param _timestamp
     * @return
     */
    public static String timestampToHHMMSS(BigDecimal _timestamp) {
        boolean isNegative = false;
        if (_timestamp.compareTo(BigDecimal.ZERO) < 0) {
            _timestamp = _timestamp.negate();
            isNegative = true;
        }
        BigDecimal hh = _timestamp.divide(new BigDecimal("3600"), 0, BigDecimal.ROUND_FLOOR);
        BigDecimal mm = _timestamp.subtract(hh.multiply(new BigDecimal("3600"))).divide(new BigDecimal("60"), 0, BigDecimal.ROUND_FLOOR);
        return (isNegative ? "负" : "") + (hh.compareTo(BigDecimal.ZERO) > 0 ? hh + "时" : "") + (mm.compareTo(BigDecimal.ZERO) > 0 ? mm + "分" : "") + _timestamp.subtract(hh.multiply(new BigDecimal("3600"))).subtract(mm.multiply(new BigDecimal("60"))) + "秒";
    }

    public static String formatYYYYMM(Date time) {
        return sdf_time_yyyyMM.format(time);
    }

    /**
     * 位数不足时前面补零
     *
     * @param num
     * @param digit
     * @return
     */
    public static String fillDigit(Integer num, int digit) {
        if (num == null) {
            num = 0;
        }
        return String.format("%0" + digit + "d", num);
    }

    /**
     * 位数不足时前面补零, 位数超长时截取前面几位
     *
     * @param num
     * @param digit
     * @return
     */
    public static String fillDigitOrTruncate(Integer num, int digit) {
        if (num == null) {
            num = 0;
        }
        return String.format("%0" + digit + "d", num.toString().length() > digit ? Integer.parseInt(num.toString().substring(0, digit)) : num);
    }

    public static Object mapToBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, Object> beanToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

    public static Date addTimeUnit(Date date, Integer timeUnit, Integer amount) {
        if (date == null || timeUnit == null || amount == null) {
            return new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(timeUnit, amount);
        return c.getTime();
    }

    /**
     * VO类对拷, 支持不同类型的VO, 两个参数new完后传入
     *
     * @param src
     * @param target
     */
    public static void VOConverter(Object src, Object target) {
        if (src == null || target == null) {
            return;
        }
        Class<?> srcClassType = src.getClass();
        Class<?> targetClassType = target.getClass();
        //获得对象的所有成员变量
        Field[] srcFields = srcClassType.getDeclaredFields();
        for (Field srcField : srcFields) {
            try {
                //获取成员变量的名字
                String name = srcField.getName();    //获取成员变量的名字，此处为id，name,age
//                System.out.println(name);
                //获取get和set方法的名字
                String firstLetter = name.substring(0, 1).toUpperCase();    //将属性的首字母转换为大写
                String getMethodName = "get" + firstLetter + name.substring(1);
                if ("boolean".equalsIgnoreCase(srcField.getType().getName())) {
                    getMethodName = "is" + firstLetter + name.substring(1);
                }
                String setMethodName = "set" + firstLetter + name.substring(1);
//                System.out.println(getMethodName + "," + setMethodName);
                //获取方法对象
                Method getMethod = srcClassType.getMethod(getMethodName, new Class[]{});
                Method setMethod = targetClassType.getMethod(setMethodName, new Class[]{srcField.getType()});//注意set方法需要传入参数类型
                //调用get方法获取旧的对象的值
                Object value = getMethod.invoke(src, new Object[]{});
                //调用set方法将这个值复制到新的对象中去
                setMethod.invoke(target, new Object[]{value});
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return;
    }

    /**
     * 创建x位的随机字符或数字
     *
     * @param chars
     * @param length
     */
    private static String random(String chars, int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(chars.length());
            sb.append(chars.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 创建x位的随机字符
     * 没有I和O
     *
     * @param length
     */
    public static String randomChar(int length) {
        return StringUtil.random("ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz", length);
    }

    /**
     * 创建x位的随机字符或数字
     * 没有I和O
     *
     * @param length
     */
    public static String randomCharAndDigit(int length) {
        return StringUtil.random("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz0123456789", length);
    }

    /**
     * 创建x位的随机数字
     *
     * @param length
     */
    public static String randomDigit(int length) {
        return StringUtil.random("0123456789", length);
    }

    /**
     * 目前应用于抽奖活动页面 先取昵称nickname: 具体规则如下：如果为昵称的 1：4个以上字符的
     * 前面保留两个字符，后面保留两个字符，中间用“**
     * **”显示，如：小胡显示为“小***胡”，贝克汉姆显示为“贝***姆”，123456789显示为“12***89 “ 2：不足4个字符的则 显示
     * 昵称前两个字符*** 剩余字符，剩余字符如没有则为空 如：天 显示为天***，rew显示为 re***w。
     * 没有取手机号mobile:显示前三位+***+后四位 再没有取邮箱username:邮箱名的前两个字符+**+@邮箱域名.前面的部分+*
     * 不取真实姓名
     *
     * @return
     * @see com.hoomsun.model.User.getDisplayNameFuzzy
     */
    public static String getDisplayNameFuzzy(String nickName, String username, String mobile) {
        if (StringUtil.isNotEmpty(nickName)) {
            try {
                if (nickName.getBytes().length <= 4) {
                    return nickName.getBytes().length != nickName.length() ? (nickName.substring(0, 1) + "***")
                            : (nickName.substring(0, 2) + "***" + (nickName.substring(2)));
                }
                return nickName.getBytes().length != nickName.length() ? (nickName.substring(0, 1) + "***" + nickName
                        .substring(nickName.length() - 1)) : (nickName.substring(0, 2) + "***" + nickName
                        .substring(nickName.length() - 2));
            } catch (Exception e) {
                return "******";
            }
        } else if (StringUtil.isNotEmpty(username)) {
            try {
                return username.substring(0, 2) + "**@*"
                        + username.split("@")[1].substring(username.split("@")[1].lastIndexOf("."));
            } catch (Exception e) {
                return "********";
            }
        } else if (StringUtil.isNotEmpty(mobile)) {
            try {
                return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            } catch (Exception e) {
                return "***********";
            }
        } else {
            return "*******";
        }
    }

    /**
     * 手机号mobile:显示前三位+***+后四位
     *
     * @param mobile
     * @return
     * @see com.hoomsun.model.User.getDisplayNameFuzzy
     */
    public static String getMobileFuzzy(String mobile) {
        if (StringUtil.isNotEmpty(mobile)) {
            try {
                return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            } catch (Exception e) {
                return "***********";
            }
        }
        return "*******";
    }

    /**
     * 邮箱:邮箱名的前两个字符+**+@邮箱域名.前面的部分+*
     *
     * @param email
     * @return
     * @see com.hoomsun.model.User.getDisplayNameFuzzy
     */
    public static String getEmailFuzzy(String email) {
        if (StringUtil.isNotEmpty(email)) {
            try {
                return email.substring(0, 2) + "**@*"
                        + email.split("@")[1].substring(email.split("@")[1].lastIndexOf("."));
            } catch (Exception e) {
                return "********";
            }
        }
        return "*******";
    }

    /**
     * 身份证号:身份证号的前6个字符+***+后1个字符
     *
     * @param idcard
     * @return
     */
    public static String getIdcardFuzzy(String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            try {
                return idcard.substring(0, 6) + "***" + idcard.substring(idcard.length() - 1, idcard.length());
            } catch (Exception e) {
                return "********";
            }
        }
        return "*******";
    }

    /**
     * 银行卡号:前4个字符+***+后4个字符
     *
     * @param bankcard
     * @return
     */
    public static String getBankcardFuzzy(String bankcard) {
        if (StringUtil.isNotEmpty(bankcard)) {
            try {
                return bankcard.substring(0, 4) + "***" + bankcard.substring(bankcard.length() - 4, bankcard.length());
            } catch (Exception e) {
                return "********";
            }
        }
        return "*******";
    }

    /**
     * 判断是否合法邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获得POST 过来参数设置到新的params中
     */
    public static String buildSign(Map requestParams, String privateKey) {
        Map params = new HashMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用,但是不一定能解决所有的问题,所以建议写过滤器实现编码控制
            // 如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        Properties properties = new Properties();

        for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            Object value = params.get(name);

            if (name == null || name.equalsIgnoreCase("sign") || name.equalsIgnoreCase("sign_type")) {
                continue;
            }
            properties.setProperty(name, value.toString());
        }
        String content = getSignatureContent(properties);
        if (privateKey == null) {
            return null;
        }
        String signBefore = content + privateKey;
        return StringUtil.MD5Encode(signBefore, "UTF-8");
    }

    /**
     * 功能：对交易返回参数按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param properties
     * @return
     */
    public static String getSignatureContent(Properties properties) {
        StringBuffer content = new StringBuffer();
        List keys = new ArrayList(properties.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = properties.getProperty(key);

            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        return content.toString();
    }

    /**
     * 生成随机用户名
     * 规则就是"WE"+8位数字，数字不能有4
     *
     * @return
     */
    public static String createNickName() {
        StringBuilder nickName = new StringBuilder("WE");
        Random random = new Random();
        int randomNum;
        int randomIndex = 0;
        while (true) {
            randomNum = random.nextInt(10);
            if (randomNum == 4) {
                continue;
            }
            randomIndex++;
            nickName.append(randomNum);
            if (randomIndex == 8) {
                break;
            }
        }
        return nickName.toString();
    }

    /**
     * 给合作平台生成随机用户名
     * 规则就是"WE"+8位数字，数字不能有4
     *
     * @return
     */
    public static String createNickName(String partners) {
        StringBuilder nickName = new StringBuilder(partners);
        Random random = new Random();
        int randomNum;
        int randomIndex = 0;
        while (true) {
            randomNum = random.nextInt(10);
            if (randomNum == 4) {
                continue;
            }
            randomIndex++;
            nickName.append(randomNum);
            if (randomIndex == 8) {
                break;
            }
        }
        return nickName.toString();
    }


    public static byte[] serialize(Serializable obj) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Serializable deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return (Serializable) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

