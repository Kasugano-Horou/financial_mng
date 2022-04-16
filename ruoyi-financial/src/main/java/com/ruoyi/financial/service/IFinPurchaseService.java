package com.ruoyi.financial.service;

import java.util.List;
import com.ruoyi.financial.domain.FinPurchase;

/**
 * 采购订单Service接口
 * 
 * @author horou
 * @date 2022-03-23
 */
public interface IFinPurchaseService 
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
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的采购订单主键集合
     * @return 结果
     */
    public int deleteFinPurchaseByPurchaseIds(Long[] purchaseIds);

    /**
     * 删除采购订单信息
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    public int deleteFinPurchaseByPurchaseId(Long purchaseId);

    /**
     * 上传采购发票信息
     *
     * @param purchaseId 采购主键
     * @param fileName 文件名字
     * @return 结果
     */
    public int addInvoice(Long purchaseId, String fileName);
}
