package com.ruoyi.financial.domain;

import java.util.Date;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * 合同对象 fin_contract
 *
 * @author ruoyi
 */
public class FinContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同编号 */
    @Excel(name = "合同ID", cellType = ColumnType.NUMERIC, prompt = "合同ID")
    private Long contractId;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNumber;

    /** 合同日期 */
    @Excel(name = "合同日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractDate;

    /** 合同开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "合同开始日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date contractStart;

    /** 合同结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "合同结束日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date contractEnd;

    /** 合同类型 */
    @Excel(name = "合同类型", readConverterExp = "0=内部,1=外部,2=未知")
    private String contractType;

    /** 应收款 */
    @Excel(name = "应收款")
    private Double debtors;

    /** 已收款 */
    @Excel(name = "已收款")
    private Double receivable;

    /** 授权委托人 */
    @Excel(name = "授权委托人")
    private String principal;

    /** 部门ID */
    @Excel(name = "部门ID", type = Type.IMPORT)
    private Long deptId;

    /** 对方单位 */
    @Excel(name = "对方单位")
    private String oppositePartyUnit;

    /** 合同文件地址 */
    @Excel(name = "合同文件地址")
    private String fileAddress;

    /** 合同状态 */
    @Excel(name = "合同状态", readConverterExp = "0=未通过,1=未开始,2=进行中,3=已结束")
    private String status;

    /** 合同签订地点 */
    @Excel(name = "合同签订地点")
    private String contractLocation;

    /** 合同简介 */
    @Excel(name = "合同简介")
    private String introduction;

    /** 删除标志（0代表存在 2代表删除）*/
    private String delFlag;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    public FinContract() { }    public FinContract(Long contractId) { this.contractId=contractId; }
    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }
    @Xss(message = "合同名称不能包含脚本字符")
    @NotBlank(message = "合同名称不能为空")
    @Size(min = 0, max = 255, message = "合同名称长度不能超过255个字符")
    public String getContractName() { return contractName; }
    public void setContractName(String contractName) { this.contractName = contractName; }
    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
    public Date getContractDate() { return contractDate; }
    public void setContractDate(Date contractDate) { this.contractDate = contractDate; }
    public Date getContractStart() { return contractStart; }
    public void setContractStart(Date contractStart) { this.contractStart = contractStart; }
    public Date getContractEnd() { return contractEnd; }
    public void setContractEnd(Date contractEnd) { this.contractEnd = contractEnd; }
    public String getContractType() { return contractType; }
    public void setContractType(String contractType) { this.contractType = contractType; }
    public Double getDebtors() { return debtors; }
    public void setDebtors(Double debtors) { this.debtors = debtors; }
    public Double getReceivable() { return receivable; }
    public void setReceivable(Double receivable) { this.receivable = receivable; }
    public String getPrincipal() { return principal; }
    public void setPrincipal(String principal) { this.principal = principal; }
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public String getOppositePartyUnit() { return oppositePartyUnit; }
    public void setOppositePartyUnit(String oppositePartyUnit) { this.oppositePartyUnit = oppositePartyUnit; }
    public String getFileAddress() { return fileAddress; }
    public void setFileAddress(String fileAddress) { this.fileAddress = fileAddress; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getContractLocation() { return contractLocation; }
    public void setContractLocation(String contractLocation) { this.contractLocation = contractLocation; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public String getDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }
    public SysDept getDept() { return dept; }
    public void setDept(SysDept dept) { this.dept = dept; }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("contractId", getContractId())
        .append("contractName", getContractName())
        .append("contractNumber", getContractNumber())
        .append("contractDate", getContractDate())
        .append("contractStart", getContractStart())
        .append("contractEnd", getContractEnd())
        .append("contractType", getContractType())
        .append("debtors", getDebtors())
        .append("receivable", getReceivable())
        .append("principal",getPrincipal())
        .append("deptId", getDeptId())
        .append("oppositePartyUnit", getOppositePartyUnit())
        .append("fileAddress", getFileAddress())
        .append("status", getStatus())
        .append("contractLocation", getContractLocation())
        .append("introduction", getIntroduction())
        .append("delFlag", getDelFlag())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("remark", getRemark())
        .append("dept", getDept())
        .toString();
    }
}