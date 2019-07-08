package com.geoin.iot.modules.OneNet.api.key;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geoin.iot.modules.OneNet.api.AbstractAPI;
import com.geoin.iot.modules.OneNet.exception.OnenetApiException;
import com.geoin.iot.modules.OneNet.http.HttpGetMethod;
import com.geoin.iot.modules.OneNet.request.RequestInfo.Method;
import com.geoin.iot.modules.OneNet.response.BasicResponse;
import com.geoin.iot.modules.OneNet.response.key.KeyList;
import com.geoin.iot.modules.OneNet.utils.Config;

public class FindKeyList extends AbstractAPI{
	private String apikey;
	private Integer page;
	private Integer perpage;
	private HttpGetMethod HttpMethod;
	private String devId;
	private String title;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @param title：根据名称查询，可选，字符串（左匹配
	 * @param apikey：需要查找的apikey,String
	 * @param page:指定页码，最大页数为10000（可选）,Integer
	 * @param perpage:指定每页输出设备个数，默认30，最多100（可选）,Integer
	 * @param devId：可选,只查看与该设备相关的非master-key,String
	 * @param key：masterkey(注：只能为master-key)
	 */
	public FindKeyList(String title,String apikey, Integer page, Integer perpage, String devId,String key) {
		this.apikey = apikey;
		this.page = page;
		this.perpage = perpage;
		this.devId = devId;
		this.method = Method.GET;
		this.key = key;
		this.title = title;
        Map<String, Object> headmap = new HashMap<String, Object>();
        Map<String, Object> urlmap = new HashMap<String, Object>();
        HttpMethod = new HttpGetMethod(method);
        this.url = Config.getString("test.url") + "/keys";
        // url参数
		if (apikey != null) {
			urlmap.put("key", apikey);
		}
		if (StringUtils.isNotBlank(title)) {
			urlmap.put("title", title);
		}
        if (page != null) {
            urlmap.put("page", page);
        }
        if (perpage != null) {
            urlmap.put("per_page", perpage);
        }
        if (devId != null) {
            urlmap.put("device_id", devId);
        }
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        HttpMethod.setcompleteUrl(url,urlmap);
	}


	public BasicResponse<KeyList> executeApi() {
		BasicResponse response = null;
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), KeyList.class);
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("error: {}" , e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
	}
}
