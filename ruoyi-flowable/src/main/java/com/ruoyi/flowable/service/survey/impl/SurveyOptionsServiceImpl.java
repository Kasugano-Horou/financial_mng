package com.ruoyi.flowable.service.survey.impl;

import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.flowable.domain.survey.SurveyOptions;
import com.ruoyi.flowable.mapper.survey.SurveyOptionsMapper;
import com.ruoyi.flowable.service.survey.ISurveyOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题目选项Service业务层处理
 * 
 * @author 2y
 * @date 2021-03-21
 */
@Service
public class SurveyOptionsServiceImpl implements ISurveyOptionsService
{
    @Autowired
    private SurveyOptionsMapper surveyOptionsMapper;

    /**
     * 查询题目选项
     * 
     * @param optionId 题目选项ID
     * @return 题目选项
     */
    @Override
    public SurveyOptions selectSurveyOptionsById(Long optionId)
    {
        return surveyOptionsMapper.selectSurveyOptionsById(optionId);
    }

    /**
     * 查询题目选项列表
     * 
     * @param surveyOptions 题目选项
     * @return 题目选项
     */
    @Override
    public List<SurveyOptions> selectSurveyOptionsList(SurveyOptions surveyOptions)
    {
        return surveyOptionsMapper.selectSurveyOptionsList(surveyOptions);
    }

    /**
     * 新增题目选项
     * 
     * @param surveyOptions 题目选项
     * @return 结果
     */
    @Override
    public int insertSurveyOptions(SurveyOptions surveyOptions)
    {
        surveyOptions.setCreateTime(DateUtils.getNowDate());
        return surveyOptionsMapper.insertSurveyOptions(surveyOptions);
    }

    /**
     * 修改题目选项
     * 
     * @param surveyOptions 题目选项
     * @return 结果
     */
    @Override
    public int updateSurveyOptions(SurveyOptions surveyOptions)
    {
        surveyOptions.setUpdateTime(DateUtils.getNowDate());
        return surveyOptionsMapper.updateSurveyOptions(surveyOptions);
    }

    /**
     * 批量删除题目选项
     * 
     * @param optionIds 需要删除的题目选项ID
     * @return 结果
     */
    @Override
    public int deleteSurveyOptionsByIds(Long[] optionIds)
    {
        return surveyOptionsMapper.deleteSurveyOptionsByIds(optionIds);
    }

    /**
     * 删除题目选项信息
     * 
     * @param optionId 题目选项ID
     * @return 结果
     */
    @Override
    public int deleteSurveyOptionsById(Long optionId)
    {
        return surveyOptionsMapper.deleteSurveyOptionsById(optionId);
    }
}
