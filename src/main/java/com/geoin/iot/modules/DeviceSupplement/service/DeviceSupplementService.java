package com.geoin.iot.modules.DeviceSupplement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geoin.iot.common.utils.PageUtils;
import com.geoin.iot.modules.DeviceSupplement.entity.DeviceSupplementEntity;

import java.util.Map;

/**
 * 
 */
public interface DeviceSupplementService extends IService<DeviceSupplementEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

