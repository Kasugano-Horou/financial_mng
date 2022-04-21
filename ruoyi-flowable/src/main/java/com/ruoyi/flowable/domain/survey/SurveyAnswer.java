package com.ruoyi.flowable.domain.survey;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 问卷填写对象 t_survey_answer
 * 
 * @author 2y
 * @date 2021-03-21
 */
public class SurveyAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问卷填写编号 */
    private Long answerId;

    /** 题目编号 */
    @Excel(name = "题目编号")
    private Long questionId;

    /** 问卷编号 */
    @Excel(name = "问卷编号")
    private Long paperId;

    /** 选项编号 */
    @Excel(name = "选项编号")
    private Long optionId;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 自行填写内容 */
    @Excel(name = "自行填写内容")
    private String content;

    public void setAnswerId(Long answerId) 
    {
        this.answerId = answerId;
    }

    public Long getAnswerId() 
    {
        return answerId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public void setQuestionId(Long questionId)
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setOptionId(Long optionId) 
    {
        this.optionId = optionId;
    }

    public Long getOptionId() 
    {
        return optionId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("answerId", getAnswerId())
            .append("paperId", getPaperId())
            .append("questionId", getQuestionId())
            .append("optionId", getOptionId())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("content", getContent())
            .toString();
    }
}
