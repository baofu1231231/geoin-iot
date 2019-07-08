package com.geoin.iot.modules.OneNet.api.datastreams;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.geoin.iot.modules.OneNet.api.AbstractAPI;
import com.geoin.iot.modules.OneNet.exception.OnenetApiException;
import com.geoin.iot.modules.OneNet.http.HttpGetMethod;
import com.geoin.iot.modules.OneNet.request.RequestInfo.Method;
import com.geoin.iot.modules.OneNet.response.BasicResponse;
import com.geoin.iot.modules.OneNet.response.datastreams.DatastreamsResponse;
import com.geoin.iot.modules.OneNet.response.device.DeviceResponse;
import com.geoin.iot.modules.OneNet.utils.Config;

public class GetDatastreamApi extends AbstractAPI{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private HttpGetMethod HttpMethod;
	private String datastreamId;
	private String devId;
	/**
	 * 查询单个数据流
	 * @param devId
	 * @param datastreamId
	 * @param key
	 */
	public GetDatastreamApi( String devId,String datastreamId,String key) {
		this.datastreamId = datastreamId;
		this.devId = devId;
		this.key = key;
		this.method = Method.GET;
        this.HttpMethod=new HttpGetMethod(method);
        Map<String, Object> headmap = new HashMap<String, Object>();
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        this.url = Config.getString("test.url") + "/devices/"+ devId+"/datastreams/"+datastreamId;
        HttpMethod.setcompleteUrl(url,null);
		
	}

	public BasicResponse<DatastreamsResponse> executeApi() {

		ObjectMapper mapper = new ObjectMapper();
		BasicResponse response=null;
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			HttpResponse httpResponse=HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), DatastreamsResponse.class);
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("json error {}", e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}
		finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
		
	}
}
