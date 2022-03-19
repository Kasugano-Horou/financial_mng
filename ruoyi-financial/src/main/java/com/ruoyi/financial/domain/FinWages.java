package com.ruoyi.financial.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysEmp;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工资对象 fin_wages
 * 
 * @author horou
 * @date 2022-03-18
 */
public class FinWages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工资ID */
    private Long wageId;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String empCode;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String empName;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issuingDate;

    /** 基础工资 */
    @Excel(name = "基础工资")
    private BigDecimal baseWages;

    /** 奖金 */
    @Excel(name = "奖金")
    private BigDecimal bonus;

    /** 五险 */
    @Excel(name = "五险")
    private BigDecimal insurance;

    /** 住房公积金 */
    @Excel(name = "住房公积金")
    private BigDecimal houseFund;

    /** 扣款项 */
    @Excel(name = "扣款项")
    private BigDecimal deducted;

    /** 其他 */
    @Excel(name = "其他")
    private BigDecimal others;

    /** 所得税 */
    @Excel(name = "所得税")
    private BigDecimal tax;

    /** 实发工资 */
    @Excel(name = "实发工资")
    private BigDecimal finalWage;

    private SysEmp sysEmp;

    public void setWageId(Long wageId) 
    {
        this.wageId = wageId;
    }
    public Long getWageId() 
    {
        return wageId;
    }
    public void setEmpCode(String empCode) 
    {
        this.empCode = empCode;
    }
    public String getEmpCode() 
    {
        return empCode;
    }
    public void setEmpName(String empName) 
    {
        this.empName = empName;
    }
    public String getEmpName() 
    {
        return empName;
    }
    public void setIssuingDate(Date issuingDate)
    {
        this.issuingDate = issuingDate;
    }
    public Date getIssuingDate()
    {
        return issuingDate;
    }
    public void setBaseWages(BigDecimal baseWages)
    {
        this.baseWages = baseWages;
    }
    public BigDecimal getBaseWages()
    {
        return baseWages;
    }
    public void setBonus(BigDecimal bonus)
    {
        this.bonus = bonus;
    }
    public BigDecimal getBonus()
    {
        return bonus;
    }
    public void setInsurance(BigDecimal insurance)
    {
        this.insurance = insurance;
    }
    public BigDecimal getInsurance()
    {
        return insurance;
    }
    public void setHouseFund(BigDecimal houseFund)
    {
        this.houseFund = houseFund;
    }
    public BigDecimal getHouseFund()
    {
        return houseFund;
    }
    public void setDeducted(BigDecimal deducted)
    {
        this.deducted = deducted;
    }
    public BigDecimal getDeducted()
    {
        return deducted;
    }
    public void setOthers(BigDecimal others)
    {
        this.others = others;
    }
    public BigDecimal getOthers()
    {
        return others;
    }
    public void setTax(BigDecimal tax)
    {
        this.tax = tax;
    }
    public BigDecimal getTax()
    {
        return tax;
    }
    public void setFinalWage(BigDecimal finalWage)
    {
        this.finalWage = finalWage;
    }
    public BigDecimal getFinalWage()
    {
        return finalWage;
    }
    public void setSysEmp(SysEmp sysEmp) {this.sysEmp = sysEmp;}
    public SysEmp getSysEmp(){ return sysEmp;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wageId", getWageId())
            .append("empCode", getEmpCode())
            .append("empName", getEmpName())
            .append("issuingDate", getIssuingDate())
            .append("baseWages", getBaseWages())
            .append("bonus", getBonus())
            .append("insurance", getInsurance())
            .append("houseFund", getHouseFund())
            .append("deducted", getDeducted())
            .append("others", getOthers())
            .append("tax", getTax())
            .append("finalWage", getFinalWage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("sysEmp", getSysEmp())
            .toString();
    }
}
