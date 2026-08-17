package com.kingroad.pulsar.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.domain.vo.broker.BrokerMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 14:47
 * @Version: v1.0
 * @Description: Apache Pulsar 相关的工具
 */
@Slf4j
public class PulsarUtil {

    /**
     * 连接超时时间
     */
    public static final Integer CONN_TIMEOUT = 10;

    /**
     * 读取超时时间
     */
    public static final Integer READ_TIMEOUT = 15;

    /**
     * 请求超时时间
     */
    public static final Integer REQUEST_TIMEOUT = 20;

    public static PulsarAdmin getAdmin(PulsarCluster cluster) {

        if(StringUtils.isBlank(cluster.getAuthPlugin())) {
            return buildPulsarAdmin(cluster.getAdminApiUrl(), null,null);
        }

        try {
            Class clazz = Class.forName(cluster.getAuthPlugin());
            JSONObject tokenJson = JSONUtil.parseObj(cluster.getAuthParams());
            return buildPulsarAdmin(cluster.getAdminApiUrl(), clazz, JSONUtil.toJsonStr(tokenJson.get("token")));
        }catch (Exception e){
            log.error("构造PulsarAdmin失败，{}", e.getLocalizedMessage());
        }
        return null;
    }

    public static PulsarAdmin buildPulsarAdmin(String serviceUrl, Class authenticationClass, String token) {
        PulsarAdmin admin = null;

        try{
            PulsarAdminBuilder builder = PulsarAdmin.builder()
                    .serviceHttpUrl(serviceUrl)
                    .connectionTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .requestTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

            if(ObjectUtils.isNotEmpty(authenticationClass)){
                JSONObject tokenJson  = new JSONObject();
                tokenJson.put("token", token);

                builder.authentication(authenticationClass.getName(), JSONUtil.toJsonStr(tokenJson));
            }

            admin = builder.build();
        }catch (Exception e){
            log.error("获取PulsarAdmin失败：{}, {}", e.getLocalizedMessage(), e.getCause());
        }
        return admin;
    }

    public BrokerMetric getBrokerMetric(String metrics){
        if(StringUtils.isBlank(metrics)) return null;

        JSONArray array = JSONUtil.parseArray(metrics);


        return BrokerMetric.builder().build();
    }


}
