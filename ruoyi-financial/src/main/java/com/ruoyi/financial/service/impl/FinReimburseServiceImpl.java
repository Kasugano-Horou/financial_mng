package com.ruoyi.financial.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinReimburseMapper;
import com.ruoyi.financial.domain.FinReimburse;
import com.ruoyi.financial.service.IFinReimburseService;

/**
 * 报销Service业务层处理
 * 
 * @author horou
 * @date 2022-03-20
 */
@Service
public class FinReimburseServiceImpl implements IFinReimburseService 
{
    @Autowired
    private FinReimburseMapper finReimburseMapper;

    /**
     * 查询报销
     * 
     * @param reimburseId 报销主键
     * @return 报销
     */
    @Override
    public FinReimburse selectFinReimburseByReimburseId(Long reimburseId)
    {
        return finReimburseMapper.selectFinReimburseByReimburseId(reimburseId);
    }

    /**
     * 查询报销列表
     * 
     * @param finReimburse 报销
     * @return 报销
     */
    @Override
    public List<FinReimburse> selectFinReimburseList(FinReimburse finReimburse)
    {
        return finReimburseMapper.selectFinReimburseList(finReimburse);
    }

    /**
     * 新增报销
     * 
     * @param finReimburse 报销
     * @return 结果
     */
    @Override
    public int insertFinReimburse(FinReimburse finReimburse)
    {
        finReimburse.setCreateTime(DateUtils.getNowDate());
        return finReimburseMapper.insertFinReimburse(finReimburse);
    }

    /**
     * 修改报销
     * 
     * @param finReimburse 报销
     * @return 结果
     */
    @Override
    public int updateFinReimburse(FinReimburse finReimburse)
    {
        finReimburse.setUpdateTime(DateUtils.getNowDate());
        return finReimburseMapper.updateFinReimburse(finReimburse);
    }

    /**
     * 批量删除报销
     * 
     * @param reimburseIds 需要删除的报销主键
     * @return 结果
     */
    @Override
    public int deleteFinReimburseByReimburseIds(Long[] reimburseIds)
    {
        return finReimburseMapper.deleteFinReimburseByReimburseIds(reimburseIds);
    }

    /**
     * 删除报销信息
     * 
     * @param reimburseId 报销主键
     * @return 结果
     */
    @Override
    public int deleteFinReimburseByReimburseId(Long reimburseId)
    {
        return finReimburseMapper.deleteFinReimburseByReimburseId(reimburseId);
    }
}
