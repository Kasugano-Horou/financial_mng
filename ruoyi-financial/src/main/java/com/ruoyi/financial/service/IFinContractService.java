package com.ruoyi.financial.service;

import com.ruoyi.financial.domain.FinContract;
import java.util.List;

/**
 * 合同管理Service接口
 *
 * @author horou
 * @date 2022-03-08
 */
public interface IFinContractService
{
    /**
     * 查询合同管理
     *
     * @param contractId 合同管理主键
     * @return 合同管理
     */
    public FinContract selectFinContractByContractId(Long contractId);

    /**
     * 查询合同管理列表
     *
     * @param finContract 合同管理
     * @return 合同管理集合
     */
    public List<FinContract> selectFinContractList(FinContract finContract);

    /**
     * 新增合同管理
     *
     * @param finContract 合同管理
     * @return 结果
     */
    public int insertFinContract(FinContract finContract);

    /**
     * 修改合同管理
     *
     * @param finContract 合同管理
     * @return 结果
     */
    public int updateFinContract(FinContract finContract);

    /**
     * 修改合同状态
     *
     * @param finContract 合同
     * @return 结果
     */
    public int updateFinContractStatus(FinContract finContract);

    /**
     * 批量删除合同管理
     *
     * @param contractIds 需要删除的合同管理主键集合
     * @return 结果
     */
    public int deleteFinContractByContractIds(Long[] contractIds);

    /**
     * 删除合同管理信息
     *
     * @param contractId 合同管理主键
     * @return 结果
     */
    public int deleteFinContractByContractId(Long contractId);
}