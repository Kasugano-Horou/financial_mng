package com.ruoyi.flowable.service.survey;


import com.ruoyi.flowable.domain.survey.SurveyOptions;

import java.util.List;

/**
 * 题目选项Service接口
 * 
 * @author 2y
 * @date 2021-03-21
 */
public interface ISurveyOptionsService 
{
    /**
     * 查询题目选项
     * 
     * @param optionId 题目选项ID
     * @return 题目选项
     */
    public SurveyOptions selectSurveyOptionsById(Long optionId);

    /**
     * 查询题目选项列表
     * 
     * @param surveyOptions 题目选项
     * @return 题目选项集合
     */
    public List<SurveyOptions> selectSurveyOptionsList(SurveyOptions surveyOptions);

    /**
     * 新增题目选项
     * 
     * @param surveyOptions 题目选项
     * @return 结果
     */
    public int insertSurveyOptions(SurveyOptions surveyOptions);

    /**
     * 修改题目选项
     * 
     * @param surveyOptions 题目选项
     * @return 结果
     */
    public int updateSurveyOptions(SurveyOptions surveyOptions);

    /**
     * 批量删除题目选项
     * 
     * @param optionIds 需要删除的题目选项ID
     * @return 结果
     */
    public int deleteSurveyOptionsByIds(Long[] optionIds);

    /**
     * 删除题目选项信息
     * 
     * @param optionId 题目选项ID
     * @return 结果
     */
    public int deleteSurveyOptionsById(Long optionId);
}
