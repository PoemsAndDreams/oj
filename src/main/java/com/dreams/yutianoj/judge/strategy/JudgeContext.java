package com.dreams.yutianoj.judge.strategy;

import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;
import com.dreams.yutianoj.model.dto.question.JudgeCase;
import com.dreams.yutianoj.model.entity.Question;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 14:28
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
