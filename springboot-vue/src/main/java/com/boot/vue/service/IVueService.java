package com.boot.vue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.vue.entity.Vue;
import com.boot.vue.request.VueREQ;
import com.boot.vue.result.Result;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description: 持久化服务
 */
public interface IVueService extends IService<Vue> {

    Result search(long page, long size, VueREQ req);

}
