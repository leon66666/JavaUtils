package zhongqiu.javautils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by wangzhongqiu on 2017/6/8.
 */
public class DateUtil {


    private static final Log log = LogFactory.getLog(DateUtil.class);

    public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
    public static final String DATE_PATTERN_MONTH = "yyyy-MM";
    public static final String DAY_EARLY_TIME = " 00:00:00";
    public static final String DAY_LAST_TIME = " 23:59:59";
    public static final String DATE_PATTERN_MINUTE_CHINESE = "MM月dd日 HH:mm";
    public static final String DATE_TIME_PATTERN_ESCROW = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_DAY_SHORT = "yyyyMMdd";
    public static final String DATE_FORMAT_DAY_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SECOND_SHORT = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_MILLS_SHORT = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_MILLS_SHORT_ALISDK = "yyyy-MM-ddHH:mm:ss.SSS";

    public static int getMaxDayOfLastMonth() {
        Date now = new Date();
        Date lastMonth = DateUtils.addMonths(now, -1);
        lastMonth = getMaxDateOfMonth(lastMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastMonth);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    public static int getYearOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int yearOfLastMonth = calendar.get(Calendar.YEAR);
        return yearOfLastMonth;
    }

    public static int getMonthOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int lastMonth = calendar.get(Calendar.MONTH) + 1;
        return lastMonth;
    }

    public static Date addMonths(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, i);
        return calendar.getTime();
    }

    public static int getCurrentStatYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    // 获取当前月份，cal.get(Calendar.MONTH)是从零开始。
    public static int getCurrentStatMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getCurrentStatDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 时间校验: 开始时间不能大于当前时间.
     */
    public static Date validateStartDate(Date startDate) {
        Date today = new Date();
        // 开始时间不能大于当前时间.
        if (startDate.compareTo(today) == 1) {
            log.warn("startDate.compareTo(today)==1, set startDate = today:" + today);
            startDate = today;
        }
        return startDate;
    }

    /**
     * 时间校验: 不能晚于当前时间(如果晚于当前时间，则替换为当前时间)
     */
    public static Date notAfterNow(Date myDate) {
        Date today = new Date();
        if (myDate.after(today)) {
            log.warn("myDate.after(today), set myDate = today:" + today);
            myDate = today;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于昨天(如果晚于昨天，则替换为昨天)
     */
    public static Date notAfterYesterday(Date myDate) {
        Date today = new Date();
        Date yesterday = DateUtils.addDays(today, -1);
        ;
        // 3. 结束时间不能大于昨天.
        if (myDate.after(yesterday)) {
            log.warn("myDate.after(yesterday), set myDate = yesterday:" + yesterday);
            myDate = yesterday;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于上一个月(如果晚于上一个月，则替换为上一个月)
     */
    public static Date notAfterLastMonth(Date myDate) {
        Date today = new Date();
        Date lastMonth = DateUtils.addMonths(today, -1);
        lastMonth = DateUtil.getMaxDateOfMonth(lastMonth);
        // 3. 结束时间不能大于上一个月.
        if (myDate.after(lastMonth)) {
            log.warn("myDate.after(lastMonth), set myDate = lastMonth:" + lastMonth);
            myDate = lastMonth;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于上一年(如果晚于上一年，则替换为上一年)
     */
    public static Date notAfterLastYear(Date myDate) {
        Date today = new Date();
        Date lastYear = DateUtils.addYears(today, -1);
        lastYear = DateUtil.getMaxDateOfYear(lastYear);
        // 3. 结束时间不能大于上一年.
        if (myDate.after(lastYear)) {
            log.warn("myDate.after(lastYear), set myDate = lastYear:" + lastYear);
            myDate = lastYear;
        }
        return myDate;
    }

    /**
     * 时间校验: myDate不能早于basicDate(如果早于basicDate，则替换为basicDate)
     *
     * @throws Exception
     */
    public static Date notBefore(Date myDate, String basicStr) throws Exception {
        Date basicDate = DateUtil.stringToDateTime(basicStr);
        // Date today = new Date();
        // Date yesterday = DateUtils.addDays(today, -1);;
        // 3. 结束时间不能大于昨天.
        if (myDate.before(basicDate)) {
            log.warn("myDate.before(basicDate), set myDate = basicDate:" + basicDate);
            myDate = basicDate;
        }
        return myDate;
    }

    /***
     * 将日期转化为字符串。 字符串格式("yyyy-MM-dd HH:mm:ss")。
     */
    public static String dateTime2String(Date date) {
        return dateToString(date, DATE_PATTERN_DEFAULT);
    }

    /***
     * 将日期转化为字符串。 字符串格式("yyyy-MM-dd")，小时、分、秒被忽略。
     */
    public static String dateToString(Date date) {
        return dateToString(date, DATE_PATTERN_DAY);
    }

    /***
     * 将日期转化为字符串
     */
    public static String dateToString(Date date, String pattern) {
        String str = "";
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            str = formater.format(date);
        } catch (Throwable e) {
            log.error(e);
        }
        return str;
    }

    /**
     * 将传入的年月日转化为Date类型
     */
    public static Date YmdToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * 将字符串转化为日期
     */
    public static Date stringToDateTime(String str) throws Exception {
        return getDateFormatOfDefault().parse(str);
    }

    /**
     * 将字符串转化为日期
     */
    public static Date stringToMediumDateTime(String str) throws ParseException {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return format.parse(str);
    }

    /**
     * 获取默认的DateFormat
     */
    public static DateFormat getDateFormatOfDefault() {
        return new SimpleDateFormat(DATE_PATTERN_DEFAULT);
    }

    /**
     * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
     * 例如："2012-07-01"或者"2012-7-1"或者"2012-7-01"或者"2012-07-1"是等价的。
     */
    public static Date stringToDate(String str, String pattern) {
        Date dateTime = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            dateTime = formater.parse(str);
        } catch (Exception e) {
            log.error(e);
        }
        return dateTime;
    }

    /**
     * 将字符串转化为日期(从一种格式到另一种格式)。
     */
    public static String StringPatternToPattern(String str, String pattern1, String pattern2) {
        Date dateTime = null;
        String productStr = "";
        try {
            if (!(str == null || str.equals(""))) {
                SimpleDateFormat formater = new SimpleDateFormat(pattern1);
                dateTime = formater.parse(str);

                SimpleDateFormat formater1 = new SimpleDateFormat(pattern2);
                productStr = formater1.format(dateTime);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return productStr;
    }

    /**
     * 日期时间带时分秒的Timestamp表示
     */
    public static Timestamp stringToDateHMS(String str) {
        Timestamp time = null;
        try {
            time = java.sql.Timestamp.valueOf(str);
        } catch (Exception ex) {
            log.error(ex);
        }
        return time;

    }

    /**
     * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
     */
    public static Date getMinDateOfHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
     */
    public static Date getMinDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMinDateOfHour(date));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        return calendar.getTime();
    }

    /**
     * 取得一年中的最早一天。
     */
    public static Date getMinDateOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一年中的最后一天
     */
    public static Date getMaxDateOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一周中的最早一天。
     */
    public static Date getMinDateOfWeek(Date date, Locale locale) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        if (locale == null)
            locale = Locale.CHINESE;
        Date tmpDate = calendar.getTime();
        if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
            if (day_of_week == 1) {// 星期天
                tmpDate = DateUtils.addDays(tmpDate, -6);
            } else {
                tmpDate = DateUtils.addDays(tmpDate, 1);
            }
        }

        return tmpDate;
    }

    /**
     * 取得一周中的最后一天
     */
    public static Date getMaxDateOfWeek(Date date, Locale locale) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        if (locale == null)
            locale = Locale.CHINESE;
        Date tmpDate = calendar.getTime();
        if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
            if (day_of_week == 1) {// 星期天
                tmpDate = DateUtils.addDays(tmpDate, -6);
            } else {
                tmpDate = DateUtils.addDays(tmpDate, 1);
            }
        }

        return tmpDate;
    }

    /**
     * 取得一月中的最早一天。
     */
    public static Date getMinDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一月中的最后一天
     */
    public static Date getMaxDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
     */
    public static Date getMaxDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的小时的59分59秒时刻的Date对象。
     */
    public static Date getMaxDateOfHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 获取2个时间相隔几秒
     */
    public static int getBetweenSecondNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return -1;

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1L;
        long TIME = 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            log.error(e);
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几分钟
     */
    public static int getBetweenMinuteNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return -1;

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1l;
        long TIME = 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几小时
     */
    public static int getBetweenHourNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return -1;

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1l;
        long TIME = 60L * 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几天(endDate+1s)
     * "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
     */
    public static int getBetweenDayNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return -1;

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long dayNumber = -1L;
        long DAY = 24L * 60L * 60L * 1000L;
        try {
            // "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
            dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;

        } catch (Exception e) {
            log.error(e);
        }
        return (int) dayNumber;
    }

    /**
     * 获取2个时间相隔几天
     *
     * @param startDate
     * @param endDate   "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算两天
     * @return
     */
    public static int getBetweenDayNumberForNormal(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return -1;

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long dayNumber = -1L;
        long DAY = 24L * 60L * 60L * 1000L;
        try {
            // "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算两天
            dayNumber = (endDate.getTime() - startDate.getTime()) / DAY;

        } catch (Exception e) {
            log.error(e);
        }
        return (int) dayNumber;
    }

    /**
     * 获取2个时间相隔几月
     */
    public static int getBetweenMonthNumber(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null)
                return -1;

            // swap start and end date
            if (startDate.after(endDate)) {
                Date tmp = endDate;
                endDate = startDate;
                startDate = tmp;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            int monthS = calendar.get(Calendar.MONTH);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int monthE = calendar.get(Calendar.MONTH);
            int yearE = calendar.get(Calendar.YEAR);

            if (yearE - yearS == 0) {
                result = monthE - monthS;
            } else {
                result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
            }

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 获取2个时间相隔几年
     */
    public static int getBetweenYearNumber(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null)
                return -1;

            // swap start and end date
            if (startDate.after(endDate)) {
                Date tmp = endDate;
                endDate = startDate;
                startDate = tmp;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int yearE = calendar.get(Calendar.YEAR);

            result = yearE - yearS;

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 按天拆分时间
     */
    public static List<Date> splitDateByDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            return null;

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startDate);

        int num = getBetweenDayNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addDays(startDate, i));
        }

        return dateList;
    }

    /**
     * 按月拆分时间
     */
    public static List<Date> splitDateByMonth(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();

        if (startDate == null || endDate == null) {
            return dateList;
        }

        dateList.add(startDate);
        int num = getBetweenMonthNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addMonths(startDate, i));
        }

        return dateList;
    }

    /**
     * 按年拆分时间
     */
    public static List<Date> splitDateByYear(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();

        if (startDate == null || endDate == null) {
            return dateList;
        }

        dateList.add(startDate);
        int num = getBetweenYearNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addYears(startDate, i));
        }

        return dateList;
    }

    /**
     * 本季度
     */
    public static List<Date> getCurrentQuarter() {
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);// 一月为0

        dateList.add(1, calendar.getTime());// 结束时间设置为当前时间

        if (month >= 0 && month <= 2) {// 第一季度
            calendar.set(Calendar.MONTH, 0);
        } else if (month >= 3 && month <= 5) {// 第二季度
            calendar.set(Calendar.MONTH, 3);
        } else if (month >= 6 && month <= 8) {// 第三季度
            calendar.set(Calendar.MONTH, 6);
        } else {// 第四季度
            calendar.set(Calendar.MONTH, 9);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        dateList.add(0, calendar.getTime());

        return dateList;
    }

    /**
     * 上季度
     */
    public static List<Date> getLastQuarter() {
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);// 一月为0

        // 如果是第一季度则返回去年的第四季度
        if (month >= 0 && month <= 2) {// 当前第一季度
            calendar.add(Calendar.YEAR, -1);// 退到去年
            calendar.set(Calendar.MONTH, 9);// 去年十月
        } else if (month >= 3 && month <= 5) {// 当前第二季度
            calendar.set(Calendar.MONTH, 0);
        } else if (month >= 6 && month <= 8) {// 当前第三季度
            calendar.set(Calendar.MONTH, 3);
        } else {// 当前第四季度
            calendar.set(Calendar.MONTH, 6);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        dateList.add(0, calendar.getTime());

        calendar.add(Calendar.MONTH, 3);// 加3个月，到下个季度的第一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);// 退一天，得到上季度的最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        dateList.add(1, calendar.getTime());

        return dateList;
    }

    /**
     * 返回2个日期中的大者
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * 返回不大于date2的日期 如果 date1 >= date2 返回date2 如果 date1 < date2 返回date1
     */
    public static Date ceil(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date2;
        } else {
            return date1;
        }
    }

    /**
     * 返回不小于date2的日期 如果 date1 >= date2 返回date1 如果 date1 < date2 返回date2
     */
    public static Date floor(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * 返回2个日期中的小者
     */
    public static Date min(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date2;
        } else {
            return date1;
        }
    }

    /**
     * 判断输入日期是否是一天中的最大时刻
     */
    public static boolean isMaxDayOfDay(Date date1, String precision) {
        if (date1 == null)
            return false;
        Date date2 = getMaxDateOfDay(date1);
        int diffNum = 0;
        if ("HH".equals(precision)) {
            diffNum = getBetweenHourNumber(date1, date2);
        } else if ("mm".equals(precision)) {
            diffNum = getBetweenMinuteNumber(date1, date2);
        } else {
            diffNum = getBetweenSecondNumber(date1, date2);
        }
        return diffNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最小时刻
     */
    public static boolean isMinDayOfDay(Date date1, String precision) {
        if (date1 == null)
            return false;
        Date date2 = getMinDateOfDay(date1);
        int diffNum = 0;
        if ("HH".equals(precision)) {
            diffNum = getBetweenHourNumber(date1, date2);
        } else if ("mm".equals(precision)) {
            diffNum = getBetweenMinuteNumber(date1, date2);
        } else {
            diffNum = getBetweenSecondNumber(date1, date2);
        }
        return diffNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最大时刻
     */
    public static boolean isMaxDayOfDay(Date date1) {
        if (date1 == null)
            return false;
        Date date2 = getMaxDateOfDay(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最小时刻
     */
    public static boolean isMinDayOfDay(Date date1) {
        if (date1 == null)
            return false;
        Date date2 = getMinDateOfDay(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一月中的最大时刻
     */
    public static boolean isMaxDayOfMonth(Date date1) {
        if (date1 == null)
            return false;
        Date date2 = getMaxDateOfMonth(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一月中的最小时刻
     */
    public static boolean isMinDayOfMonth(Date date1) {
        if (date1 == null)
            return false;
        Date date2 = getMinDateOfMonth(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 输入日期是否为同一天.
     */
    public static boolean isTheSameDay(Date startDate, Date endDate) {
        String startDateStr = dateToString(startDate);
        String endDateStr = dateToString(endDate);
        return startDateStr.equals(endDateStr);
    }

    /**
     * 功能：获取昨天最大时间。 输入: 2010-01-31 00:00:00 返回：2010-01-30 23:59:59
     */
    public static Date getLastMaxDay(Date startDate) {
        if (startDate == null) {
            return null;
        }
        startDate = DateUtils.addDays(startDate, -1);
        return DateUtil.getMaxDateOfDay(startDate);
    }

    /**
     * 根据字符串时间,返回Calendar
     */
    public static Calendar getCalendar(String datetimeStr) {
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotBlank(datetimeStr)) {
            Date date = DateUtil.stringToDate(datetimeStr, DATE_PATTERN_DEFAULT);
            cal.setTime(date);
        }
        return cal;
    }

    /**
     * startStr 或者 startStr-endStr
     */
    public static String getDifferentStr(String startStr, String endStr) {
        String dateRangeStr = "";
        if (startStr.equals(endStr)) {
            dateRangeStr = startStr;
        } else {
            dateRangeStr = startStr + "-" + endStr;
        }
        return dateRangeStr;
    }

    /**
     * 给定一个日期和天数，得到这个日期+天数的日期
     *
     * @param date 指定日期
     * @param num  天数
     * @return
     */
    public static String getNextDay(String date, int num) {
        Date d = stringToDate(date, DATE_PATTERN_DAY);
        Calendar ca = Calendar.getInstance();
        ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        ca.setTime(d);

        int day = ca.get(Calendar.DATE);
        day = day + num;
        ca.set(Calendar.DATE, day);
        return getFormatDateTime(ca.getTime(), DATE_PATTERN_DAY);

    }

    /**
     * 给定一个日期和天数，得到这个日期+天数的日期
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getNextDay(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        ca.setTime(date);

        int day = ca.get(Calendar.DATE);
        day = day + num;
        ca.set(Calendar.DATE, day);
        return ca.getTime();
    }

    /**
     * 根据指定格式获取日期数据
     *
     * @param date    ：指定日期
     * @param pattern ：日期格式
     * @return
     */
    private static String getFormatDateTime(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format.format(date);
    }

    /**
     * 获取给定日期的下一个月的日期的最晚时间
     *
     * @param startDate
     * @return
     */
    public static Date getNextMonthDay(Date startDate) {
        // 是不是
        // int month = startDate.getMonth();
        Date monthEndDate = getMaxDateOfMonth(startDate);
        Date nextMonth = DateUtils.addMonths(startDate, 1);
        nextMonth = stringToDate(dateToString(nextMonth, DATE_PATTERN_DAY) + DAY_LAST_TIME, DATE_PATTERN_DEFAULT);
        if (isTheSameDay(startDate, monthEndDate)) {
            nextMonth = getMaxDateOfMonth(nextMonth);
        }
        return nextMonth;
    }

    /**
     * 获取一天最早时间 如2014-11-11 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getDateEarlyTime(Date date) {
        try {
            return stringToDateTime(DateUtil.dateToString(date) + DAY_EARLY_TIME);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 获取一天最晚时间 如2014-11-11 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getDateLastTime(Date date) {
        try {
            return stringToDateTime(DateUtil.dateToString(date) + DAY_LAST_TIME);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 根据秒获取还剩时分秒
     *
     * @param seconds
     * @return
     */
    public static String getDateStrBySecond(long seconds) {

        long hour = seconds / (60 * 60);//时
        long minute = (seconds - (60 * 60 * hour)) / 60;
        long second = seconds - (60 * 60 * hour) - (60 * minute);
        String dateStr = "";
        if (hour > 0) {
            dateStr = dateStr + hour + "小时";
        }
        if (minute > 0) {
            dateStr = dateStr + minute + "分钟";
        }
        if (second > 0) {
            dateStr = dateStr + second + "秒";
        }

        return dateStr;
    }

    /**
     * 获取某一日期的起始时间（0点0分0秒），参数为null时则返回当前日期的起始时间
     *
     * @param date
     * @return Date
     */
    public static Date getStartTime(Date date) {
        Calendar now = Calendar.getInstance();
        if (date != null) {
            now.setTime(date);
        }
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * 获取某一日期的结束时间（23点59分59秒），参数为null时则返回当前日期的结束时间
     *
     * @param date
     * @return Date
     */
    public static Date getEndTime(Date date) {
        Calendar now = Calendar.getInstance();
        if (date != null) {
            now.setTime(date);
        }
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now.getTime();
    }

    /**
     * 从String类型的时间获取年份
     */
    public static int getYear(String time, String pattern) {
        Date date = parse(time, pattern);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        return year;
    }

    /**
     * 从String类型的时间获取月份
     */
    public static int getMonth(String time, String pattern) {
        Date date = parse(time, pattern);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH); // 一月为0
        return month + 1;
    }

    /**
     * 从String类型的时间获取日期
     */
    public static int getDay(String time, String pattern) {
        Date date = parse(time, pattern);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int day = ca.get(Calendar.DATE);
        return day;
    }

    /**
     * 将Date类型的时间按某种格式转换为String型
     */
    public static String format(Date time, String pattern) {
        if (time == null || StringUtils.isEmpty(pattern)) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat format = getFormat(pattern);
        return format.format(time);
    }

    /**
     * 将String类型的时间转换为Date型
     * 支持格式有：yyyyMMdd
     *
     * @param time
     * @return
     */
    public static Date parse(String time) {
        try {
            if (time.length() == 8) {
                return parse(time, DATE_FORMAT_DAY_SHORT);
            } else if (time.length() == 14) {
                return parse(time, DATE_FORMAT_SECOND_SHORT);
            } else if (time.length() == 19) {
                return parse(time, DATE_FORMAT_SECOND_SHORT);
            } else {
                throw new IllegalArgumentException("Parse Date Error: " + time);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse Date Error: " + time);
        }
    }

    /**
     * 将String类型的时间按某种格式转换为Date型
     */
    public static Date parse(String time, String pattern) {
        try {
            SimpleDateFormat format = getFormat(pattern);
            return format.parse(time);
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse Date Error: " + time + ", With Pattern: " + pattern);
        }
    }

    /**
     * 获取Date所属月的第一天日期
     *
     * @return Date 默认null
     */
    public static Date getMonthFirstDate(Date date) {
        if (null == date) {
            return null;
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDate = ca.getTime();
        return firstDate;
    }

    /**
     * 获取Date所属月的第一天日期
     *
     * @param pattern 时间格式,默认为yyyy-MM-dd
     * @return String 默认null
     */
    public static String getMonthFirstDate(Date date, String pattern) {
        Date firstDate = getMonthFirstDate(date);
        if (null == firstDate) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = DATE_FORMAT_DAY;
        }

        return format(firstDate, pattern);
    }

    /**
     * 获取Date所属月的最后一天日期
     *
     * @return Date 默认null
     */
    public static Date getMonthLastDate(Date date) {
        if (null == date) {
            return null;
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);

        Date lastDate = ca.getTime();
        return lastDate;
    }

    /**
     * 获取Date所属月的最后一天日期
     *
     * @param pattern 时间格式,默认为yyyy-MM-dd
     * @return String 默认null
     */
    public static String getMonthLastDate(Date date, String pattern) {
        Date lastDate = getMonthLastDate(date);
        if (null == lastDate) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = DATE_FORMAT_DAY;
        }

        return format(lastDate, pattern);
    }

    /**
     * 计算两个日期间隔的秒数
     *
     * @param firstDate 小者
     * @param lastDate  大者
     * @return int 默认-1
     */
    public static long getTimeIntervalMins(Date firstDate, Date lastDate) {
        if (null == firstDate || null == lastDate) {
            return -1;
        }

        long intervalMilli = lastDate.getTime() - firstDate.getTime();
        return (intervalMilli / (1000));
    }

    /**
     * 计算两个日期间隔的天数
     *
     * @param firstDate 小者
     * @param lastDate  大者
     * @return int 默认-1
     */
    public static int getDayNum(Date firstDate, Date lastDate) {
        long timeInterval = getTimeIntervalMins(firstDate, lastDate);
        long between_days = timeInterval / (3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    static SimpleDateFormat getFormat(String pattern) {
        Map<String, SimpleDateFormat> cache = _simpleDateFormatCache.get();
        SimpleDateFormat format = cache.get(pattern);
        if (format != null) {
            return format;
        }

        // 防止cache过大
        if (cache.size() > 20) {
            cache.clear();
        }
        cache.put(pattern, format = new SimpleDateFormat(pattern));
        return format;
    }

    static ThreadLocal<Map<String, SimpleDateFormat>>
            _simpleDateFormatCache =
            new ThreadLocal<Map<String, SimpleDateFormat>>() {
                protected Map<String, SimpleDateFormat> initialValue() {
                    return new ConcurrentHashMap<String, SimpleDateFormat>();
                }
            };
    static final Date _emptyDate = new Date(0);


    public static int getIntervalSec(long t1, long t2) {
        return (int) (t1 - t2);
    }

    public static String getDateByTimeStamp(long timestamp) {
        Long timestampMs = timestamp * 1000;
        String date = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss").format(new Date(timestampMs));
        return date;
    }

    /**
     * 在文件服务中，生成T-1日 15:00:00 至 T日 14：59：59 跨天类型
     * 后续会在这里进行容错，-1 -2 -3 进行之前三天的时间获取
     *
     * @return
     */
    public static Map<String, Date> generateDate(String right, String left) {
        final SimpleDateFormat dealDate = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat dealDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String today = dealDate.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dealDate.format(calendar.getTime());
        today += right;
        yesterday += left;
        Map<String, Date> dateMap = new HashMap();
        Date todayDate = null;
        Date yesterdayDate = null;
        try {
            todayDate = dealDateTime.parse(today);
            yesterdayDate = dealDateTime.parse(yesterday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dateMap.put("rightDate", todayDate);
        dateMap.put("leftDate", yesterdayDate);

        return dateMap;
    }

    /**
     * 文件服务中，上传文件需要用到的时间， 当天类型
     * 后续会在这里进行容错，-1 -2 -3 进行之前三天的时间获取
     *
     * @return
     */
    public static Map<String, Date> generateUploadDate() {

        final SimpleDateFormat dealDate = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat dealDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        /**
         * 右侧时间为当前时间
         * 左侧时间默认为当前日的15：00：00
         */
        //calendar.add(Calendar.DATE, -1);
        String right = dealDate.format(calendar.getTime());
        String left = dealDate.format(calendar.getTime());
        left += " 15:00:00";
        right += " 17:00:00";
        Map<String, Date> dateMap = new HashMap();
        Date rightDate = null;
        Date leftDate = null;
        try {
            rightDate = dealDateTime.parse(right);
            leftDate = dealDateTime.parse(left);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dateMap.put("rightDate", rightDate);
        dateMap.put("leftDate", leftDate);

        return dateMap;
    }

    /**
     * 获取偏移量日期
     *
     * @param Offset
     * @return
     */
    public static String getOffsetDateStr(int Offset) {

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, Offset);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String offsetDate = format.format(ca.getTime());
        return offsetDate;
    }

    public static Date getOffsetDate(int Offset) {

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, Offset);
        return ca.getTime();
    }

    /**
     * 格式化成   01月01日格式
     *
     * @return
     */
    public static String getMonthDay(Date now) {
        String monthDay;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        if (month < 9) {
            monthDay = "0" + (month + 1) + "月";
        } else {
            monthDay = String.valueOf(month + 1) + "月";
        }
        if (date < 10) {
            monthDay = monthDay + "0" + date + "日";
        } else {
            monthDay = monthDay + date + "日";
        }
        return monthDay;
    }

    /**
     * 将String类型的时间按某种格式转换为Date型
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Map<String, Date> createMapTime(String startTime, String endTime) {
        try {
            if (startTime == null || "".equals(startTime)) {
                startTime = "2016-01-01";
            }
            if (endTime == null || "".equals(endTime)) {
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                endTime = formatter.format(now);
            }
            Map<String, Date> timeMap = new HashMap<String, Date>();
            String newStartTime = startTime + " 00:00:00";
            String newEndTime = endTime + " 23:59:59";
            SimpleDateFormat format = getFormat("yyyy-MM-dd HH:mm:ss");
            timeMap.put("start", format.parse(newStartTime));
            timeMap.put("end", format.parse(newEndTime));
            return timeMap;
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse Date Error: " + startTime + ", With Pattern: ");
        }
    }

    /**
     * 将String类型的时间按某种格式转换为StringDate型
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Map<String, String> createMapStringTime(String startTime, String endTime) {
        try {
            if (startTime == null || "".equals(startTime)) {
                startTime = "2016-01-01";
            }
            if (endTime == null || "".equals(endTime)) {
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                endTime = formatter.format(now);
            }
            Map<String, String> timeMap = new HashMap<String, String>();
            String newStartTime = startTime + " 00:00:00";
            String newEndTime = endTime + " 23:59:59";
            timeMap.put("start", newStartTime);
            timeMap.put("end", newEndTime);
            return timeMap;
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse Date Error: " + startTime + ", With Pattern: ");
        }
    }

    /**
     * 处理查询中开始时间
     *
     * @param startDate
     * @return
     */
    public static Date getStartDate(String startDate) {
        Date sDate;
        if (null != startDate && !"".equals(startDate)) {
            sDate = stringToDate(startDate + " 00:00:00", DATE_PATTERN_DAY);
        } else {
            sDate = new Date();
            sDate = stringToDate(dateToString(sDate, DATE_PATTERN_DAY) + " 00:00:00",
                    DATE_PATTERN_DAY);
        }
        return sDate;
    }

    /**
     * 处理查询中得结束时间
     *
     * @param endDate
     * @param sDate
     * @return
     */
    public static Date getEndDate(String endDate, Date sDate) {
        Date eDate;

        if (null != endDate && !"".equals(endDate)) {
            eDate = stringToDate(endDate + " 23:59:59", DATE_PATTERN_DEFAULT);
        } else {
            eDate = getNextMonthDay(sDate);
        }
        return eDate;
    }

    /**
     * 处理查询中得结束时间
     *
     * @param endDate
     * @return
     */
    public static Date getEndDate(String endDate) {
        Date eDate = null;
        if (null != endDate && !"".equals(endDate)) {
            eDate = stringToDate(endDate + " 23:59:59", DATE_PATTERN_DEFAULT);
        }
        return eDate;
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println(getBetweenDayNumberForNormal(format.parse("2015-03-27 00:00:01"), format.parse("2016-03-27 00:00:00")));
            System.out.println(getNextDay(new Date(), -1));
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
}