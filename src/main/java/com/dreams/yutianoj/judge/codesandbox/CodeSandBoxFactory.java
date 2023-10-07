package com.dreams.yutianoj.judge.codesandbox;

import com.dreams.yutianoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.dreams.yutianoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.dreams.yutianoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 12:20
 * 代码沙箱工产
 */
public class CodeSandBoxFactory {

    /**
     * 接收代码沙箱插件类型，创建代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
