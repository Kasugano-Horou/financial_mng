package com.ruoyi.financial.mapper;

import java.util.List;
import com.ruoyi.financial.domain.FinPurchase;

/**
 * 采购订单Mapper接口
 * 
 * @author horou
 * @date 2022-03-23
 */
public interface FinPurchaseMapper 
{
    /**
     * 查询采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 采购订单
     */
    public FinPurchase selectFinPurchaseByPurchaseId(Long purchaseId);

    /**
     * 查询采购订单列表
     * 
     * @param finPurchase 采购订单
     * @return 采购订单集合
     */
    public List<FinPurchase> selectFinPurchaseList(FinPurchase finPurchase);

    /**
     * 新增采购订单
     * 
     * @param finPurchase 采购订单
     * @return 结果
     */
    public int insertFinPurchase(FinPurchase finPurchase);

    /**
     * 修改采购订单
     * 
     * @param finPurchase 采购订单
     * @return 结果
     */
    public int updateFinPurchase(FinPurchase finPurchase);

    /**
     * 删除采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    public int deleteFinPurchaseByPurchaseId(Long purchaseId);

    /**
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinPurchaseByPurchaseIds(Long[] purchaseIds);
}
