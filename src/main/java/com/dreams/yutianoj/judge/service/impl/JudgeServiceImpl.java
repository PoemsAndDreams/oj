package com.dreams.yutianoj.judge.service.impl;

import cn.hutool.json.JSONUtil;
import com.dreams.yutianoj.common.ErrorCode;
import com.dreams.yutianoj.exception.BusinessException;
import com.dreams.yutianoj.judge.codesandbox.CodeSandBox;
import com.dreams.yutianoj.judge.codesandbox.CodeSandBoxFactory;
import com.dreams.yutianoj.judge.codesandbox.CodeSandBoxProxy;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.dreams.yutianoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.dreams.yutianoj.judge.service.JudgeService;
import com.dreams.yutianoj.judge.strategy.JudgeContext;
import com.dreams.yutianoj.judge.strategy.manager.JudgeManager;
import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;
import com.dreams.yutianoj.model.dto.question.JudgeCase;
import com.dreams.yutianoj.model.entity.Question;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import com.dreams.yutianoj.model.enums.QuestionSubmitStatusEnum;
import com.dreams.yutianoj.service.QuestionService;
import com.dreams.yutianoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 13:18
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //获取对应题目
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        //如果不为等待状态，异常
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        //更新为判题中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }


        //调用沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        //code,language,和输入用例
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();

        // 执行判题
        JudgeContext judgeContext = new JudgeContext();
        //设置上下文
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);


        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        //更新为判题中
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        String jsonStr = JSONUtil.toJsonStr(judgeInfo);
        questionSubmitUpdate.setJudgeInfo(jsonStr);
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        QuestionSubmit result = questionSubmitService.getById(questionId);
        return result;
    }
}
