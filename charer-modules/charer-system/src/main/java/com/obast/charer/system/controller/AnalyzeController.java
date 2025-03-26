package com.obast.charer.system.controller;

import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.properties.I18nLocaleProperties;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.stats.DataItem;
import com.obast.charer.model.topup.Topup;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.qo.*;
import com.obast.charer.data.business.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.time.LocalDateTime;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：运营统计
 */
@Api(tags = {"运营统计"})
@Slf4j
@RestController
@RequestMapping("/admin/analyze")
public class AnalyzeController {

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private ITopupData topupData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IProductData productData;

    @Autowired
    CharerProperties charerProperties;

    @ApiOperation(value = "统计", notes = "统计", httpMethod = "统计")
    @SaCheckPermission("business:analyze:list")
    @PostMapping("/summary")
    public Map<?,?> summary() {
        Map<String, Object> result = new HashMap<>();

        String language = charerProperties.getSystem().getI18n().getLanguage();
        I18nLocaleProperties i18nLocale = Arrays.stream(charerProperties.getSystem().getI18n().getLocales()).filter(item->item.getLocale().equals(language)).findFirst().orElse(null);
        String dateFormat = Constants.DATE_FORMAT;
        if(i18nLocale != null) {
            dateFormat = i18nLocale.getDateFormat();
        }

        TimeZone timeZone = TimeZone.getTimeZone(Constants.TIMEZONE);
        if(i18nLocale != null) {
            timeZone = TimeZone.getTimeZone(i18nLocale.getTimezone());
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        LocalDate now = LocalDate.now();
        // 获取当天的开始时间
        LocalDateTime startOfDay = now.atStartOfDay();
        ZonedDateTime zonedStartTime = startOfDay.atZone(timeZone.toZoneId());
        Instant instantStartTime = zonedStartTime.toInstant();
        Date startTime = Date.from(instantStartTime);

        String startTimeStr = sdf.format(startTime);

        // 获取当天的结束时间
        LocalDateTime endOfDay = now.atTime(LocalTime.MAX); // 23:59:59.999999999
        ZonedDateTime zonedEndTime = endOfDay.atZone(timeZone.toZoneId());
        Instant instantEndTime = zonedEndTime.toInstant();
        Date endTime = Date.from(instantEndTime);
        String endTimeStr = sdf.format(endTime);

        Map<String,Object> orderSummary = ordersData.findSummary();

        DecimalFormat df = new DecimalFormat("#,###.##");

        result.put("orderTotalAmount", df.format(orderSummary.getOrDefault("order_amount", 0)));
        result.put("orderTotalCount", orderSummary.getOrDefault("order_count", "0"));

        result.put("orderElecAmount", df.format(orderSummary.getOrDefault("order_elec_amount", 0)));
        result.put("orderServiceAmount", df.format(orderSummary.getOrDefault("order_service_amount", 0)));
        result.put("orderParkAmount", df.format(orderSummary.getOrDefault("order_park_amount", 0)));

        Map<String,Object> orderTodaySummary = ordersData.findSummaryByCreateTime(startTimeStr, endTimeStr);
        result.put("orderTodayAmount", df.format(orderTodaySummary.getOrDefault("order_amount", 0)));
        result.put("orderTodayElecAmount", df.format(orderTodaySummary.getOrDefault("order_elec_amount", 0)));
        result.put("orderTodayServiceAmount", df.format(orderTodaySummary.getOrDefault("order_service_amount", 0)));
        result.put("orderTodayParkAmount", df.format(orderTodaySummary.getOrDefault("order_park_amount", 0)));


        Map<String,Object> topupSummary = topupData.findSummary();
        result.put("topupAmount", df.format(topupSummary.getOrDefault("paid_amount", 0)));

        Map<String,Object> topupTodaySummary = topupData.findSummaryByCreateTime(startTimeStr, endTimeStr);
        result.put("topupTodayAmount", df.format(topupTodaySummary.getOrDefault("paid_amount", 0)));

        return result;
    }

    @ApiOperation(value = "充电桩分类统计", notes = "充电桩分类统计", httpMethod = "POST")
    @SaCheckPermission("business:analyze:list")
    @PostMapping("/chargerCategory")
    public Map<?,?> chargerCategory() {
        Map<String, Object> result = new HashMap<>();

        List<DataItem> chargerCategory = new ArrayList<>();
        ProductQueryBo productQueryBo = new ProductQueryBo();
        List<Product> products = productData.findList(productQueryBo);
        if(!products.isEmpty()) {
            for(Product product: products) {
                ChargerQueryBo queryBo = new ChargerQueryBo();
                queryBo.setProductKey(product.getProductKey());
                long count = chargerData.findCount(queryBo);
                chargerCategory.add(new DataItem(product.getName(), count));
            }
        }

        result.put("chargerCategory", chargerCategory);
        return result;
    }

    @ApiOperation(value = "充电桩状态统计", notes = "充电桩状态统计", httpMethod = "POST")
    @SaCheckPermission("business:analyze:list")
    @PostMapping("/chargerState")
    public Map<?,?> chargerState() {
        Map<String, Object> result = new HashMap<>();

        ChargerGunQueryBo chargerGunQueryBo = new ChargerGunQueryBo();
        chargerGunQueryBo.setState(ChargerGunStateEnum.Idle);
        List<ChargerGun> idleChargerGun = chargerGunData.findList(chargerGunQueryBo);
        result.put("chargerIdleTotal", idleChargerGun.size());

        chargerGunQueryBo = new ChargerGunQueryBo();
        chargerGunQueryBo.setState(ChargerGunStateEnum.Charging);
        List<ChargerGun> chargingChargerGun = chargerGunData.findList(chargerGunQueryBo);
        result.put("chargerChargingTotal", chargingChargerGun.size());

        chargerGunQueryBo = new ChargerGunQueryBo();
        chargerGunQueryBo.setState(ChargerGunStateEnum.Offline);
        List<ChargerGun> offlineChargerGun = chargerGunData.findList(chargerGunQueryBo);
        result.put("chargerOfflineTotal", offlineChargerGun.size());

        chargerGunQueryBo = new ChargerGunQueryBo();
        chargerGunQueryBo.setState(ChargerGunStateEnum.Fail);
        List<ChargerGun> failChargerGun = chargerGunData.findList(chargerGunQueryBo);
        result.put("chargerFailTotal", failChargerGun.size());

        chargerGunQueryBo = new ChargerGunQueryBo();
        chargerGunQueryBo.setState(ChargerGunStateEnum.Unknow);
        List<ChargerGun> unknownChargerGun = chargerGunData.findList(chargerGunQueryBo);
        result.put("chargerUnknownTotal", unknownChargerGun.size());

        return result;
    }


    @ApiOperation(value = "充电桩状态统计", notes = "充电桩状态统计", httpMethod = "POST")
    @SaCheckPermission("business:analyze:list")
    @PostMapping("/saleStatistics")
    public Map<?,?> saleStatistics() {
        Map<String, Object> result = new HashMap<>();

        String language = charerProperties.getSystem().getI18n().getLanguage();
        I18nLocaleProperties i18nLocale = Arrays.stream(charerProperties.getSystem().getI18n().getLocales()).filter(item->item.getLocale().equals(language)).findFirst().orElse(null);
        String dateFormat = Constants.DATE_FORMAT;
        if(i18nLocale != null) {
            dateFormat = i18nLocale.getDateFormat();
        }

        TimeZone timeZone = TimeZone.getTimeZone(Constants.TIMEZONE);
        if(i18nLocale != null) {
            timeZone = TimeZone.getTimeZone(i18nLocale.getTimezone());
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        List<DataItem> months = new ArrayList<>();
        for(int i = 1; i<=12; i++) {
            int year = LocalDate.now().getYear();
            Month month = Month.of(i);

            // 计算一个月的开始和结束时间
            LocalDateTime monthLocalStartTime = LocalDateTime.of(year, month, 1,0,0,0);
            ZonedDateTime zonedMonthStartTime = monthLocalStartTime.atZone(timeZone.toZoneId());
            Instant instantMonthStartTime = zonedMonthStartTime.toInstant();
            Date monthStartTime = Date.from(instantMonthStartTime);

            String startTimeStr = sdf.format(monthStartTime);

            LocalDateTime monthLocalEndTime = monthLocalStartTime.with(TemporalAdjusters.lastDayOfMonth());
            ZonedDateTime zonedMonthEndTime = monthLocalEndTime.atZone(timeZone.toZoneId());
            Instant instantMonthEndTime = zonedMonthEndTime.toInstant();
            Date monthEndTime = Date.from(instantMonthEndTime);
            String endTimeStr = sdf.format(monthEndTime);

            Map<String,Object> orderTodaySummary = ordersData.findSummaryByCreateTime(startTimeStr, endTimeStr);
            BigDecimal amount = (BigDecimal) orderTodaySummary.getOrDefault("order_amount", "0");

            months.add(new DataItem(String.format("%s月", i), amount.toString()));
        }

        result.put("months", months);

        return result;
    }
}
