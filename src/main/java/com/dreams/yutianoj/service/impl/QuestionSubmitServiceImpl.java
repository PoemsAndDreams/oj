package com.dreams.yutianoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dreams.yutianoj.common.ErrorCode;
import com.dreams.yutianoj.exception.BusinessException;
import com.dreams.yutianoj.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.dreams.yutianoj.model.entity.Question;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import com.dreams.yutianoj.model.entity.User;
import com.dreams.yutianoj.model.enums.QuestionSubmitLanguageEnum;
import com.dreams.yutianoj.model.enums.QuestionSubmitStatusEnum;
import com.dreams.yutianoj.service.QuestionService;
import com.dreams.yutianoj.service.QuestionSubmitService;
import com.dreams.yutianoj.service.QuestionSubmitService;
import com.dreams.yutianoj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author xiayutian
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-10-02 10:50:28
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR,"编译语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        // 锁必须要包裹住事务方法
        QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);


        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        // 未提交题目
        boolean save = this.save(questionSubmit);

        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据输入失败");
        }
        return questionSubmit.getId();

    }



}




