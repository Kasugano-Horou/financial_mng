package com.ruoyi.project.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目成本对象 pro_project_cost
 * 
 * @author horou
 * @date 2022-03-19
 */
public class ProjectCost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long projectCostId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNumber;

    /** 营业税金 */
    @Excel(name = "营业税金")
    private BigDecimal businessTax;

    /** 管理成本 */
    @Excel(name = "管理成本")
    private BigDecimal managenmentCost;

    /** 人员成本 */
    @Excel(name = "人员成本")
    private BigDecimal personnelCost;

    /** 采购支出 */
    @Excel(name = "采购支出")
    private BigDecimal procurementCost;

    /** 其他支出 */
    @Excel(name = "其他支出")
    private BigDecimal othersCost;

    /** 维护成本 */
    @Excel(name = "维护成本")
    private BigDecimal maintenanceCost;

    /** 帐期总收入 */
    @Excel(name = "帐期总收入")
    private BigDecimal generalIncome;

    /** 税前利润 */
    @Excel(name = "税前利润")
    private BigDecimal preTax;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setProjectCostId(Long projectCostId) 
    {
        this.projectCostId = projectCostId;
    }
    public Long getProjectCostId() 
    {
        return projectCostId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }
    public String getProjectName()
    {
        return projectName;
    }
    public void setContractNumber(String contractNumber) 
    {
        this.contractNumber = contractNumber;
    }
    public String getContractNumber()
    {
        return contractNumber;
    }
    public void setBusinessTax(BigDecimal businessTax) 
    {
        this.businessTax = businessTax;
    }
    public BigDecimal getBusinessTax()
    {
        return businessTax;
    }
    public void setManagenmentCost(BigDecimal managenmentCost) 
    {
        this.managenmentCost = managenmentCost;
    }
    public BigDecimal getManagenmentCost()
    {
        return managenmentCost;
    }
    public void setPersonnelCost(BigDecimal personnelCost) 
    {
        this.personnelCost = personnelCost;
    }
    public BigDecimal getPersonnelCost()
    {
        return personnelCost;
    }
    public void setProcurementCost(BigDecimal procurementCost) 
    {
        this.procurementCost = procurementCost;
    }
    public BigDecimal getProcurementCost()
    {
        return procurementCost;
    }
    public void setOthersCost(BigDecimal othersCost) 
    {
        this.othersCost = othersCost;
    }
    public BigDecimal getOthersCost()
    {
        return othersCost;
    }
    public void setMaintenanceCost(BigDecimal maintenanceCost) 
    {
        this.maintenanceCost = maintenanceCost;
    }
    public BigDecimal getMaintenanceCost()
    {
        return maintenanceCost;
    }
    public void setGeneralIncome(BigDecimal generalIncome) 
    {
        this.generalIncome = generalIncome;
    }
    public BigDecimal getGeneralIncome()
    {
        return generalIncome;
    }
    public void setPreTax(BigDecimal preTax) 
    {
        this.preTax = preTax;
    }
    public BigDecimal getPreTax()
    {
        return preTax;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectCostId", getProjectCostId())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("contractNumber", getContractNumber())
            .append("businessTax", getBusinessTax())
            .append("managenmentCost", getManagenmentCost())
            .append("personnelCost", getPersonnelCost())
            .append("procurementCost", getProcurementCost())
            .append("othersCost", getOthersCost())
            .append("maintenanceCost", getMaintenanceCost())
            .append("generalIncome", getGeneralIncome())
            .append("preTax", getPreTax())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
