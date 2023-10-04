package com.dreams.yutianoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreams.yutianoj.annotation.AuthCheck;
import com.dreams.yutianoj.common.BaseResponse;
import com.dreams.yutianoj.common.ErrorCode;
import com.dreams.yutianoj.common.ResultUtils;
import com.dreams.yutianoj.constant.UserConstant;
import com.dreams.yutianoj.exception.BusinessException;

import com.dreams.yutianoj.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.dreams.yutianoj.model.dto.QuestionSubmit.QuestionSubmitQueryRequest;
import com.dreams.yutianoj.model.dto.question.QuestionQueryRequest;
import com.dreams.yutianoj.model.entity.Question;
import com.dreams.yutianoj.model.entity.QuestionSubmit;
import com.dreams.yutianoj.model.entity.User;
import com.dreams.yutianoj.model.vo.QuestionSubmitVO;
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
        // 登录才能
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);

        return ResultUtils.success(questionSubmitId);
    }



    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //全部数据
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //返回脱敏信息
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,loginUser));
    }

}
