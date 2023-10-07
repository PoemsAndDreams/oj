package com.dreams.yutianoj.judge.codesandbox.impl;

import com.dreams.yutianoj.judge.codesandbox.CodeSandBox;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;
import com.dreams.yutianoj.model.enums.JudgeInfoMessageEnum;
import com.dreams.yutianoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 11:59
 * 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("示例代码沙箱执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getText());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMassage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
