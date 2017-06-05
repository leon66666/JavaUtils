package zhongqiu.javautils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangzhongqiu on 2017/6/2.
 * 预计日期工具类
 */
public class ExpectedDateUtil {

    public static Logger logger = LoggerFactory.getLogger(ExpectedDateUtil.class);

    /**
     * 法定节假日列表
     */
    private static Set<String> holidays = new HashSet<String>();
    /**
     * 工作日
     */
    private static Set<String> workdays = new HashSet<String>();
    private static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        try {
            InputStream is = ExpectedDateUtil.class.getClassLoader().getResourceAsStream("holidays.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String holiday = null;
            while ((holiday = br.readLine()) != null) {
                if (!holiday.trim().isEmpty()) {
                    holidays.add(holiday.trim());
                }
            }
            if (br != null) {
                br.close();
            }
            InputStream workis = ExpectedDateUtil.class.getClassLoader().getResourceAsStream("workdays.txt");
            BufferedReader wkbr = new BufferedReader(new InputStreamReader(workis));
            String workday = null;
            while ((workday = wkbr.readLine()) != null) {
                if (!workday.trim().isEmpty()) {
                    workdays.add(workday.trim());
                }
            }
            if (wkbr != null) {
                wkbr.close();
            }
        } catch (Exception e) {
            logger.error("预计日期工具类初始化失败：{}", e.getMessage());
        }
    }

    /**
     * 是否是周末
     *
     * @param calendar
     * @return true, 是;false,不是;
     */
    public static boolean isWeekend(Calendar calendar) {
        // 1是周日，7是周六
        return calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7;
    }

    /**
     * 是否是法定节假日
     *
     * @param date
     * @return true, 是;false,不是;
     */
    public static boolean isHoliday(Date date) {
        return holidays.contains(dateFormat.format(date));
    }

    /**
     * 是否为工作日
     *
     * @param date
     * @return
     */
    public static boolean isWorkday(Date date) {
        return workdays.contains(dateFormat.format(date));
    }

    /**
     * 计算预计日期，不包含周末和法定节假日
     *
     * @param date 开始时间
     * @param days 天数
     * @return 预计日期
     */
    public static Date calculateExpectedDate(Date date, int days) {
        calendar.setTime(date);
        for (int i = 1; i <= days; i++) {
            do {
                calendar.add(Calendar.DATE, 1);
            } while ((isWeekend(calendar) || isHoliday(calendar.getTime())) && !isWorkday(calendar.getTime()));
        }
        return calendar.getTime();
    }

    /**
     * 是否工作时间，不包括法定假日和休息日，早9点前和17点以后时间,我们限制到16:45:00
     *
     * @param date 开始时间
     * @return true, 是;false,不是;
     */
    public static boolean isWorkTime(Date date) {
        calendar.setTime(date);
        if (isWorkday(date)) {
            return true;
        }
        if (isWeekend(calendar) || isHoliday(calendar.getTime())) {
            return false;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        String startStr = dateStr + " 09:00:00";
        String endStr = dateStr + " 16:45:00";
        if (!CalendarUtil.isBetween(date, startStr, endStr)) {
            return false;
        }
        return true;
    }
}

