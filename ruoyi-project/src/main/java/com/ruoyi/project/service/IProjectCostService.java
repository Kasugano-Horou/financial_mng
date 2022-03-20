package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectCost;

/**
 * 项目成本Service接口
 * 
 * @author horou
 * @date 2022-03-19
 */
public interface IProjectCostService 
{
    /**
     * 查询项目成本
     * 
     * @param projectCostId 项目成本主键
     * @return 项目成本
     */
    public ProjectCost selectProjectCostByProjectCostId(Long projectCostId);

    /**
     * 查询项目成本列表
     * 
     * @param projectCost 项目成本
     * @return 项目成本集合
     */
    public List<ProjectCost> selectProjectCostList(ProjectCost projectCost);

    /**
     * 新增项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    public int insertProjectCost(ProjectCost projectCost);

    /**
     * 修改项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    public int updateProjectCost(ProjectCost projectCost);

    /**
     * 批量删除项目成本
     * 
     * @param projectCostIds 需要删除的项目成本主键集合
     * @return 结果
     */
    public int deleteProjectCostByProjectCostIds(Long[] projectCostIds);

    /**
     * 删除项目成本信息
     * 
     * @param projectCostId 项目成本主键
     * @return 结果
     */
    public int deleteProjectCostByProjectCostId(Long projectCostId);
}
