package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 员工Service接口
 *
 * @author horou
 * @date 2022-03-11
 */
public interface ISysEmpService
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
     * 通过员工号查询员工
     *
     * @param empCode 员工号
     * @return 员工对象信息
     */
    public SysEmp selectEmpByEmpCode(String empCode);

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
     * 批量删除员工
     *
     * @param empIds 需要删除的员工主键集合
     * @return 结果
     */
    public int deleteEmpByEmpIds(Long[] empIds);

    /**
     * 删除员工信息
     *
     * @param empId 员工主键
     * @return 结果
     */
    public int deleteEmpByEmpId(Long empId);

    /**
     * 导入员工数据
     *
     * @param empList 员工数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importEmp(List<SysEmp> empList, Boolean isUpdateSupport, String operName);
}