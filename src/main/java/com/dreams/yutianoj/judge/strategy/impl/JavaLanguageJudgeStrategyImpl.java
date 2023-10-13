package com.dreams.yutianoj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.dreams.yutianoj.judge.strategy.JudgeContext;
import com.dreams.yutianoj.judge.strategy.JudgeStrategy;
import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;
import com.dreams.yutianoj.model.dto.question.JudgeCase;
import com.dreams.yutianoj.model.dto.question.JudgeConfig;
import com.dreams.yutianoj.model.entity.Question;
import com.dreams.yutianoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 14:32
 * java判题策略
 */
public class JavaLanguageJudgeStrategyImpl implements JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {


        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();



        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;


        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
//        Long memory = judgeInfo.getMemory();
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);


        //判断沙箱执行结果答案是否符合预期

        if (outputList.size() != inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMassage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))){
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMassage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }

        //判断题目限制是否符合预期
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long stackLimit = judgeConfig.getStackLimit();


        //java特殊策略
        //......


        if (time > timeLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMassage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (memory > memoryLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMassage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }


        judgeInfoResponse.setMassage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;

    }
}
