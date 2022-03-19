package com.ruoyi.financial.mapper;

import java.util.List;
import com.ruoyi.financial.domain.FinWages;

/**
 * 工资Mapper接口
 * 
 * @author horou
 * @date 2022-03-18
 */
public interface FinWagesMapper 
{
    /**
     * 查询工资
     * 
     * @param wageId 工资主键
     * @return 工资
     */
    public FinWages selectFinWagesByWageId(Long wageId);

    /**
     * 查询工资列表
     * 
     * @param finWages 工资
     * @return 工资集合
     */
    public List<FinWages> selectFinWagesList(FinWages finWages);

    /**
     * 新增工资
     * 
     * @param finWages 工资
     * @return 结果
     */
    public int insertFinWages(FinWages finWages);

    /**
     * 修改工资
     * 
     * @param finWages 工资
     * @return 结果
     */
    public int updateFinWages(FinWages finWages);

    /**
     * 删除工资
     * 
     * @param wageId 工资主键
     * @return 结果
     */
    public int deleteFinWagesByWageId(Long wageId);

    /**
     * 批量删除工资
     * 
     * @param wageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinWagesByWageIds(Long[] wageIds);
}
