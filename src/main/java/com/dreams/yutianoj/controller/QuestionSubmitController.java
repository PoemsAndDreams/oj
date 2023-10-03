package com.dreams.yutianoj.controller;

import com.dreams.yutianoj.common.BaseResponse;
import com.dreams.yutianoj.common.ErrorCode;
import com.dreams.yutianoj.common.ResultUtils;
import com.dreams.yutianoj.exception.BusinessException;

import com.dreams.yutianoj.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.dreams.yutianoj.model.entity.User;
import com.dreams.yutianoj.service.QuestionSubmitService;
import com.dreams.yutianoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交题目的id
     */
    @PostMapping("/")
    public BaseResponse<Long> doSubmitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);

        return ResultUtils.success(questionSubmitId);
    }

}
