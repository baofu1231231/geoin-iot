package com.geoin.iot.modules.DeviceSupplement.controller;

import com.geoin.iot.common.annotation.SysLog;
import com.geoin.iot.common.utils.PageUtils;
import com.geoin.iot.common.utils.R;
import com.geoin.iot.modules.DeviceSupplement.entity.DeviceSupplementEntity;
import com.geoin.iot.modules.DeviceSupplement.service.DeviceSupplementService;
import com.geoin.iot.modules.OneNet.api.datapoints.GetDatapointsListApi;
import com.geoin.iot.modules.OneNet.response.BasicResponse;
import com.geoin.iot.modules.OneNet.response.datapoints.DatapointsList;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


/**
 * 
 */
@Api(value = "活动相关", description = "活动相关接口")
@RestController
@RequestMapping("generator/devicesupplement")
public class DeviceSupplementController {
    @Autowired
    private DeviceSupplementService deviceSupplementService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("generator:devicesupplement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceSupplementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("generator:devicesupplement:info")
    public R info(@PathVariable("id") Integer id){
		DeviceSupplementEntity deviceSupplement = deviceSupplementService.getById(id);

        return R.ok().put("deviceSupplement", deviceSupplement);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("generator:devicesupplement:save")
    public R save(@RequestBody DeviceSupplementEntity deviceSupplement){
		deviceSupplementService.save(deviceSupplement);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("generator:devicesupplement:update")
    public R update(@RequestBody DeviceSupplementEntity deviceSupplement){
		deviceSupplementService.updateById(deviceSupplement);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("generator:devicesupplement:delete")
    public R delete(@RequestBody Integer[] ids){
		deviceSupplementService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @ApiOperation("从OneNet上面获取数据保存设备基本信息")
    @SysLog("从OneNet上面获取数据保存设备基本信息")
    @PostMapping("/saveData")
    public R saveData(){
        try {
            List<Map<String,Object>> list = testGetDatapointsApi();
            list.stream().forEach((o)->{
                DeviceSupplementEntity deviceSupplementEntity = new DeviceSupplementEntity();
                BigDecimal lon = new BigDecimal(o.get("lon").toString());
                BigDecimal lat = new BigDecimal(o.get("lat").toString());
                BigDecimal alt = new BigDecimal(o.get("alt").toString());
                Double speed = Double.valueOf(o.get("speed").toString());
                Integer satview = Integer.valueOf(o.get("sv").toString());
                Float snrmax = Float.valueOf(o.get("snrmax").toString());
                Float voltage = Float.valueOf(o.get("voltage").toString());
                Timestamp postime = new Timestamp(System.currentTimeMillis());
                String[] StrArr = o.get("timestamp").toString().split("-");
                String year ="20"+ StrArr[0];
                String tsStr = year+"-"+StrArr[1]+"-"+StrArr[2] +" "+StrArr[3]+":"+StrArr[4]+":"+StrArr[5];
                try {
                    postime = Timestamp.valueOf(tsStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                deviceSupplementEntity.setLon(lon);
                deviceSupplementEntity.setLat(lat);
                deviceSupplementEntity.setHeight(alt);
                deviceSupplementEntity.setSpeed(speed);
                deviceSupplementEntity.setSatview(satview);
                deviceSupplementEntity.setSnrmax(snrmax);
                deviceSupplementEntity.setVoltage(voltage);
                deviceSupplementEntity.setPostime(postime);

                deviceSupplementService.save(deviceSupplementEntity);
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    public List<Map<String,Object>> testGetDatapointsApi() throws JSONException {
        String datastreamids = "safetyhelmet";
        String devid = "533463793";
        String key = "L2eQ4C5PAVUuSbE2kfQMbR=wPB8=";
        Integer limit = 200;
        GetDatapointsListApi api = new GetDatapointsListApi(datastreamids, null, null, devid, null, limit, null, null,
                null, null, null, key);
        BasicResponse<DatapointsList> response = api.executeApi();
        String strJson = response.getJson();
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(strJson, map.getClass());
        List<Map<String,Object>> lists = new ArrayList<>();
        JSONObject jsonData = convertKeyValueToJSON((LinkedTreeMap<String, Object>) map.get("data"));
        List list = (List) jsonData.get("datastreams");
        List<LinkedTreeMap<String,Object>> listMap = (List<LinkedTreeMap<String, Object>>) ((LinkedTreeMap) list.get(0)).get("datapoints");
        listMap.stream().forEach((o)->{
            String str = o.get("value").toString();
            Map<String,Object> map0 = getValues(str);
            lists.add(map0);
        });
        return lists;
    }

    private Map<String, Object> getValues(String str) {
        Map<String,Object> map=new HashMap<>();
        String[] char0 = str.split(",");
        map.put("lon",char0[0].split(":")[1]);
        map.put("lat",char0[1].split(":")[1]);
        map.put("alt",char0[2].split(":")[1]);
        map.put("speed",char0[3].split(":")[1]);
        map.put("sv",char0[4].split(":")[1]);
        map.put("snrmax",char0[5].split(":")[1]);
        map.put("gps",char0[6].split(":")[1]);
        map.put("bds",char0[7].split(":")[1]);
        map.put("timestamp",char0[8].split(":")[1]);
        map.put("voltage",char0[9].split(":")[1]);
        map.put("takeoff",char0[10].split(":")[1]);
        map.put("sos",char0[11].split(":")[1]);
        map.put("electrical",char0[12].split(":")[1]);
        map.put("lowpower",char0[13].split(":")[1]);
        return map;
    }

    public static JSONObject convertKeyValueToJSON(LinkedTreeMap<String, Object> ltm) {
        JSONObject jo=new JSONObject();
        Object[] objs = ltm.entrySet().toArray();
        for (int l=0;l<objs.length;l++)
        {
            Map.Entry o= (Map.Entry) objs[l];
            try {
                if (o.getValue() instanceof LinkedTreeMap)
                    jo.put(o.getKey().toString(),convertKeyValueToJSON((LinkedTreeMap<String, Object>) o.getValue()));
                else
                    jo.put(o.getKey().toString(),o.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jo;
    }

}
