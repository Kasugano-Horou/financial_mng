package com.ruoyi.financial.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysFileInfo;
import com.ruoyi.project.domain.ProProject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发票对象 fin_invoice
 * 
 * @author horou
 * @date 2022-03-23
 */
public class FinInvoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发票ID */
    private Long invoiceId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private String invoiceType;

    /** 发票来源" */
    @Excel(name = "发票来源")
    private String invoiceFrom;

    /** 发票名称 */
    @Excel(name = "发票名称")
    private String invoiceName;

    /** 发票代码 */
    @Excel(name = "发票代码")
    private String invoiceCode;

    /** 发票号码 */
    @Excel(name = "发票号码")
    private String invoiceNumber;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invoiceDate;

    /** 购方名称 */
    @Excel(name = "购方名称")
    private String purchasesName;

    /** 购方纳税人识别号 */
    @Excel(name = "购方纳税人识别号")
    private String purchasesNumber;

    /** 购方地址及电话 */
    @Excel(name = "购方地址及电话")
    private String purchasesAddressTel;

    /** 购方开户行及账号 */
    @Excel(name = "购方开户行及账号")
    private String purchasesBankersAccount;

    /** 销售方名称 */
    @Excel(name = "销售方名称")
    private String salersName;

    /** 销售方纳税人识别号 */
    @Excel(name = "销售方纳税人识别号")
    private String salersNumber;

    /** 销售方地址及电话 */
    @Excel(name = "销售方地址及电话")
    private String salersAddressTel;

    /** 销售方开户行及账号 */
    @Excel(name = "销售方开户行及账号")
    private String salersBankersCcount;

    /** 合计金额 */
    @Excel(name = "合计金额")
    private BigDecimal total;

    /** 合计税额 */
    @Excel(name = "合计税额")
    private BigDecimal tax;

    /** 价税合计(大写) */
    @Excel(name = "价税合计(大写)")
    private String pricePlusChinese;

    /** 价税合计(小写) */
    @Excel(name = "价税合计(小写)")
    private BigDecimal pricePlusArabic;

    /** 密码区 */
    @Excel(name = "密码区")
    private String password;

    /** 收款人 */
    @Excel(name = "收款人")
    private String payee;

    /** 复核 */
    @Excel(name = "复核")
    private String review;

    /** 开票人 */
    @Excel(name = "开票人")
    private String drawer;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private ProProject project;

    /** 文件对象 */
    private SysFileInfo sysFileInfo;

    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public Long getInvoiceId() 
    {
        return invoiceId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }
    public Long getProjectId() 
    {
        return projectId;
    }
    public void setInvoiceType(String invoiceType) 
    {
        this.invoiceType = invoiceType;
    }
    public String getInvoiceType() 
    {
        return invoiceType;
    }
    public void setInvoiceFrom(String invoiceFrom) { this.invoiceFrom = invoiceFrom; }
    public String getInvoiceFrom() { return invoiceFrom; }
    public void setInvoiceName(String invoiceName) 
    {
        this.invoiceName = invoiceName;
    }
    public String getInvoiceName() 
    {
        return invoiceName;
    }
    public void setInvoiceCode(String invoiceCode) 
    {
        this.invoiceCode = invoiceCode;
    }
    public String getInvoiceCode() 
    {
        return invoiceCode;
    }
    public void setInvoiceNumber(String invoiceNumber) 
    {
        this.invoiceNumber = invoiceNumber;
    }
    public String getInvoiceNumber() 
    {
        return invoiceNumber;
    }
    public void setInvoiceDate(Date invoiceDate) 
    {
        this.invoiceDate = invoiceDate;
    }
    public Date getInvoiceDate() 
    {
        return invoiceDate;
    }
    public void setPurchasesName(String purchasesName) 
    {
        this.purchasesName = purchasesName;
    }
    public String getPurchasesName() 
    {
        return purchasesName;
    }
    public void setPurchasesNumber(String purchasesNumber) 
    {
        this.purchasesNumber = purchasesNumber;
    }
    public String getPurchasesNumber() 
    {
        return purchasesNumber;
    }
    public void setPurchasesAddressTel(String purchasesAddressTel) 
    {
        this.purchasesAddressTel = purchasesAddressTel;
    }
    public String getPurchasesAddressTel() 
    {
        return purchasesAddressTel;
    }
    public void setPurchasesBankersAccount(String purchasesBankersAccount) { this.purchasesBankersAccount = purchasesBankersAccount; }
    public String getPurchasesBankersAccount() 
    {
        return purchasesBankersAccount;
    }
    public void setSalersName(String salersName) 
    {
        this.salersName = salersName;
    }
    public String getSalersName() 
    {
        return salersName;
    }
    public void setSalersNumber(String salersNumber) 
    {
        this.salersNumber = salersNumber;
    }
    public String getSalersNumber() 
    {
        return salersNumber;
    }
    public void setSalersAddressTel(String salersAddressTel) 
    {
        this.salersAddressTel = salersAddressTel;
    }
    public String getSalersAddressTel() 
    {
        return salersAddressTel;
    }
    public void setSalersBankersCcount(String salersBankersCcount) 
    {
        this.salersBankersCcount = salersBankersCcount;
    }
    public String getSalersBankersCcount() 
    {
        return salersBankersCcount;
    }
    public void setTotal(BigDecimal total) 
    {
        this.total = total;
    }
    public BigDecimal getTotal()
    {
        return total;
    }
    public void setTax(BigDecimal tax) 
    {
        this.tax = tax;
    }
    public BigDecimal getTax() 
    {
        return tax;
    }
    public void setPricePlusChinese(String pricePlusChinese) 
    {
        this.pricePlusChinese = pricePlusChinese;
    }
    public String getPricePlusChinese() 
    {
        return pricePlusChinese;
    }
    public void setPricePlusArabic(BigDecimal pricePlusArabic) 
    {
        this.pricePlusArabic = pricePlusArabic;
    }
    public BigDecimal getPricePlusArabic() 
    {
        return pricePlusArabic;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }
    public String getPassword() 
    {
        return password;
    }
    public void setPayee(String payee) 
    {
        this.payee = payee;
    }
    public String getPayee() 
    {
        return payee;
    }
    public void setReview(String review) 
    {
        this.review = review;
    }
    public String getReview() 
    {
        return review;
    }
    public void setDrawer(String drawer) 
    {
        this.drawer = drawer;
    }
    public String getDrawer() 
    {
        return drawer;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }
    public String getMemo() 
    {
        return memo;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }
    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setProject(ProProject project) { this.project = project; }
    public ProProject getProject() { return project; }
    public void setSysFileInfo(SysFileInfo sysFileInfo) {
        this.sysFileInfo = sysFileInfo;
    }

    public SysFileInfo getSysFileInfo() { return this.sysFileInfo; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("invoiceId", getInvoiceId())
            .append("projectId", getProjectId())
            .append("invoiceType", getInvoiceType())
            .append("invoiceFrom", getInvoiceFrom())
            .append("invoiceName", getInvoiceName())
            .append("invoiceCode", getInvoiceCode())
            .append("invoiceNumber", getInvoiceNumber())
            .append("invoiceDate", getInvoiceDate())
            .append("purchasesName", getPurchasesName())
            .append("purchasesNumber", getPurchasesNumber())
            .append("purchasesAddressTel", getPurchasesAddressTel())
            .append("purchasesBankersAccount", getPurchasesBankersAccount())
            .append("salersName", getSalersName())
            .append("salersNumber", getSalersNumber())
            .append("salersAddressTel", getSalersAddressTel())
            .append("salersBankersCcount", getSalersBankersCcount())
            .append("total", getTotal())
            .append("tax", getTax())
            .append("pricePlusChinese", getPricePlusChinese())
            .append("pricePlusArabic", getPricePlusArabic())
            .append("password", getPassword())
            .append("payee", getPayee())
            .append("review", getReview())
            .append("drawer", getDrawer())
            .append("memo", getMemo())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("project", getProject())
            .append("sysFileInfo", getSysFileInfo())
            .toString();
    }
}
