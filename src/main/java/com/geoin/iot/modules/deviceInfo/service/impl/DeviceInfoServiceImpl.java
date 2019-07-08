package com.geoin.iot.modules.deviceInfo.service.impl;

import com.geoin.iot.modules.deviceInfo.service.DeviceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geoin.iot.common.utils.PageUtils;
import com.geoin.iot.common.utils.Query;
import com.geoin.iot.modules.deviceInfo.dao.DeviceInfoDao;
import com.geoin.iot.modules.deviceInfo.entity.DeviceInfoEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoDao, DeviceInfoEntity> implements DeviceInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeviceInfoEntity> page = this.page(
                new Query<DeviceInfoEntity>().getPage(params),
                new QueryWrapper<DeviceInfoEntity>()
        );

        return new PageUtils(page);
    }

}