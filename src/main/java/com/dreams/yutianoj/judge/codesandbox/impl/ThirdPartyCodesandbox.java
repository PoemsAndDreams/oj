package com.dreams.yutianoj.judge.codesandbox.impl;

import com.dreams.yutianoj.judge.codesandbox.Codesandbox;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 11:59
 * 第三方代码沙箱（调用第三方代码沙箱）
 */
public class ThirdPartyCodesandbox implements Codesandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
