package com.geoin.iot.modules.deviceInfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 */
@Data
@TableName("tb_device_info")
public class DeviceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String sn;
	/**
	 * 0:无类型，1：安全帽，2：电池
	 */
	private Integer type;
	/**
	 * IMEI
	 */
	private String imei;
	/**
	 * 物联网卡号
	 */
	private String iotcard;
	/**
	 * 0 激活 1 激活
	 */
	private Integer activited;
	/**
	 * 激活时间
	 */
	private Date actidate;
	/**
	 * 在线状态  0 离线 1 在线
	 */
	private Integer status;
	/**
	 * 录入时阿
	 */
	private Date regdate;
	/**
	 * 纬度
	 */
	private BigDecimal lat;
	/**
	 * 经度
	 */
	private BigDecimal lon;
	/**
	 * 高度
	 */
	private BigDecimal height;
	/**
	 * 定位时间
	 */
	private Date postime;
	/**
	 * 位置
	 */
	private String location;

}
