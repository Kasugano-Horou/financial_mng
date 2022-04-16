package com.ruoyi.financial.mapper;

import com.ruoyi.financial.domain.FinContract;
import java.util.List;

/**
 * 合同Mapper接口
 *
 * @author horou
 * @date 2022-03-08
 */
public interface FinContractMapper {


    /**
     * 查询合同列表
     *
     * @param finContract 合同
     * @return 合同集合
     */
    public List<FinContract> selectFinContractList(FinContract finContract);



    /**
     * 查询合同
     *
     * @param contractId 合同主键
     * @return 合同
     */
    public FinContract selectFinContractByContractId(Long contractId);

    /**
     * 通过合同编号查询合同
     *
     * @param contractNumber 合同编号
     * @return 用户对象信息
     */
    public FinContract selectFinContractByContractNumber(String contractNumber);

    /**
     * 新增合同
     *
     * @param finContract 合同
     * @return 结果
     */
    public int insertFinContract(FinContract finContract);

    /**
     * 修改合同
     *
     * @param finContract 合同
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
     * 删除合同
     *
     * @param contractId 合同主键
     * @return 结果
     */
    public int deleteFinContractByContractId(Long contractId);

    /**
     * 批量删除合同
     *
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinContractByContractIds(Long[] contractIds);
}