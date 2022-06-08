package com.boot.vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.vue.base.Page;
import com.boot.vue.entity.Vue;
import com.boot.vue.mapper.VueMapper;
import com.boot.vue.request.VueREQ;
import com.boot.vue.result.Result;
import com.boot.vue.service.IVueService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description:
 */
@Service
public class VueServiceImpl extends ServiceImpl<VueMapper, Vue> implements IVueService {

    @Override
    public Result search(long page, long size, VueREQ req) {

        //封装查询条件
        QueryWrapper<Vue> query = new QueryWrapper<>();
        if(null != req){
            if(StringUtils.isNotBlank(req.getName())){
                query.like("name", req.getName());
            }

            if(StringUtils.isNotBlank(req.getCardNum())){
                query.like("card_num", req.getCardNum());
            }

            if(StringUtils.isNotBlank(req.getPayType())){
                query.eq("pay_type", req.getPayType());
            }

            if(null != req.getBirthday()){
                query.eq("birthday", req.getBirthday());
            }
        }

        //封装分页对象
        IPage<Vue> p = new Page<>(page, size);
        IPage<Vue> data = baseMapper.selectPage(p, query);
        return Result.ok(data);
    }
}
