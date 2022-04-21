package com.ruoyi.flowable.domain.survey.dto;

import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * 问卷填写对象 t_survey_answer
 * 
 * @author 2y
 * @date 2021-03-21
 */
public class SurveyAnswerDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 问卷填写编号 */
    private Long answerId;

    /** 问卷标题 */
    @Excel(name = "问卷标题")
    private String paperTitle;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 题目名称 */
    private String questionTitle;

    /** 选项内容 */
    private String optionContent;

    /** 自行填写内容 */
    private String content;

    /** 题目编号 */
    private Long questionId;

    /** 问卷编号 */
    private Long paperId;

    /** 选项编号 */
    private Long optionId;

    /** 选项编号 */
    private Long userId;

    /** 单选：0 多选：1 自行填入：2 */
    private Integer answer;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SurveyAnswerDto{" +
                "answerId=" + answerId +
                ", paperTitle='" + paperTitle + '\'' +
                ", nickName='" + nickName + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", optionContent='" + optionContent + '\'' +
                ", content='" + content + '\'' +
                ", questionId=" + questionId +
                ", paperId=" + paperId +
                ", optionId=" + optionId +
                ", userId=" + userId +
                ", answer=" + answer +
                '}';
    }
}
