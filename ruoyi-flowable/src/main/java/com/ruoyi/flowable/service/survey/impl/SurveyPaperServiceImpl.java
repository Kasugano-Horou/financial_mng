package com.ruoyi.flowable.service.survey.impl;

import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.flowable.domain.survey.SurveyPaper;
import com.ruoyi.flowable.mapper.survey.SurveyPaperMapper;
import com.ruoyi.flowable.service.survey.ISurveyPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调查问卷模板Service业务层处理
 * 
 * @author 2y
 * @date 2021-03-21
 */
@Service
public class SurveyPaperServiceImpl implements ISurveyPaperService
{
    @Autowired
    private SurveyPaperMapper surveyPaperMapper;

    /**
     * 查询调查问卷模板
     * 
     * @param id 调查问卷模板ID
     * @return 调查问卷模板
     */
    @Override
    public SurveyPaper selectSurveyPaperById(Long id)
    {
        return surveyPaperMapper.selectSurveyPaperById(id);
    }

    /**
     * 查询调查问卷模板列表
     * 
     * @param surveyPaper 调查问卷模板
     * @return 调查问卷模板
     */
    @Override
    public List<SurveyPaper> selectSurveyPaperList(SurveyPaper surveyPaper)
    {
        return surveyPaperMapper.selectSurveyPaperList(surveyPaper);
    }

    /**
     * 新增调查问卷模板
     * 
     * @param surveyPaper 调查问卷模板
     * @return 结果
     */
    @Override
    public int insertSurveyPaper(SurveyPaper surveyPaper)
    {
        surveyPaper.setCreateTime(DateUtils.getNowDate());
        return surveyPaperMapper.insertSurveyPaper(surveyPaper);
    }

    /**
     * 修改调查问卷模板
     * 
     * @param surveyPaper 调查问卷模板
     * @return 结果
     */
    @Override
    public int updateSurveyPaper(SurveyPaper surveyPaper)
    {
        surveyPaper.setUpdateTime(DateUtils.getNowDate());
        return surveyPaperMapper.updateSurveyPaper(surveyPaper);
    }

    /**
     * 批量删除调查问卷模板
     * 
     * @param ids 需要删除的调查问卷模板ID
     * @return 结果
     */
    @Override
    public int deleteSurveyPaperByIds(Long[] ids)
    {
        return surveyPaperMapper.deleteSurveyPaperByIds(ids);
    }

    /**
     * 删除调查问卷模板信息
     * 
     * @param id 调查问卷模板ID
     * @return 结果
     */
    @Override
    public int deleteSurveyPaperById(Long id)
    {
        return surveyPaperMapper.deleteSurveyPaperById(id);
    }

    /**
     * 查询所有问卷信息
     *
     * @return
     */
    @Override
    public List<SurveyPaper> selectPaperAll() {
        return surveyPaperMapper.selectPaperAll();
    }

    /**
     * 问卷详情
     *
     * @param id
     * @return
     */
    @Override
    public SurveyPaper selectSurveyPaperDetail(Long id) {
        return surveyPaperMapper.selectSurveyPaperDetail(id);
    }
}
