package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProProjectMapper;
import com.ruoyi.project.domain.ProProject;
import com.ruoyi.project.service.IProProjectService;

/**
 * 项目Service业务层处理
 *
 * @author horou
 * @date 2022-03-15
 */
@Service
public class ProProjectServiceImpl implements IProProjectService
{
    @Autowired
    private ProProjectMapper proProjectMapper;

    /**
     * 查询项目
     *
     * @param projectId 项目主键
     * @return 项目
     */
    @Override
    public ProProject selectProProjectByProjectId(Long projectId)
    {
        return proProjectMapper.selectProProjectByProjectId(projectId);
    }

    /**
     * 查询项目列表
     *
     * @param proProject 项目
     * @return 项目
     */
    @Override
    public List<ProProject> selectProProjectList(ProProject proProject)
    {
        return proProjectMapper.selectProProjectList(proProject);
    }

    /**
     * 查询项目表格数据
     *
     * @param proProject 项目
     * @return 项目
     */
    @Override
    public List<ProProject> selectProProjectTableData(ProProject proProject)
    {
        return proProjectMapper.selectProProjectTableData(proProject);
    }

    /**
     * 新增项目
     *
     * @param proProject 项目
     * @return 结果
     */
    @Override
    public int insertProProject(ProProject proProject)
    {
        proProject.setCreateTime(DateUtils.getNowDate());
        return proProjectMapper.insertProProject(proProject);
    }

    /**
     * 修改项目
     *
     * @param proProject 项目
     * @return 结果
     */
    @Override
    public int updateProProject(ProProject proProject)
    {
        proProject.setUpdateTime(DateUtils.getNowDate());
        return proProjectMapper.updateProProject(proProject);
    }

    /**
     * 批量删除项目
     *
     * @param projectIds 需要删除的项目主键
     * @return 结果
     */
    @Override
    public int deleteProProjectByProjectIds(Long[] projectIds)
    {
        return proProjectMapper.deleteProProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目信息
     *
     * @param projectId 项目主键
     * @return 结果
     */
    @Override
    public int deleteProProjectByProjectId(Long projectId)
    {
        return proProjectMapper.deleteProProjectByProjectId(projectId);
    }
}
