package com.ruoyi.financial.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.common.core.domain.entity.SysFileInfo;
import com.ruoyi.project.domain.ProProject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购订单对象 fin_purchase
 * 
 * @author horou
 * @date 2022-03-23
 */
public class FinPurchase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购ID */
    private Long purchaseId;

    /** 项目ID */
    @Excel(name = "项目ID", type = Type.IMPORT)
    private Long projectId;

    /** 发票ID */
    @Excel(name = "发票ID", type = Type.IMPORT)
    private Long invoiceId;

    /** 采购主题 */
    @Excel(name = "采购主题")
    private String purchaseName;

    /** 采购类别 */
    @Excel(name = "采购类别")
    private String purchaseType;

    /** 采购员 */
    @Excel(name = "采购员")
    private Long purchaser;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplier;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 员工对象 */
    @Excels({
            @Excel(name = "员工姓名", targetAttr = "empName", type = Type.EXPORT),
            @Excel(name = "员工编号", targetAttr = "empCode", type = Type.EXPORT)
    })
    private SysEmp emp;

    /** 项目 */
    @Excels({
            @Excel(name = "项目名称", targetAttr = "projectName", type = Type.EXPORT),
            @Excel(name = "项目负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private ProProject project;

    /** 文件对象 */
    private SysFileInfo sysFileInfo;

    public void setPurchaseId(Long purchaseId) 
    {
        this.purchaseId = purchaseId;
    }
    public Long getPurchaseId() 
    {
        return purchaseId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }
    public Long getProjectId() 
    {
        return projectId;
    }
    public void setInvoiceId(Long invoiceId) 
    {
        this.invoiceId = invoiceId;
    }
    public Long getInvoiceId() 
    {
        return invoiceId;
    }
    public void setPurchaseName(String purchaseName) 
    {
        this.purchaseName = purchaseName;
    }
    public String getPurchaseName() 
    {
        return purchaseName;
    }
    public void setPurchaseType(String purchaseType) 
    {
        this.purchaseType = purchaseType;
    }
    public String getPurchaseType() 
    {
        return purchaseType;
    }
    public void setPurchaser(Long purchaser)
    {
        this.purchaser = purchaser;
    }
    public Long getPurchaser()
    {
        return purchaser;
    }
    public void setSupplier(String supplier) 
    {
        this.supplier = supplier;
    }
    public String getSupplier() 
    {
        return supplier;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }
    public String getStatus() 
    {
        return status;
    }
    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }
    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setEmp(SysEmp emp) { this.emp = emp; }
    public SysEmp getEmp() { return emp; }
    public void setProject(ProProject project) { this.project = project; }
    public ProProject getProject() { return project; }
    public void setSysFileInfo(SysFileInfo sysFileInfo) {
        this.sysFileInfo = sysFileInfo;
    }

    public SysFileInfo getSysFileInfo() { return this.sysFileInfo; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("purchaseId", getPurchaseId())
            .append("projectId", getProjectId())
            .append("invoiceId", getInvoiceId())
            .append("purchaseName", getPurchaseName())
            .append("purchaseType", getPurchaseType())
            .append("purchaser", getPurchaser())
            .append("supplier", getSupplier())
            .append("status", getStatus())
            .append("purchaseDate", getPurchaseDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("emp", getEmp())
            .append("project", getProject())
            .append("sysFileInfo", getSysFileInfo())
            .toString();
    }
}
