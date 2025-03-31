package com.obast.charer.data.config.id;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Component;

import java.util.Random;
/**
 * ID生成器
 */
@Component
public class IdGenerator {


    static short len = 20;
    /*
     * 生成订单号
     */
    public static String generateOrderTranId() {
      IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
      YitIdHelper.setIdGenerator(options);
      String tranId = String.valueOf(YitIdHelper.nextId());
      int offset = len - tranId.length() - 1;
      if(offset <= 0) {
          return tranId;
      }
      int min = (int) Math.pow(10, offset);
      int max = (int) Math.pow(10, offset+1)-1;
      Random random = new Random();
      tranId += String.valueOf(random.nextInt(max - min + 1) + min);
      return tranId;
    }

    public static String generateTopupTranId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        return String.valueOf(YitIdHelper.nextId());
    }


    public static String generateRefundTranId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        return String.valueOf(YitIdHelper.nextId());
    }

    public static String generateRefundBalanceTranId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        return String.valueOf(YitIdHelper.nextId());
    }

    public static String generateCouponCodeTranId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        return String.valueOf(YitIdHelper.nextId());
    }

    public static String generateParkTranId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        return String.valueOf(YitIdHelper.nextId());
    }

    public static String generateAppId() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        String tranId = String.valueOf(YitIdHelper.nextId());
        int offset = len - tranId.length() - 1;
        if(offset <= 0) {
            return tranId;
        }
        int min = (int) Math.pow(10, offset);
        int max = (int) Math.pow(10, offset+1)-1;
        Random random = new Random();
        tranId += String.valueOf(random.nextInt(max - min + 1) + min);
        return tranId;
    }
}