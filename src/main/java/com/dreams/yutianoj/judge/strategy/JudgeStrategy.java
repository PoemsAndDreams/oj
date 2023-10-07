package com.dreams.yutianoj.judge.strategy;

import com.dreams.yutianoj.judge.codesandbox.model.JudgeInfo;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 14:22
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
