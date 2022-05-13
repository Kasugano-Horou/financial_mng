package com.ruoyi.financial.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysFileInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OptionalUtil;
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinInvoiceMapper;
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.financial.service.IFinInvoiceService;

/**
 * 发票Service业务层处理
 * 
 * @author horou
 * @date 2022-03-23
 */
@Service
public class FinInvoiceServiceImpl implements IFinInvoiceService 
{
    @Autowired
    private FinInvoiceMapper finInvoiceMapper;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询发票
     * 
     * @param invoiceId 发票主键
     * @return 发票
     */
    @Override
    public FinInvoice selectFinInvoiceByInvoiceId(Long invoiceId)
    {
        return finInvoiceMapper.selectFinInvoiceByInvoiceId(invoiceId);
    }

    /**
     * 查询发票列表
     * 
     * @param finInvoice 发票
     * @return 发票
     */
    @Override
    public List<FinInvoice> selectFinInvoiceList(FinInvoice finInvoice)
    {
        List<FinInvoice> finInvoiceList = finInvoiceMapper.selectFinInvoiceList(finInvoice);
        for(int i = 0; i < finInvoiceList.size(); i++){
            FinInvoice finInvoice1 = finInvoiceList.get(i);
            SysFileInfo sysFileInfo = finInvoice1.getSysFileInfo();
            if(sysFileInfo != null){
                SysFileInfo finalSysFileInfo = sysFileInfo;
                String filePath = OptionalUtil.or(() ->serverConfig.getUrl() + finalSysFileInfo.getFilePath(), "");
                sysFileInfo.setFilePath(filePath);
            }else {
                sysFileInfo = new SysFileInfo();
                sysFileInfo.setFilePath("");
            }

            finInvoice1.setSysFileInfo(sysFileInfo);
            finInvoiceList.set(i,finInvoice1);
        }
        return finInvoiceList;
    }

//    /**
//     * 查询同一来源所有发票信息
//     *
//     * @param invoiceFrom 发票来源
//     * @return 发票
//     */
//    @Override
//    public List<FinInvoice> selectFinInvoiceByInvoiceFrom(String invoiceFrom)
//    {
//        return finInvoiceMapper.selectFinInvoiceByInvoiceFrom(invoiceFrom);
//    }

    /**
     * 新增发票
     * 
     * @param finInvoice 发票
     * @return 结果
     */
    @Override
    public int insertFinInvoice(FinInvoice finInvoice)
    {
        finInvoice.setCreateTime(DateUtils.getNowDate());
        return finInvoiceMapper.insertFinInvoice(finInvoice);
    }

    /**
     * 修改发票
     * 
     * @param finInvoice 发票
     * @return 结果
     */
    @Override
    public int updateFinInvoice(FinInvoice finInvoice)
    {
        finInvoice.setUpdateTime(DateUtils.getNowDate());
        return finInvoiceMapper.updateFinInvoice(finInvoice);
    }

    /**
     * 批量删除发票
     * 
     * @param invoiceIds 需要删除的发票主键
     * @return 结果
     */
    @Override
    public int deleteFinInvoiceByInvoiceIds(Long[] invoiceIds)
    {
        return finInvoiceMapper.deleteFinInvoiceByInvoiceIds(invoiceIds);
    }

    /**
     * 删除发票信息
     * 
     * @param invoiceId 发票主键
     * @return 结果
     */
    @Override
    public int deleteFinInvoiceByInvoiceId(Long invoiceId)
    {
        return finInvoiceMapper.deleteFinInvoiceByInvoiceId(invoiceId);
    }

    /**
     * 添加发票文件
     *
     * @param invoiceId 发票主键
     * @param fileId 文件主键
     * @retyrn
     */
    public int insertFinInvoiceFile(Long invoiceId, Long fileId)
    {
        return finInvoiceMapper.insertFinInvoiceFile(invoiceId, fileId);

    }
}
