package com.geoin.iot.modules.deviceInfo.controller;

import com.geoin.iot.common.utils.PageUtils;
import com.geoin.iot.common.utils.R;
import com.geoin.iot.modules.deviceInfo.entity.DeviceInfoEntity;
import com.geoin.iot.modules.deviceInfo.service.DeviceInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 */
@RestController
@RequestMapping("generator/deviceinfo")
public class DeviceInfoController {
    @Autowired
    private DeviceInfoService deviceInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:deviceinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:deviceinfo:info")
    public R info(@PathVariable("id") Integer id){
		DeviceInfoEntity deviceInfo = deviceInfoService.getById(id);

        return R.ok().put("deviceInfo", deviceInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:deviceinfo:save")
    public R save(@RequestBody DeviceInfoEntity deviceInfo){
		deviceInfoService.save(deviceInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:deviceinfo:update")
    public R update(@RequestBody DeviceInfoEntity deviceInfo){
		deviceInfoService.updateById(deviceInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:deviceinfo:delete")
    public R delete(@RequestBody Integer[] ids){
		deviceInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
