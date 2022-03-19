package com.ruoyi.financial.service.impl;


import com.ruoyi.financial.domain.FinContract;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinContractMapper;
import com.ruoyi.financial.service.IFinContractService;
import java.util.List;

/**
 * 合同管理Service业务层处理
 *
 * @author horou
 * @date 2022-03-08
 */
@Service
public class FinContractServiceImpl implements IFinContractService
{
    @Autowired
    private FinContractMapper finContractMapper;

    /**
     * 查询合同管理
     *
     * @param contractId 合同管理主键
     * @return 合同管理
     */
    @Override
    public FinContract selectFinContractByContractId(Long contractId)
    {
        return finContractMapper.selectFinContractByContractId(contractId);
    }

    /**
     * 查询合同管理列表
     *
     * @param finContract 合同管理
     * @return 合同管理
     */
    @Override
    public List<FinContract> selectFinContractList(FinContract finContract)
    {
        return finContractMapper.selectFinContractList(finContract);
    }

    /**
     * 新增合同管理
     *
     * @param finContract 合同管理
     * @return 结果
     */
    @Override
    public int insertFinContract(FinContract finContract)
    {
        finContract.setCreateTime(DateUtils.getNowDate());
        return finContractMapper.insertFinContract(finContract);
    }

    /**
     * 修改合同管理
     *
     * @param finContract 合同管理
     * @return 结果
     */
    @Override
    public int updateFinContract(FinContract finContract)
    {
        finContract.setUpdateTime(DateUtils.getNowDate());
        return finContractMapper.updateFinContract(finContract);
    }

    /**
     * 修改合同状态
     *
     * @param finContract 合同
     * @return 结果
     */
    @Override
    public int updateFinContractStatus(FinContract finContract){
        finContract.setUpdateTime(DateUtils.getNowDate());
        return finContractMapper.updateFinContractStatus(finContract);
    }

    /**
     * 批量删除合同管理
     *
     * @param contractIds 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteFinContractByContractIds(Long[] contractIds)
    {
        return finContractMapper.deleteFinContractByContractIds(contractIds);
    }

    /**
     * 删除合同管理信息
     *
     * @param contractId 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteFinContractByContractId(Long contractId)
    {
        return finContractMapper.deleteFinContractByContractId(contractId);
    }
}