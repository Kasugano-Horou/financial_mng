package com.ruoyi.flowable.service.survey.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

import com.ruoyi.flowable.domain.survey.SurveyAnswer;
import com.ruoyi.flowable.domain.survey.SurveyPaper;
import com.ruoyi.flowable.domain.survey.dto.SurveyAnswerDto;
import com.ruoyi.flowable.mapper.survey.SurveyAnswerMapper;
import com.ruoyi.flowable.mapper.survey.SurveyPaperMapper;
import com.ruoyi.flowable.service.survey.ISurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 问卷填写Service业务层处理
 * 
 * @author 2y
 * @date 2021-03-21
 */
@Service
public class SurveyAnswerServiceImpl implements ISurveyAnswerService
{
    @Autowired
    private SurveyAnswerMapper surveyAnswerMapper;

    @Autowired
    private SurveyPaperMapper surveyPaperMapper;

    /**
     * 查询问卷填写
     * 
     * @param answerId 问卷填写ID
     * @return 问卷填写
     */
    @Override
    public SurveyAnswer selectSurveyAnswerById(Long answerId)
    {
        return surveyAnswerMapper.selectSurveyAnswerById(answerId);
    }

    /**
     * 查询问卷填写列表
     * 
     * @param surveyAnswer 问卷填写
     * @return 问卷填写
     */
    @Override
    public List<SurveyAnswer> selectSurveyAnswerList(SurveyAnswer surveyAnswer)
    {
        return surveyAnswerMapper.selectSurveyAnswerList(surveyAnswer);
    }

    /**
     * 分组查询所有
     *
     * @return
     */
    @Override
    public List<SurveyAnswerDto> selectAllList(SurveyAnswerDto surveyAnswer) {
        return surveyAnswerMapper.selectAllList(surveyAnswer);
    }

    /**
     * 根据用户编号调查问卷查询填写详情
     *
     * @param userId
     * @return
     */
    @Override
    public SurveyPaper searchAllByUserId(Long paperId, Long userId) {
        SurveyPaper paper = surveyPaperMapper.selectSurveyPaperDetail(paperId);
        List<SurveyAnswerDto> answerList = surveyAnswerMapper.searchAllByUserId(userId);
        paper.getQuestions().forEach(surveyQuestion -> {
            if (surveyQuestion.getAnswer() == 0){
                answerList.forEach(answer ->{
                   if (answer.getQuestionId().equals(surveyQuestion.getQuestionId())){
//                     if (answer.getOptionId().equals(surveyOptions.getOptionId())){
                       surveyQuestion.setSelected(answer.getOptionId());
//                     }
                   }
                });
            }
            if (surveyQuestion.getAnswer() == 1){
                List<Long> asList = new ArrayList<>();
                answerList.forEach(answer ->{
                    if (answer.getQuestionId().equals(surveyQuestion.getQuestionId())){
                        asList.add(answer.getOptionId());
                        surveyQuestion.setCheckList(asList.toArray(new Long[0]));
                    }
                });
            }
            if (surveyQuestion.getAnswer() == 2){
                answerList.forEach(answer ->{
                    if (answer.getQuestionId().equals(surveyQuestion.getQuestionId())){
                        surveyQuestion.setRemark(answer.getContent());
                    }
                });
            }
        });
        return paper;
    }

    /**
     * 新增问卷填写
     * 
     * @param surveyAnswer 问卷填写
     * @return 结果
     */
    @Override
    public int insertSurveyAnswer(SurveyAnswer surveyAnswer)
    {
        surveyAnswer.setCreateTime(DateUtils.getNowDate());
        return surveyAnswerMapper.insertSurveyAnswer(surveyAnswer);
    }

    /**
     * 修改问卷填写
     * 
     * @param surveyAnswer 问卷填写
     * @return 结果
     */
    @Override
    public int updateSurveyAnswer(SurveyAnswer surveyAnswer)
    {
        return surveyAnswerMapper.updateSurveyAnswer(surveyAnswer);
    }

    /**
     * 批量删除问卷填写
     * 
     * @param answerIds 需要删除的问卷填写ID
     * @return 结果
     */
    @Override
    public int deleteSurveyAnswerByIds(Long[] answerIds)
    {
        return surveyAnswerMapper.deleteSurveyAnswerByIds(answerIds);
    }

    /**
     * 删除问卷填写信息
     * 
     * @param answerId 问卷填写ID
     * @return 结果
     */
    @Override
    public int deleteSurveyAnswerById(Long answerId)
    {
        return surveyAnswerMapper.deleteSurveyAnswerById(answerId);
    }

    /**
     * 批量新增
     *
     * @param surveyAnswer
     * @return
     */
    @Override
    public int addBatch(List<SurveyAnswer> surveyAnswer) {
        try {
            Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
            surveyAnswer.forEach(answer ->{
                answer.setCreateTime(DateUtils.getNowDate());
                answer.setUserId(userId);
                surveyAnswerMapper.insertSurveyAnswer(answer);
            });
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
