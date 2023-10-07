package com.dreams.yutianoj.judge.codesandbox;

import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 12:48
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{

    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱返回信息" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
