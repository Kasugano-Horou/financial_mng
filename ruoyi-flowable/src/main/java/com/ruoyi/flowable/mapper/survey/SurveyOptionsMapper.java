package com.ruoyi.flowable.mapper.survey;


import com.ruoyi.flowable.domain.survey.SurveyOptions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目选项Mapper接口
 * 
 * @author 2y
 * @date 2021-03-21
 */
public interface SurveyOptionsMapper 
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
     * 删除题目选项
     * 
     * @param optionId 题目选项ID
     * @return 结果
     */
    public int deleteSurveyOptionsById(Long optionId);

    /**
     * 批量删除题目选项
     * 
     * @param optionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSurveyOptionsByIds(Long[] optionIds);

    /**
     * 批量新增问题选项
     * @param list
     */
    int insertSurveyOptionList(@Param("list") List<SurveyOptions> list);

    /**
     * 根据问题编号删除选项
     * @param questionId
     */
    void deleteSurveyOptionsByQuestionId(Long questionId);
}
