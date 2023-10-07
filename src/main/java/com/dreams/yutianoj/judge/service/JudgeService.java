package com.dreams.yutianoj.judge.service;

import com.dreams.yutianoj.model.entity.QuestionSubmit;
import com.dreams.yutianoj.model.vo.QuestionSubmitVO;

/**
 * @author PoemsAndDreams
 * @date 2023-10-07 13:11
 * 判题服务
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
