package com.dreams.yutianoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.dreams.yutianoj.common.ErrorCode;
import com.dreams.yutianoj.exception.BusinessException;
import com.dreams.yutianoj.judge.codesandbox.Codesandbox;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 11:59
 * 远程代码沙箱
 */
public class RemoteCodesandbox implements Codesandbox {
    //定义鉴权请求头和密匙
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remotesandbox error ! message = " + responseStr);
        }

        ExecuteCodeResponse bean = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
        return bean;
    }
}
