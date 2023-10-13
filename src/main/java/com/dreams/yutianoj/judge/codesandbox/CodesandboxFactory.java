package com.dreams.yutianoj.judge.codesandbox;

import com.dreams.yutianoj.judge.codesandbox.impl.ExampleCodesandbox;
import com.dreams.yutianoj.judge.codesandbox.impl.RemoteCodesandbox;
import com.dreams.yutianoj.judge.codesandbox.impl.ThirdPartyCodesandbox;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 12:20
 * 代码沙箱工产
 */
public class CodesandboxFactory {

    /**
     * 接收代码沙箱插件类型，创建代码沙箱实例
     * @param type
     * @return
     */
    public static Codesandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodesandbox();
            case "remote":
                return new RemoteCodesandbox();
            case "thirdParty":
                return new ThirdPartyCodesandbox();
            default:
                return new ExampleCodesandbox();
        }
    }
}
