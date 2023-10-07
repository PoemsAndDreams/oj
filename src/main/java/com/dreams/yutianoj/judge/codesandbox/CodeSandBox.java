package com.dreams.yutianoj.judge.codesandbox;

import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author PoemsAndDreams
 * @date 2023-10-06 23:08
 * 代码沙箱接口定义
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
