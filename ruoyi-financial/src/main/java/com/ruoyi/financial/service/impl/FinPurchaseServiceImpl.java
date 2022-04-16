package com.ruoyi.financial.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysFileInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OptionalUtil;
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.financial.domain.FinPurchase;
import com.ruoyi.financial.mapper.FinInvoiceMapper;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinPurchaseMapper;
import com.ruoyi.financial.domain.FinPurchase;
import com.ruoyi.financial.service.IFinPurchaseService;

/**
 * 采购订单Service业务层处理
 * 
 * @author horou
 * @date 2022-03-23
 */
@Service
public class FinPurchaseServiceImpl implements IFinPurchaseService 
{
    @Autowired
    private FinPurchaseMapper finPurchaseMapper;
    @Autowired
    private FinInvoiceMapper finInvoiceMapper;
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 采购订单
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public FinPurchase selectFinPurchaseByPurchaseId(Long purchaseId)
    {
        
        return finPurchaseMapper.selectFinPurchaseByPurchaseId(purchaseId);
    }

    /**
     * 查询采购订单列表
     * 
     * @param finPurchase 采购订单
     * @return 采购订单
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<FinPurchase> selectFinPurchaseList(FinPurchase finPurchase)
    {
        List<FinPurchase> finPurchaseList = finPurchaseMapper.selectFinPurchaseList(finPurchase);
        for(int i = 0; i < finPurchaseList.size(); i++){
            FinPurchase finPurchase1 = finPurchaseList.get(i);
            SysFileInfo sysFileInfo = finPurchase1.getSysFileInfo();
            if(sysFileInfo != null){
                SysFileInfo finalSysFileInfo = sysFileInfo;
                String filePath = OptionalUtil.or(() ->serverConfig.getUrl() + finalSysFileInfo.getFilePath(), "");
                sysFileInfo.setFilePath(filePath);
            }else {
                sysFileInfo = new SysFileInfo();
                sysFileInfo.setFilePath("");
            }

            finPurchase1.setSysFileInfo(sysFileInfo);
            finPurchaseList.set(i,finPurchase1);
        }
        return finPurchaseList;
    }

    /**
     * 新增采购订单
     * 
     * @param finPurchase 采购订单
     * @return 结果
     */
    @Override
    public int insertFinPurchase(FinPurchase finPurchase)
    {
        finPurchase.setCreateTime(DateUtils.getNowDate());
        return finPurchaseMapper.insertFinPurchase(finPurchase);
    }

    /**
     * 修改采购订单
     * 
     * @param finPurchase 采购订单
     * @return 结果
     */
    @Override
    public int updateFinPurchase(FinPurchase finPurchase)
    {
        finPurchase.setUpdateTime(DateUtils.getNowDate());
        return finPurchaseMapper.updateFinPurchase(finPurchase);
    }

    /**
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的采购订单主键
     * @return 结果
     */
    @Override
    public int deleteFinPurchaseByPurchaseIds(Long[] purchaseIds)
    {
        return finPurchaseMapper.deleteFinPurchaseByPurchaseIds(purchaseIds);
    }

    /**
     * 删除采购订单信息
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    @Override
    public int deleteFinPurchaseByPurchaseId(Long purchaseId)
    {
        return finPurchaseMapper.deleteFinPurchaseByPurchaseId(purchaseId);
    }

    /**
     * 上传采购发票信息
     *
     * @param purchaseId 采购主键
     * @param fileName 文件名字
     * @return 结果
     */
    public int addInvoice(Long purchaseId, String fileName){
        String realName = fileName.substring(fileName.lastIndexOf("/") + 1);
        String filePath = null;
        try {
            filePath = fileName.substring(0, fileName.lastIndexOf("/") + 1) + java.net.URLEncoder.encode(realName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //文件更新到sys_file_info
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileName(realName);
        sysFileInfo.setFilePath(filePath);
        sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
        System.out.println("nameeeeeesysFileInfo.getFileName():"+sysFileInfo.getFileName());
        System.out.println("rullllllllllllsysFilePath.getFilePath():"+sysFileInfo.getFilePath());

        //新增发票信息
        FinInvoice finInvoice = new FinInvoice();
        finInvoice.setInvoiceFrom("3");
        finInvoiceMapper.insertFinInvoice(finInvoice);

        //更新发票-文件表
        finInvoiceMapper.insertFinInvoiceFile(finInvoice.getInvoiceId(), sysFileInfo.getFileId());

        //更新采购信息
        FinPurchase finPurchase1 = new FinPurchase();
        finPurchase1.setPurchaseId(purchaseId);
        finPurchase1.setStatus("2");
        finPurchase1.setInvoiceId(finInvoice.getInvoiceId());
        return finPurchaseMapper.updateFinPurchase(finPurchase1);
    }
}
