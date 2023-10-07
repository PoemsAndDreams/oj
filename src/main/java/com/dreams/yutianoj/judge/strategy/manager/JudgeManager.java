package com.dreams.yutianoj.judge.strategy.manager;

import com.dreams.yutianoj.judge.strategy.JudgeContext;
import com.dreams.yutianoj.judge.strategy.JudgeStrategy;
import com.dreams.yutianoj.judge.strategy.impl.DefaultJudgeStrategyImpl;
import com.dreams.yutianoj.judge.strategy.impl.JavaLanguageJudgeStrategyImpl;
import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 15:01
 * 判题管理简化调用
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if ("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
