package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.mapper.SysEmpMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysEmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
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
    private static final Logger log = LoggerFactory.getLogger(SysEmpServiceImpl.class);

    @Autowired
    private SysEmpMapper sysEmpMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

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
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysEmp> selectEmpList(SysEmp sysEmp)
    {
        return sysEmpMapper.selectEmpList(sysEmp);
    }

    /**
     * 通过员工号查询员工
     *
     * @param empCode 员工号
     * @return 员工对象信息
     */
    @Override
    public SysEmp selectEmpByEmpCode(String empCode)
    {
        return sysEmpMapper.selectEmpByEmpCode(empCode);
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

    /**
     * 导入员工数据
     *
     * @param empList 员工数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importEmp(List<SysEmp> empList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(empList) || empList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysEmp emp : empList)
        {
            try
            {
                // 验证是否存在这个员工
                SysEmp e = sysEmpMapper.selectEmpByEmpCode(emp.getEmpCode());
                if (StringUtils.isNull(e))
                {
                    BeanValidators.validateWithException(validator, emp);
                    emp.setCreateBy(operName);
                    this.insertEmp(emp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + emp.getEmpName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, emp);
                    emp.setUpdateBy(operName);
                    this.updateEmp(emp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + emp.getEmpName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、员工 " + emp.getEmpName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工 " + emp.getEmpName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
