package com.dreams.yutianoj.model.dto.question;

import lombok.Data;

/**
 * @author PoemsAndDreams
 * @date 2023-10-02 21:24
 * 题目用例
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

}
