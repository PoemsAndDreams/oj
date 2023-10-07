package com.dreams.yutianoj.judge.codesandbox.model;

import lombok.Data;

/**
 * @author PoemsAndDreams
 * @date 2023-10-02 21:40
 * 判题对象
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String massage;


    /**
     * 消耗内存
     */
    private Long memory;


    /**
     * 消耗时间（KB）
     */
    private Long time;


}
