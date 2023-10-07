package com.dreams.yutianoj.judge.codesandbox.impl;

import com.dreams.yutianoj.judge.codesandbox.CodeSandBox;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 11:59
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
