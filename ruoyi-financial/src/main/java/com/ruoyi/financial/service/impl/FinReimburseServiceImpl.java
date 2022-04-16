package com.ruoyi.financial.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysFileInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OptionalUtil;
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.financial.mapper.FinInvoiceMapper;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.financial.mapper.FinReimburseMapper;
import com.ruoyi.financial.domain.FinReimburse;
import com.ruoyi.financial.service.IFinReimburseService;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报销Service业务层处理
 * 
 * @author horou
 * @date 2022-03-20
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class FinReimburseServiceImpl implements IFinReimburseService 
{
    @Autowired
    private FinReimburseMapper finReimburseMapper;
    @Autowired
    private FinInvoiceMapper finInvoiceMapper;
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;
    @Autowired
    private ServerConfig serverConfig;

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
        List<FinReimburse> finReimburseList = finReimburseMapper.selectFinReimburseList(finReimburse);
        for(int i = 0; i < finReimburseList.size(); i++){
            FinReimburse finReimburse1 = finReimburseList.get(i);
            SysFileInfo sysFileInfo = finReimburse1.getSysFileInfo();
            if(sysFileInfo != null){
                SysFileInfo finalSysFileInfo = sysFileInfo;
                String filePath = OptionalUtil.or(() ->serverConfig.getUrl() + finalSysFileInfo.getFilePath(), "");
                sysFileInfo.setFilePath(filePath);
            }else {
                sysFileInfo = new SysFileInfo();
                sysFileInfo.setFilePath("");
            }

            finReimburse1.setSysFileInfo(sysFileInfo);
            finReimburseList.set(i,finReimburse1);
        }
        return finReimburseList;
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

    /**
     * 上传报销发票信息
     *
     * @param reimburseId 报销主键
     * @param fileName 文件名字
     * @return 结果
     */
    public int addInvoice(Long reimburseId, String fileName)
    {
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
        finInvoice.setInvoiceFrom("2");
        finInvoiceMapper.insertFinInvoice(finInvoice);

        //更新发票-文件表
        finInvoiceMapper.insertFinInvoiceFile(finInvoice.getInvoiceId(), sysFileInfo.getFileId());

        //更新报销信息
        FinReimburse finReimburse1 = new FinReimburse();
        finReimburse1.setReimburseId(reimburseId);
        finReimburse1.setStatus("2");
        finReimburse1.setInvoiceId(finInvoice.getInvoiceId());
        return finReimburseMapper.updateFinReimburse(finReimburse1);

    }
}
