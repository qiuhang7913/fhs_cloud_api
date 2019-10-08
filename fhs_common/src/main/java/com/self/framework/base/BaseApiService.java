package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.framework.cenum.HttpResultEnum;
import com.self.framework.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @description 分布式子系统内部调用基类
 * @author qiuhang
 * @date 2019/10/8/008
 */
public class BaseApiService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @description 接口请求执行
     * @author qiuhang
     * @date 2019/10/8/008
     */
    protected String excute(String requestUrl, Map<String,Object> from){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<Map> strEntity = new HttpEntity<>(from, headers);
        try {
            ResponseEntity<String> forEntity = restTemplate.postForEntity(requestUrl, strEntity, String.class);
            if (HttpResultEnum.SUCCESS.getCode().equals(forEntity.getStatusCode().value())){
                return forEntity.getBody();
            }
        }catch (Exception e){
            logger.error("接口请求异常", e);
            throw new BusinessException(HttpResultEnum.SYSTEM_RRROR);
        }
        return null;
    }
    
    /**
     * @description 是否调用成功
     * @author qiuhang
     * @date 2019/10/8/008
     */
    protected boolean isOk(String rvString){
        JSONObject jsonObject = JSON.parseObject(rvString);
        Integer code = jsonObject.getInteger("code");
        return HttpResultEnum.SUCCESS.getCode().equals(code);
    }
    
    /**
     * @description 结果转换jsonObject
     * @author qiuhang
     * @date 2019/10/8/008
     */
    protected JSONObject result(String rvString){
        return JSON.parseObject(rvString);
    }
}
