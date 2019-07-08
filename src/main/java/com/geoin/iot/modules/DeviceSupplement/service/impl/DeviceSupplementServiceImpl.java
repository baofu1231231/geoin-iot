package com.geoin.iot.modules.DeviceSupplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geoin.iot.common.utils.PageUtils;
import com.geoin.iot.common.utils.Query;
import com.geoin.iot.modules.DeviceSupplement.dao.DeviceSupplementDao;
import com.geoin.iot.modules.DeviceSupplement.entity.DeviceSupplementEntity;
import com.geoin.iot.modules.DeviceSupplement.service.DeviceSupplementService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("deviceSupplementService")
public class DeviceSupplementServiceImpl extends ServiceImpl<DeviceSupplementDao, DeviceSupplementEntity> implements DeviceSupplementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeviceSupplementEntity> page = this.page(
                new Query<DeviceSupplementEntity>().getPage(params),
                new QueryWrapper<DeviceSupplementEntity>()
        );

        return new PageUtils(page);
    }

}