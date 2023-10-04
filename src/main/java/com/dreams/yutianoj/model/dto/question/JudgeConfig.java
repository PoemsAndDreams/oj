package com.dreams.yutianoj.model.dto.question;

import lombok.Data;

/**
 * @author PoemsAndDreams
 * @date 2023-10-02 21:24
 * 题目配置
 */
@Data
public class JudgeConfig {


    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(KB)
     */
    private Long memoryLimit;


    /**
     * 堆栈限制(KB)
     */
    private Long stackLimit;

}
