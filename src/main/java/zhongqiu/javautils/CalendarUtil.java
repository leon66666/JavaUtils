package zhongqiu.javautils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangzhongqiu on 2017/6/5.
 * 日期工具类
 */
public class CalendarUtil {
    private int x; // 日期属性：年
    private int y; // 日期属性：月
    private int z; // 日期属性：日
    private final Calendar localTime; // 当前日期
    public static final SimpleDateFormat MD_FORMAT = new SimpleDateFormat("MM月dd日");
    public static final SimpleDateFormat HM_FORMAT = new SimpleDateFormat("HH:mm");

    /**
     * 日期间隔N天
     * N=1  当前日期
     * N=2 当前日期+1
     * N=-1 当前日期-1
     */
    public static Date previousNDays(Integer n) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        date = calendar.getTime();
        return date;
    }

    public static boolean isBefore(Date date, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d2 = null;
        try {
            d2 = df.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null != date && null != d2 && date.before(d2);
    }

    public static boolean isAfter(Date date, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d2 = null;
        try {
            d2 = df.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null != date && null != d2 && date.after(d2);
    }

    public static boolean isBetween(Date date, String date2, String date3) {
        return null != date && isAfter(date, date2) && isBefore(date, date3);
    }

    /**
     * 某一个日期n月相对应某一天 n 为负值表示向前 n 为正值表示向后
     */
    public static Date calDateForMonth(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    /**
     * 某一个日期n天相对应某一天 n 为负值表示向前 n 为正值表示向后
     */
    public static Date calDateForDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, n);
        return c.getTime();
    }

    /**
     * 某一个日期n小时相对应某一天 n 为负值表示向前 n 为正值表示向后
     */
    public static Date calDateForHour(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, n);
        return c.getTime();
    }

    /**
     * 某一个日期n天相对应某一天 n 为负值表示向前 n 为正值表示向后
     */
    public static Date calDateForYear(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, n);
        return c.getTime();
    }

    /**
     * 某一时刻相对应这是时刻n分钟 为负值表示向前 n 为正值表示向后
     *
     * @param date
     * @param n
     * @return
     */
    public static Date calDateForMinute(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, n);
        return c.getTime();
    }

    public CalendarUtil() {
        localTime = Calendar.getInstance();
    }

    /**
     * 功能：得到当前日期 格式为：xxxx-yy-zz (eg: 2007-12-05)
     *
     * @return String
     */
    public String today() {
        String strY = null;
        String strZ = null;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        z = localTime.get(Calendar.DATE);
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        strZ = z >= 10 ? String.valueOf(z) : ("0" + z);
        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到当前月份月初 格式为：xxxx-yy-zz (eg: 2007-12-01)
     *
     * @return String
     */
    public static String toMonth(Date time) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
        String LockStart = sdf1.format(time);
        return LockStart;
    }


    /**
     * 功能：得到当前月份月初 格式为：yy-dd HH
     *
     * @return String
     */
    public String thisMonth() {
        String strY = null;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-01";
    }

    /**
     * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2007-12-31)
     *
     * @return String
     **/
    public String thisMonthEnd() {
        String strY = null;
        String strZ = null;
        boolean leap = false;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10 || y == 12) {
            strZ = "31";
        }
        if (y == 4 || y == 6 || y == 9 || y == 11) {
            strZ = "30";
        }
        if (y == 2) {
            leap = leapYear(x);
            if (leap) {
                strZ = "29";
            } else {
                strZ = "28";
            }
        }
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到当前季度季初 格式为：xxxx-yy-zz (eg: 2007-10-01)<br>
     *
     * @return String
     */
    public String thisSeason() {
        String dateString = "";
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "01" + "-" + "01";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "04" + "-" + "01";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "07" + "-" + "01";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "10" + "-" + "01";
        }
        return dateString;
    }

    /**
     * 功能：得到当前季度季末 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
     *
     * @return String
     */
    public String thisSeasonEnd() {
        String dateString = "";
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "03" + "-" + "31";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "06" + "-" + "30";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "09" + "-" + "30";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "12" + "-" + "31";
        }
        return dateString;
    }

    /**
     * 功能：得到当前年份年初 格式为：xxxx-yy-zz (eg: 2007-01-01)<br>
     *
     * @return String
     */
    public String thisYear() {
        x = localTime.get(Calendar.YEAR);
        return x + "-01" + "-01";
    }

    /**
     * 功能：得到当前年份年底 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
     *
     * @return String
     */
    public String thisYearEnd() {
        x = localTime.get(Calendar.YEAR);
        return x + "-12" + "-31";
    }

    /**
     * 功能：判断输入年份是否为闰年<br>
     *
     * @param year
     * @return 是：true 否：false
     */
    public boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            } else
                leap = true;
        } else
            leap = false;
        return leap;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "时" + min + "分";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(Date one, Date two) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return day + "天" + hour + "时" + min + "分" + sec + "秒";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTimeM(Date one, Date two) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            return day + "天" + hour + "时" + min + "分";
        }
        return hour + "时" + min + "分" + sec + "秒";
    }

    /**
     * 两个时间相差距离多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx小时xx分xx秒
     */
    public static String getDistanceTimeHour(long diff) {
        if (diff < 0) {
            diff = -diff;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        day = diff / (24 * 60 * 60);
        hour = (diff / (60 * 60) - day * 24);
        min = ((diff / (60)) - day * 24 * 60 - hour * 60);
        sec = (diff - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return ((hour + day * 24) == 0 ? "" : ((hour + day * 24) + "时")) + min + "分" + sec + "秒";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分
     */
    public static String getDistanceTimeMM(Date one, Date two) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        return day + "天" + hour + "时" + min + "分";
    }

    public static String getAmOrPm(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 12)
            return "下午";
        else
            return "上午";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static long getDistanceMillon(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        return diff;
    }


    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistance(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            return day + "天" + hour + "时" + min + "分" + sec + "秒";
        } else {
            if (hour != 0) {
                return hour + "时" + min + "分" + sec + "秒";
            } else {
                if (min != 0) {
                    return min + "分" + sec + "秒";
                } else {
                    return sec + "秒";
                }
            }
        }

    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistance(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        Date one;
        Date two;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day != 0) {
                return day + "天" + hour + "时" + min + "分" + sec + "秒";
            } else {
                if (hour != 0) {
                    return hour + "时" + min + "分" + sec + "秒";
                } else {
                    if (min != 0) {
                        return min + "分" + sec + "秒";
                    } else {
                        return sec + "秒";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒,
     * 如果不够1分钟则只显示秒；
     *    *  如果不够1小时则显示分秒；
     *    *  如果不够一天则显示时分秒；
     *    *  如果大于1天则显示天时分
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceLevel(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            return day + "天" + hour + "时" + min + "分";
        } else {
            if (hour != 0) {
                return hour + "时" + min + "分" + sec + "秒";
            } else {
                if (min != 0) {
                    return min + "分" + sec + "秒";
                } else {
                    return sec + "秒";
                }
            }
        }

    }

    /**
     * 当天剩余秒数
     *
     * @return int
     */
    public static int getExpire() {
        long now = new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long nextDay = calendar.getTimeInMillis();
        int expire = (int) ((nextDay - now) / 1000);
        return expire;
    }
}

