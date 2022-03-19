package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProProject;

/**
 * 项目Mapper接口
 *
 * @author horou
 * @date 2022-03-15
 */
public interface ProProjectMapper
{
    /**
     * 查询项目
     *
     * @param projectId 项目主键
     * @return 项目
     */
    public ProProject selectProProjectByProjectId(Long projectId);

    /**
     * 查询项目列表
     *
     * @param proProject 项目
     * @return 项目集合
     */
    public List<ProProject> selectProProjectList(ProProject proProject);

    /**
     * 查询项目表格数据
     *
     * @param proProject 项目
     * @return 项目集合
     */
    public List<ProProject> selectProProjectTableData(ProProject proProject);

    /**
     * 新增项目
     *
     * @param proProject 项目
     * @return 结果
     */
    public int insertProProject(ProProject proProject);

    /**
     * 修改项目
     *
     * @param proProject 项目
     * @return 结果
     */
    public int updateProProject(ProProject proProject);

    /**
     * 删除项目
     *
     * @param projectId 项目主键
     * @return 结果
     */
    public int deleteProProjectByProjectId(Long projectId);

    /**
     * 批量删除项目
     *
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProProjectByProjectIds(Long[] projectIds);
}
