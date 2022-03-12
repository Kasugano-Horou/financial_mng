package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysEmp;
import java.util.List;

/**
 * 员工表 数据层
 *
 * @author horou
 * @date 2022-03-11
 */
public interface SysEmpMapper
{
    /**
     * 查询员工
     *
     * @param empId 员工主键
     * @return 员工
     */
    public SysEmp selectEmpByEmpId(Long empId);

    /**
     * 查询员工列表
     *
     * @param sysEmp 员工
     * @return 员工集合
     */
    public List<SysEmp> selectEmpList(SysEmp sysEmp);



    /**
     * 新增员工
     *
     * @param sysEmp 员工
     * @return 结果
     */
    public int insertEmp(SysEmp sysEmp);

    /**
     * 修改员工
     *
     * @param sysEmp 员工
     * @return 结果
     */
    public int updateEmp(SysEmp sysEmp);

    /**
     * 删除员工
     *
     * @param empId 员工主键
     * @return 结果
     */
    public int deleteEmpByEmpId(Long empId);

    /**
     * 批量删除员工
     *
     * @param empIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmpByEmpIds(Long[] empIds);
}