package com.dreams.yutianoj.judge.codesandbox;

import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 12:48
 */
@Slf4j
public class CodesandboxProxy implements Codesandbox{

    private final Codesandbox codesandbox;

    public CodesandboxProxy(Codesandbox codesandbox) {
        this.codesandbox = codesandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codesandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱返回信息" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
