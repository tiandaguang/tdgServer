package com.boot.demo.utils;

import com.etc.utils.SpringContextUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Auther: edeis
 * @Date: 2018/10/31 14:56
 * @Description: 序列自增工具（每日自增）
 */
public class SequenceKit {
    private static final Logger logger = LoggerFactory.getLogger(SequenceKit.class);

    public static final String TYPE_INCR = "INCR:";

    public static final RedisTemplate REDISTEMPLATE = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);

    /**
     * 根据指定业务前缀生成自增 number
     *
     * @return
     */
    public static String getBusinessSequenceDoc(String key) {
        String date = new DateTime().toString("yyyyMMdd");
        logger.info("redisTemplate{}" + REDISTEMPLATE);
        Long no = REDISTEMPLATE.opsForValue().increment(TYPE_INCR + key, 1);
        if (no == null) {
            logger.info("redis 连接失败{}" + TYPE_INCR + key);
            String uuid = com.etc.utils.UUIDUtil.generate();
            return key + "-" + date + "-" + uuid;
        }
        return key + "-" + date + "-" + String.format("%06d", no);
    }

    public static String getTotalSequenceDoc(String payDueDate) {
        String key = new DateTime(payDueDate).toString("yyyyMMdd");
        Long no = REDISTEMPLATE.opsForValue().increment(key, 1);
        //Long no = Redis.use().incr(TYPE_Z + key);
        return "GBN" + "-" + key + "-" + String.format("%06d", no);
    }

    /**
     * 生成客户编号
     *
     * @param customerPhone
     * @return
     */
    public static String getCustomerNum(String customerPhone) {
        String key = new DateTime().toString("yyyyMMdd");
        Long no = REDISTEMPLATE.opsForValue().increment(key, 1);
        //Long no = Redis.use().incr(TYPE_Z + key);
        return "CUSTOMER" + "-" + key + "-" + customerPhone + "-" + String.format("%06d", no);
    }

    /**
     * 生成拉黑记录编号
     *
     * @param customerPhone
     * @return
     */
    public static String getBlackNum(String customerPhone) {
        String key = new DateTime().toString("yyyyMMdd");
        Long no = REDISTEMPLATE.opsForValue().increment(key, 1);
        //Long no = Redis.use().incr(TYPE_Z + key);
        return "BLACK" + "-" + key + "-" + customerPhone + "-" + String.format("%04d", no);
    }


}
