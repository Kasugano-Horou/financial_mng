package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目对象 pro_project
 *
 * @author horou
 * @date 2022-03-15
 */
public class ProProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    private Long projectId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectNumber;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 合同id */
    @Excel(name = "合同id")
    private Long contractId;

    /** 项目类型（1内部 2外部 3科研 4其他） */
    @Excel(name = "项目类型", readConverterExp = "1=内部,2=外部,3=科研,4=其他")
    private String projectType;

    /** 项目状态（1待审核 2未启动 3进行中 4已关闭） */
    @Excel(name = "项目类型", readConverterExp = "1=待审核,2=未启动,3=进行中,4=已关闭")
    private String status;

    /** 项目甲方 */
    @Excel(name = "项目甲方")
    private String parta;

    /** 项目负责人 */
    @Excel(name = "项目负责人")
    private String leader;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectNumber(String projectNumber) { this.projectNumber = projectNumber; }
    public String getProjectNumber() { return this.projectNumber; }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    public String getProjectName()
    {
        return projectName;
    }
    public void setContractId(Long contractId)
    {
        this.contractId = contractId;
    }
    public Long getContractId()
    {
        return contractId;
    }
    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }
    public String getProjectType()
    {
        return projectType;
    }
    public void setStatus(String status) {this.status = status;}
    public String getStatus() { return status; }
    public void setParta(String parta)
    {
        this.parta = parta;
    }
    public String getParta()
    {
        return parta;
    }
    public void setLeader(String leader)
    {
        this.leader = leader;
    }
    public String getLeader()
    {
        return leader;
    }
    public String getDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("projectNumber", getProjectNumber())
            .append("projectName", getProjectName())
            .append("contractId", getContractId())
            .append("projectType", getProjectType())
            .append("status", getStatus())
            .append("parta", getParta())
            .append("leader", getLeader())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}