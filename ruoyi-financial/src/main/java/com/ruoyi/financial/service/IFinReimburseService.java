package com.ruoyi.financial.service;

import java.util.List;
import com.ruoyi.financial.domain.FinReimburse;

/**
 * 报销Service接口
 * 
 * @author horou
 * @date 2022-03-20
 */
public interface IFinReimburseService 
{
    /**
     * 查询报销
     * 
     * @param reimburseId 报销主键
     * @return 报销
     */
    public FinReimburse selectFinReimburseByReimburseId(Long reimburseId);

    /**
     * 查询报销列表
     * 
     * @param finReimburse 报销
     * @return 报销集合
     */
    public List<FinReimburse> selectFinReimburseList(FinReimburse finReimburse);

    /**
     * 新增报销
     * 
     * @param finReimburse 报销
     * @return 结果
     */
    public int insertFinReimburse(FinReimburse finReimburse);

    /**
     * 修改报销
     * 
     * @param finReimburse 报销
     * @return 结果
     */
    public int updateFinReimburse(FinReimburse finReimburse);

    /**
     * 批量删除报销
     * 
     * @param reimburseIds 需要删除的报销主键集合
     * @return 结果
     */
    public int deleteFinReimburseByReimburseIds(Long[] reimburseIds);

    /**
     * 删除报销信息
     * 
     * @param reimburseId 报销主键

     * @return 结果
     */
    public int deleteFinReimburseByReimburseId(Long reimburseId);

    /**
     * 上传报销发票信息
     *
     * @param reimburseId 报销主键
     * @param fileName 文件名字
     * @return 结果
     */
    public int addInvoice(Long reimburseId, String fileName);
}
