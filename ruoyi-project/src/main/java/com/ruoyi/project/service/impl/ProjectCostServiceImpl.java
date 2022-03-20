package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectCostMapper;
import com.ruoyi.project.domain.ProjectCost;
import com.ruoyi.project.service.IProjectCostService;

/**
 * 项目成本Service业务层处理
 * 
 * @author horou
 * @date 2022-03-19
 */
@Service
public class ProjectCostServiceImpl implements IProjectCostService 
{
    @Autowired
    private ProjectCostMapper projectCostMapper;

    /**
     * 查询项目成本
     * 
     * @param projectCostId 项目成本主键
     * @return 项目成本
     */
    @Override
    public ProjectCost selectProjectCostByProjectCostId(Long projectCostId)
    {
        return projectCostMapper.selectProjectCostByProjectCostId(projectCostId);
    }

    /**
     * 查询项目成本列表
     * 
     * @param projectCost 项目成本
     * @return 项目成本
     */
    @Override
    public List<ProjectCost> selectProjectCostList(ProjectCost projectCost)
    {
        return projectCostMapper.selectProjectCostList(projectCost);
    }

    /**
     * 新增项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    @Override
    public int insertProjectCost(ProjectCost projectCost)
    {
        projectCost.setCreateTime(DateUtils.getNowDate());
        return projectCostMapper.insertProjectCost(projectCost);
    }

    /**
     * 修改项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    @Override
    public int updateProjectCost(ProjectCost projectCost)
    {
        projectCost.setUpdateTime(DateUtils.getNowDate());
        return projectCostMapper.updateProjectCost(projectCost);
    }

    /**
     * 批量删除项目成本
     * 
     * @param projectCostIds 需要删除的项目成本主键
     * @return 结果
     */
    @Override
    public int deleteProjectCostByProjectCostIds(Long[] projectCostIds)
    {
        return projectCostMapper.deleteProjectCostByProjectCostIds(projectCostIds);
    }

    /**
     * 删除项目成本信息
     * 
     * @param projectCostId 项目成本主键
     * @return 结果
     */
    @Override
    public int deleteProjectCostByProjectCostId(Long projectCostId)
    {
        return projectCostMapper.deleteProjectCostByProjectCostId(projectCostId);
    }
}
