package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysEmpMapper;
import com.ruoyi.system.service.ISysEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 员工Service业务层处理
 *
 * @author horou
 * @date 2022-03-11
 */
@Service
public class SysEmpServiceImpl implements ISysEmpService
{
    @Autowired
    private SysEmpMapper sysEmpMapper;

    /**
     * 查询员工
     *
     * @param empId 员工主键
     * @return 员工
     */
    @Override
    public SysEmp selectEmpByEmpId(Long empId)
    {
        return sysEmpMapper.selectEmpByEmpId(empId);
    }

    /**
     * 查询员工列表
     *
     * @param sysEmp 员工
     * @return 员工
     */
    @Override
    public List<SysEmp> selectEmpList(SysEmp sysEmp)
    {
        return sysEmpMapper.selectEmpList(sysEmp);
    }



    /**
     * 新增员工
     *
     * @param sysEmp 员工
     * @return 结果
     */
    @Override
    public int insertEmp(SysEmp sysEmp)
    {
        sysEmp.setCreateTime(DateUtils.getNowDate());
        return sysEmpMapper.insertEmp(sysEmp);
    }

    /**
     * 修改员工
     *
     * @param sysEmp 员工
     * @return 结果
     */
    @Override
    public int updateEmp(SysEmp sysEmp)
    {
        sysEmp.setUpdateTime(DateUtils.getNowDate());
        return sysEmpMapper.updateEmp(sysEmp);
    }

    /**
     * 批量删除员工
     *
     * @param empIds 需要删除的员工主键
     * @return 结果
     */
    @Override
    public int deleteEmpByEmpIds(Long[] empIds)
    {
        return sysEmpMapper.deleteEmpByEmpIds(empIds);
    }

    /**
     * 删除员工信息
     *
     * @param empId 员工主键
     * @return 结果
     */
    @Override
    public int deleteEmpByEmpId(Long empId)
    {
        return sysEmpMapper.deleteEmpByEmpId(empId);
    }
}
