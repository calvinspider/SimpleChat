package org.yang.zhang.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 26 13:53
 */
@Component
@Slf4j
public class HttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers=httpRequest.getHeaders();
        headers.add("Content-Type",MediaType.APPLICATION_JSON_UTF8_VALUE);
        Long startTime=System.currentTimeMillis();

        if(log.isDebugEnabled()){
            log.debug("=============HTTP request begin==============");
            log.debug("URI: "+httpRequest.getURI());
            log.debug("Method "+httpRequest.getMethod());
            log.debug("Header "+httpRequest.getHeaders());
            log.debug("Body "+new String(bytes,"UTF-8"));
            log.debug("=============HTTP request end==============");
        }

        ClientHttpResponse response=clientHttpRequestExecution.execute(httpRequest,bytes);

        if(log.isDebugEnabled()){
            log.debug("=============HTTP response begin==============");
            log.debug("Status: "+response.getStatusText());
            log.debug("RawStatusCode: "+response.getRawStatusCode());
            log.debug("Header: "+response.getHeaders());
            log.debug("Time Cost: "+(System.currentTimeMillis()-startTime));
            log.debug("=============HTTP response end==============");
        }

        return response;
    }

}
