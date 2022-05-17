package com.ruoyi.financial.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinWagesMapper;
import com.ruoyi.financial.domain.FinWages;
import com.ruoyi.financial.service.IFinWagesService;

/**
 * 工资Service业务层处理
 * 
 * @author horou
 * @date 2022-03-18
 */
@Service
public class FinWagesServiceImpl implements IFinWagesService 
{
    @Autowired
    private FinWagesMapper finWagesMapper;

    /**
     * 查询工资
     * 
     * @param wageId 工资主键
     * @return 工资
     */
    @Override
    public FinWages selectFinWagesByWageId(Long wageId)
    {
        return finWagesMapper.selectFinWagesByWageId(wageId);
    }

    /**
     * 查询工资列表
     * 
     * @param finWages 工资
     * @return 工资
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<FinWages> selectFinWagesList(FinWages finWages)
    {
        return finWagesMapper.selectFinWagesList(finWages);
    }

    /**
     * 新增工资
     * 
     * @param finWages 工资
     * @return 结果
     */
    @Override
    public int insertFinWages(FinWages finWages)
    {
        finWages.setCreateTime(DateUtils.getNowDate());
        return finWagesMapper.insertFinWages(finWages);
    }

    /**
     * 修改工资
     * 
     * @param finWages 工资
     * @return 结果
     */
    @Override
    public int updateFinWages(FinWages finWages)
    {
        finWages.setUpdateTime(DateUtils.getNowDate());
        return finWagesMapper.updateFinWages(finWages);
    }

    /**
     * 批量删除工资
     * 
     * @param wageIds 需要删除的工资主键
     * @return 结果
     */
    @Override
    public int deleteFinWagesByWageIds(Long[] wageIds)
    {
        return finWagesMapper.deleteFinWagesByWageIds(wageIds);
    }

    /**
     * 删除工资信息
     * 
     * @param wageId 工资主键
     * @return 结果
     */
    @Override
    public int deleteFinWagesByWageId(Long wageId)
    {
        return finWagesMapper.deleteFinWagesByWageId(wageId);
    }
}
