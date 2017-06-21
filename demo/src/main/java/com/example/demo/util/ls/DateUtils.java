package com.example.demo.util.ls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.h2.util.StringUtils;

/**
 * 时间处理工具类
 *
 * @author Administrator
 */
public class DateUtils {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static final Object object = new Object();

    private static final Long oneDayLong = 1000*60*60*24L;

    private static final Long oneHourLong = 1000*60*60L;

    private static final Long oneMinuteLong = 1000*60L;

    private static final Long oneSecondLong = 1000L;

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern
     *            日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException
     *             异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(final String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = DateUtils.threadLocal.get();
        if (dateFormat == null) {
            synchronized (DateUtils.object) {
                dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setLenient(false);
                DateUtils.threadLocal.set(dateFormat);
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return 日期
     */
    public static Date StringToDate(final String date, final String pattern) {
        Date myDate = null;
        if ((date != null) && !StringUtils.isNullOrEmpty(date) && !"".equals(date)) {
            try {
                myDate = DateUtils.getDateFormat(pattern).parse(date);
            } catch (final Exception e) {
                //throw new RuntimeException("1", "时间字符串格式无法转换为时间格式.");
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param dateStyle
     *            日期风格
     * @return 日期
     */
    public static Date StringToDate(final String date, final DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = DateUtils.StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return 日期字符串
     */
    public static String DateToString(final Date date, final String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = DateUtils.getDateFormat(pattern).format(date);
            } catch (final Exception e) {
                //throw new RuntimeException("1", "时间格式无法转换为字符串格式.");
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param dateStyle
     *            日期风格
     * @return 日期字符串
     */
    public static String DateToString(final Date date, final DateStyle dateStyle) {
        String dateString = null;
        if ((date != null) && (dateStyle != null)) {
            dateString = DateUtils.DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回""。
     *
     * @param date
     *            日期
     * @param dateStyle
     *            日期风格
     * @return 日期字符串
     */
    public static String DateToNotNullString(final Date date, final DateStyle dateStyle) {
        String dateString = "";
        if ((date != null) && (dateStyle != null)) {
            dateString = DateUtils.DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 开始时间加6个小时
     *
     * @param startDate
     * @return
     */
    public static Date getStartDate(final Date startDate) {
        final Calendar sca = Calendar.getInstance();
        sca.setTime(startDate);
        sca.add(Calendar.HOUR_OF_DAY, 6);

        return sca.getTime();
    }

    /**
     * 结束时间加29个小时，即第二天的凌晨5点
     *
     * @param endDate
     * @return
     */
    public static Date getEndDate(final Date endDate) {
        final Calendar eca = Calendar.getInstance();
        eca.setTime(endDate);
        eca.add(Calendar.HOUR_OF_DAY, 29);

        return eca.getTime();
    }

    public static Date getSubDate(final Date endDate) {
        final Calendar eca = Calendar.getInstance();
        eca.setTime(endDate);
        eca.add(Calendar.HOUR_OF_DAY, 22);

        return eca.getTime();
    }

    public static int getYear(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMinute(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static int getHour(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取两个日期之间的包含星期几的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
//    public static Long getDayNumsByWeekType(final Date startDate, final Date endDat) {
//        final Long days = DateUtils.daysBetween(startDate, endDate);
//        Long countDays = days / 7;
//        final Long remainingDays = days % 7;
//        final List<WeekType> weekTypeList = new ArrayList<>();
//        for (int i = 0; i < remainingDays; i++) {
//            weekTypeList.add(DateUtils.getWeekOfDate(startDate, i));
//        }
//        if (weekTypeList.contains(weekType)) {
//            countDays += 1;
//        }
//        return countDays;
//    }


    /**
     * 获取两个日期之间的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long daysBetween(Date startDate, Date endDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (final ParseException e) {
            //throw new RuntimeException("406", String.format("时间转换失败！"));
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        final long time2 = cal.getTimeInMillis();
        final long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Long.parseLong(String.valueOf(between_days));
    }

//    /**
//     * 获取某一日期 之后几天的 的星期
//     *
//     * @param date
//     * @param day
//     * @return
//     */
//    public static WeekType getWeekOfDate(final Date date, final int day) {
//        final WeekType[] weekOfDays = WeekType.values();
//        final Calendar calendar = Calendar.getInstance();
//        if (date == null) {
//            throw new CustomRuntimeException("406", String.format("请输入日期！"));
//        }
//        calendar.setTime(date);
//        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//        if (w < 0) {
//            w = 0;
//        }
//        return weekOfDays[w + day];
//    }

    /**
     * 获取两个日期之间的具体日期列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getListByBetweenDates(final Date startDate, final Date endDate) {
        final List<Date> result = new ArrayList<Date>();
        final Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        //		tempStart.add(Calendar.DAY_OF_YEAR, 1);

        final Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.remove(result.size()-1);
        result.add(endDate);
        return result;
    }



    /**
     * 将时间转换成只有年月日的时间。
     * @param date
     * @return
     */
    public static Date getYmdDate(final Date date) {
        Date ymdDate = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String sDate = simpleDateFormat.format(date);
        try {
            ymdDate = simpleDateFormat.parse(sDate);
        } catch (final ParseException e) {
            //throw new RuntimeException("406", String.format("时间转换异常！"));
        }
        return ymdDate;
    }

    /**
     * 获取时间增加一些分钟后的时间。
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinDate(final Date date,final Long minute) {
        final Long dateTime = date.getTime();
        final Long addMinTime = minute*1000*60;
        return new Date(dateTime + addMinTime);
    }

    /**
     * 获取两个时间间隔的分钟数。
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getMinByDate(final Date startDate,final Date endDate) {
        final Long oneMinTime = 1*1000*60L;
        final Long minBetween = (startDate.getTime()-endDate.getTime())/oneMinTime;
        return minBetween;
    }

    /**
     * 获取某个时间距离当天开始的毫秒值。
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getMsecond(final Date date) {
        final Long oneMinTime = 1*1000*60L;
        return (date.getHours()*60*oneMinTime)+(date.getMinutes()*oneMinTime);
    }

    /**
     * 获取昨天的日期。
     * @param date
     * @return
     */
    public static Date getYesterdayDate(final Date date) {
        final Long dateLong = date.getTime();
        return new Date(dateLong -DateUtils.oneDayLong);
    }

    /**
     * 取第一个参数的年月日，取第二个参数的时分秒，拼接成一个Date。
     * @param date
     * @return
     */
    public static Date getMixedDate(final Date date,final Date time) {
        final Date ymdDate = DateUtils.getYmdDate(date);
        final Long dateTime = ymdDate.getTime()+(time.getHours()*DateUtils.oneHourLong)+(time.getMinutes()*DateUtils.oneMinuteLong)+(time.getSeconds()*DateUtils.oneSecondLong);
        return new Date(dateTime);
    }


}
