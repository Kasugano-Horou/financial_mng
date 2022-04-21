package com.ruoyi.flowable.mapper.survey;


import com.ruoyi.flowable.domain.survey.SurveyAnswer;
import com.ruoyi.flowable.domain.survey.dto.SurveyAnswerDto;

import java.util.List;

/**
 * 问卷填写Mapper接口
 * 
 * @author 2y
 * @date 2021-03-21
 */
public interface SurveyAnswerMapper 
{
    /**
     * 查询问卷填写
     * 
     * @param answerId 问卷填写ID
     * @return 问卷填写
     */
    public SurveyAnswer selectSurveyAnswerById(Long answerId);

    /**
     * 查询问卷填写列表
     * 
     * @param surveyAnswer 问卷填写
     * @return 问卷填写集合
     */
    public List<SurveyAnswer> selectSurveyAnswerList(SurveyAnswer surveyAnswer);

    /**
     * 分组查询所有
     * @return
     */
    List<SurveyAnswerDto> selectAllList(SurveyAnswerDto surveyAnswer);

    /**
     * 新增问卷填写
     * 
     * @param surveyAnswer 问卷填写
     * @return 结果
     */
    public int insertSurveyAnswer(SurveyAnswer surveyAnswer);

    /**
     * 修改问卷填写
     * 
     * @param surveyAnswer 问卷填写
     * @return 结果
     */
    public int updateSurveyAnswer(SurveyAnswer surveyAnswer);

    /**
     * 删除问卷填写
     * 
     * @param answerId 问卷填写ID
     * @return 结果
     */
    public int deleteSurveyAnswerById(Long answerId);

    /**
     * 批量删除问卷填写
     * 
     * @param answerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSurveyAnswerByIds(Long[] answerIds);

    /**
     * 根据用户编号调查问卷查询填写详情
     * @return
     */
    List<SurveyAnswerDto> searchAllByUserId(Long userId);

}
