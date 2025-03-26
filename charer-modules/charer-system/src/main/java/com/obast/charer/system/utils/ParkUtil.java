package com.obast.charer.system.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @ Author：chuan
 * @ Date：2025-01-14-21:57
 * @ Version：1.0
 * @ Description：
 */
public class ParkUtil {

    /**
     * 计算停车费
     *
     * @param entryTime  车辆入场时间
     * @param exitTime   车辆出场时间
     * @return 应支付的停车费（单位：元）
     */
    public static BigDecimal calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime, long freeDurationInMinutes, BigDecimal hourlyFee, BigDecimal dailyMaxFee) {
        LocalDate entryDate = entryTime.toLocalDate();
        LocalDate exitDate = exitTime.toLocalDate();

        //只停了一天
        if(entryDate.equals(exitDate)) {
            return calculateDailyFee(entryTime, exitTime, freeDurationInMinutes, hourlyFee, dailyMaxFee);
        } else {
            BigDecimal amount = new BigDecimal(0);
            LocalDate currentDate = entryDate;
            while (!currentDate.isAfter(exitDate)) {
                //第一天
                if(currentDate.equals(entryDate)) {
                    BigDecimal unitAmount = calculateDailyFee(entryTime, LocalDateTime.of(currentDate, LocalTime.MAX), freeDurationInMinutes, hourlyFee, dailyMaxFee);
                    amount = amount.add(unitAmount);
                //最后一天
                } else if(currentDate.equals(exitDate)) {
                    BigDecimal unitAmount = calculateDailyFee(LocalDateTime.of(currentDate, LocalTime.MIN), exitTime, freeDurationInMinutes, hourlyFee, dailyMaxFee);
                    amount = amount.add(unitAmount);
                //中间天
                } else {
                    BigDecimal unitAmount = calculateDailyFee(LocalDateTime.of(currentDate, LocalTime.MIN), LocalDateTime.of(currentDate, LocalTime.MAX), freeDurationInMinutes, hourlyFee, dailyMaxFee);
                    amount = amount.add(unitAmount);
                }
                currentDate = currentDate.plusDays(1);
            }

            return amount;
        }
    }

    /**
     * 根据停车时长计算当天的费用
     *
     * @param startTime        当天开始停车时间
     * @param endTime          当天结束停车时间
     * @param freeDurationInMinutes 免费时长（单位：分钟）
     * @param hourlyFee        每小时费用（单位：元）
     * @param maxFeeForDay     当天最高费用（单位：元）
     * @return 当天的停车费（单位：元）
     */
    private static BigDecimal calculateDailyFee(LocalDateTime startTime, LocalDateTime endTime, long freeDurationInMinutes, BigDecimal hourlyFee, BigDecimal maxFeeForDay) {
        long parkingDurationInMinutes = ChronoUnit.MINUTES.between(startTime, endTime);

        //在免费时长内
        if(parkingDurationInMinutes <= freeDurationInMinutes) {
            return new BigDecimal(0);
        //超出了免费时长
        } else {
            //计算多少个单位
            int qty = (int) (parkingDurationInMinutes / 60);
            int remainder = (int) (parkingDurationInMinutes % 60); // 余数
            if(remainder > 15) {
                qty++;
            }
            BigDecimal amount = BigDecimal.valueOf(qty).multiply(hourlyFee);

            return amount.compareTo(maxFeeForDay) < 0 ? amount : maxFeeForDay;
        }
    }
}
