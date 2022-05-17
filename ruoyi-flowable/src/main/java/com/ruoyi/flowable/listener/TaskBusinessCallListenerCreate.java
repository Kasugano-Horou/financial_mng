package com.ruoyi.flowable.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.el.FixedValue;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Scope
@Component(value = "taskBusinessCallListenerCreate")
public class TaskBusinessCallListenerCreate implements TaskListener {// extends BusinessCallListener

    private static final Logger log = LoggerFactory.getLogger(TaskBusinessCallListenerCreate.class);

    /**
     * rest接口
     */
    private FixedValue restUrl;
    /**
     * 参数 多个的话用分号隔开 实例 userCode:00004737;status:1
     */
    private FixedValue params;

    private FixedValue formKeyName;

    @Autowired
    private RuntimeService runtimeService;

    private RestTemplate restTemplate;

    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        String restUrlStr = null, paramsStr = null, formKeyNameStr = null;
        if (restUrl != null) {
            restUrlStr = restUrl.getExpressionText();
        }
        if (params != null) {
            paramsStr = params.getExpressionText();
        }
        if (formKeyName != null) {
            formKeyNameStr = formKeyName.getExpressionText();
        }
        System.out.println("restUrlStr::"+restUrlStr+",,,paramsStr::"+paramsStr+",,,formKeyName"+formKeyNameStr);
        //执行回调
//        //TODO 临时处理
//        restUrlStr = "http://localhost:8080/financial/contract/changeStatus";
//        paramsStr = "status:3;contractId:1";

        this.callBack(processInstanceId, restUrlStr, paramsStr, formKeyNameStr);
    }

    public void callBack(String processInstanceId, String restUrl, String params, String formKeyName) {
        String paramsJson = null;
        try {
            //获取变量
            Map<String, Object> paramMap = new HashMap<>();
            Map<String, Object> processParamMap = runtimeService.getVariables(processInstanceId);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//            paramMap.put("businessKey", processInstance.getBusinessKey());
            //paramMap.put("", processInstance.getBusinessKey());
            System.out.println("processInstance:" + processInstance);
            System.out.println("processParamMap:" + processParamMap);
//            System.out.println("processParamMap.get(contractId):" + processParamMap.get("contractId"));
            if(processParamMap.containsKey(formKeyName)){
                String formId = processParamMap.get(formKeyName).toString();
                if(formId != null) {
                    paramMap.put(formKeyName,formId);
                }
                System.out.println("processParamMap.get(fromId):" + formId);
            }
            if(params != null) {
                this.setParams(params, paramMap);
            }

            System.out.println("5开始调用业务系统接口" + restUrl + ",业务参数:" + paramsJson);
            paramsJson = JSON.toJSONString(paramMap);
            System.out.println("6开始调用业务系统接口" + restUrl + ",业务参数:" + paramsJson);

            System.out.println("开始调用业务系统接口9");
            //调用业务方法
            log.info("开始调用业务系统接口" + restUrl + ",业务参数:" + paramsJson);

            //设置请求头和请求参数data
            HttpHeaders headers =new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            JSONObject body = new JSONObject(paramMap);
            HttpEntity entity =new HttpEntity<>(body, headers);

            //设置超时时间，否则会报超时错误
            HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory();
            System.out.println("开始调用业务系统接口7");
            requestFactory.setReadTimeout(30000);
            requestFactory.setConnectionRequestTimeout(30000);
            requestFactory.setConnectTimeout(30000);
            System.out.println("开始调用业务系统接口8");
            restTemplate=new RestTemplate(requestFactory);

            //ResponseEntity response =restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            //调用业务系统接口
            System.out.println("10开始调用业务系统接口" + restUrl + ",业务参数:" + paramsJson + ",HttpEntity" + entity.toString());
            restTemplate.exchange(restUrl, HttpMethod.PUT, entity, String.class);
            //restTemplate.put(restUrl, paramsJson, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setParams(String params, Map<String, Object> paramMap) {

        String[] param =  params.split(";");
        for(String key_value:param){
            String[] pair = key_value.split(":");
            if(pair.length==2){
                paramMap.put(pair[0], pair[1]);
            }
        }
    }
}


