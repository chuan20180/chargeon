package com.obast.charer.system;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2025-03-06-16:16
 * @ Version：1.0
 * @ Description：test
 */
public class Test {
    void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z");

        System.out.println(sdf.format(new Date()));
    }

}
