package com.ruoyi.financial.service;

import java.util.List;
import com.ruoyi.financial.domain.FinInvoice;

/**
 * 发票Service接口
 * 
 * @author horou
 * @date 2022-03-23
 */
public interface IFinInvoiceService 
{
    /**
     * 查询发票
     * 
     * @param invoiceId 发票主键
     * @return 发票
     */
    public FinInvoice selectFinInvoiceByInvoiceId(Long invoiceId);

    /**
     * 查询发票列表
     * 
     * @param finInvoice 发票
     * @return 发票集合
     */
    public List<FinInvoice> selectFinInvoiceList(FinInvoice finInvoice);

//    /**
//     * 查询同一来源所有发票信息
//     *
//     * @param invoiceFrom 发票来源
//     * @return 发票
//     */
//    public List<FinInvoice> selectFinInvoiceByInvoiceFrom(String invoiceFrom);

    /**
     * 新增发票
     * 
     * @param finInvoice 发票
     * @return 结果
     */
    public int insertFinInvoice(FinInvoice finInvoice);

    /**
     * 修改发票
     * 
     * @param finInvoice 发票
     * @return 结果
     */
    public int updateFinInvoice(FinInvoice finInvoice);

    /**
     * 批量删除发票
     * 
     * @param invoiceIds 需要删除的发票主键集合
     * @return 结果
     */
    public int deleteFinInvoiceByInvoiceIds(Long[] invoiceIds);

    /**
     * 删除发票信息
     * 
     * @param invoiceId 发票主键
     * @return 结果
     */
    public int deleteFinInvoiceByInvoiceId(Long invoiceId);

    /**
     * 添加发票文件
     *
     * @param invoiceId 发票主键
     * @param fileId 文件主键
     * @retyrn
     */
    public int insertFinInvoiceFile(Long invoiceId, Long fileId);

    /**
     * 上传采购发票信息
     *
     * @param finInvoice 发票信息
     * @param fileName 文件名字
     * @return 结果
     */
    public int addInvoice(FinInvoice finInvoice, String fileName);
}
