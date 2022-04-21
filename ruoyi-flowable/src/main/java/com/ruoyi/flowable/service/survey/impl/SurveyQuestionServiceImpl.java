package com.ruoyi.flowable.service.survey.impl;

import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.flowable.domain.survey.SurveyQuestion;
import com.ruoyi.flowable.mapper.survey.SurveyOptionsMapper;
import com.ruoyi.flowable.mapper.survey.SurveyQuestionMapper;
import com.ruoyi.flowable.service.survey.ISurveyQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 问卷题目Service业务层处理
 * 
 * @author survey
 * @date 2021-03-21
 */
@Service
@Transactional
public class SurveyQuestionServiceImpl implements ISurveyQuestionService
{
    @Autowired
    private SurveyQuestionMapper surveyQuestionMapper;

    @Autowired
    private SurveyOptionsMapper surveyOptionsMapper;

    /**
     * 查询问卷题目
     * 
     * @param questionId 问卷题目ID
     * @return 问卷题目
     */
    @Override
    public SurveyQuestion selectSurveyQuestionById(Long questionId)
    {
        return surveyQuestionMapper.selectSurveyQuestionById(questionId);
    }

    /**
     * 查询问卷题目列表
     * 
     * @param surveyQuestion 问卷题目
     * @return 问卷题目
     */
    @Override
    public List<SurveyQuestion> selectSurveyQuestionList(SurveyQuestion surveyQuestion)
    {
        return surveyQuestionMapper.selectSurveyQuestionList(surveyQuestion);
    }

    /**
     * 新增问卷题目
     * 
     * @param surveyQuestion 问卷题目
     * @return 结果
     */
    @Override
    public int insertSurveyQuestion(SurveyQuestion surveyQuestion)
    {
        if (surveyQuestion.getOptions().isEmpty()) {
            surveyQuestion.setCreateTime(DateUtils.getNowDate());
            return surveyQuestionMapper.insertSurveyQuestion(surveyQuestion);
        }else {
            surveyQuestion.setCreateTime(DateUtils.getNowDate());
            surveyQuestionMapper.insertSurveyQuestion(surveyQuestion);
            Long id = surveyQuestion.getQuestionId();
            surveyQuestion.getOptions().forEach(k ->{
                k.setQuestionId(id);
                k.setCreateTime(DateUtils.getNowDate());
            });
            return surveyOptionsMapper.insertSurveyOptionList(surveyQuestion.getOptions());
        }
    }

    /**
     * 修改问卷题目
     * 
     * @param surveyQuestion 问卷题目
     * @return 结果
     */
    @Override
    public int updateSurveyQuestion(SurveyQuestion surveyQuestion)
    {
        if (surveyQuestion.getOptions().isEmpty()) {
            surveyQuestion.setUpdateTime(DateUtils.getNowDate());
            return surveyQuestionMapper.updateSurveyQuestion(surveyQuestion);
        }else {
            surveyQuestion.setUpdateTime(DateUtils.getNowDate());
            surveyQuestionMapper.updateSurveyQuestion(surveyQuestion);
            surveyOptionsMapper.deleteSurveyOptionsByQuestionId(surveyQuestion.getQuestionId());
            surveyQuestion.getOptions().forEach(k ->{
                k.setQuestionId(surveyQuestion.getQuestionId());
                k.setCreateTime(DateUtils.getNowDate());
            });
            return surveyOptionsMapper.insertSurveyOptionList(surveyQuestion.getOptions());
        }
    }

    /**
     * 批量删除问卷题目
     * 
     * @param questionIds 需要删除的问卷题目ID
     * @return 结果
     */
    @Override
    public int deleteSurveyQuestionByIds(Long[] questionIds)
    {
        return surveyQuestionMapper.deleteSurveyQuestionByIds(questionIds);
    }

    /**
     * 删除问卷题目信息
     * 
     * @param questionId 问卷题目ID
     * @return 结果
     */
    @Override
    public int deleteSurveyQuestionById(Long questionId)
    {
        surveyOptionsMapper.deleteSurveyOptionsByQuestionId(questionId);
        return surveyQuestionMapper.deleteSurveyQuestionById(questionId);
    }
}
