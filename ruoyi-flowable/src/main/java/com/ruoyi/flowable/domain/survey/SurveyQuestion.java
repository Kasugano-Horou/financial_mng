package com.ruoyi.flowable.domain.survey;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * 问卷题目对象 t_survey_question
 * 
 * @author survey
 * @date 2021-03-21
 */
public class SurveyQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题目id */
    private Long questionId;

    /** 题目名称 */
    @Excel(name = "题目名称")
    private String questionTitle;

    /** 单选：0 多选：1 自行填入：2 */
    @Excel(name = "单选：0 多选：1 自行填入：2")
    private Integer answer;

    /** 问卷编号 */
    private Long paperId;

    @Excel(name = "问卷标题")
    private String paperTitle;

    /** 单选项 */
    private Long selected;

    /** 多选项 */
    private Long[] checkList;

    private List<SurveyOptions> options;

    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setQuestionTitle(String questionTitle) 
    {
        this.questionTitle = questionTitle;
    }

    public String getQuestionTitle() 
    {
        return questionTitle;
    }
    public void setAnswer(Integer answer) 
    {
        this.answer = answer;
    }

    public Integer getAnswer() 
    {
        return answer;
    }
    public void setPaperId(Long paperId) 
    {
        this.paperId = paperId;
    }

    public Long getPaperId() 
    {
        return paperId;
    }

    public List<SurveyOptions> getOptions() {
        return options;
    }

    public void setOptions(List<SurveyOptions> options) {
        this.options = options;
    }


    public Long getSelected() {
        return selected;
    }

    public void setSelected(Long selected) {
        this.selected = selected;
    }

    public Long[] getCheckList() {
        return checkList;
    }

    public void setCheckList(Long[] checkList) {
        this.checkList = checkList;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    @Override
    public String toString() {
        return "SurveyQuestion{" +
                "questionId=" + questionId +
                ", questionTitle='" + questionTitle + '\'' +
                ", answer=" + answer +
                ", paperId=" + paperId +
                ", paperTitle=" + paperTitle+
                ", selected=" + selected +
                ", checkList=" + Arrays.toString(checkList) +
                ", options=" + options +
                '}';
    }
}
