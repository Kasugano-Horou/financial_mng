package com.ruoyi.financial.domain;

import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysEmp;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.project.domain.ProProject;

/**
 * 报销对象 fin_reimburse
 * 
 * @author horou
 * @date 2022-03-20
 */
public class FinReimburse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long reimburseId;

    /** 报销单号 */
    @Excel(name = "报销单号")
    private String reimburseNumber;

    /** 报销部门 */
    @Excel(name = "报销部门")
    private Long deptId;

    /** 经手人 */
    @Excel(name = "经手人")
    private Long handBy;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private Long projectId;

    /** 报销类型 */
    @Excel(name = "报销类型")
    private String reimburseType;

    /** 附件 */
    @Excel(name = "附件")
    private String annex;

    /** 费用说明 */
    @Excel(name = "费用说明")
    private String details;

    /** 报销状态 */
    @Excel(name = "报销状态")
    private String status;

    /** 流程ID */
    @Excel(name = "流程ID")
    private Long flowId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /** 员工对象 */
    @Excels({
            @Excel(name = "员工姓名", targetAttr = "empName", type = Type.EXPORT),
            @Excel(name = "员工编号", targetAttr = "empCode", type = Type.EXPORT)
    })
    private SysEmp emp;

    /** 项目对象 */
    @Excels({
            @Excel(name = "项目名称", targetAttr = "projectName", type = Type.EXPORT),
            @Excel(name = "项目负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private ProProject project;



    public void setReimburseId(Long reimburseId) 
    {
        this.reimburseId = reimburseId;
    }
    public Long getReimburseId()
    {
        return reimburseId;
    }
    public void setReimburseNumber(String reimburseNumber) 
    {
        this.reimburseNumber = reimburseNumber;
    }
    public String getReimburseNumber() 
    {
        return reimburseNumber;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }
    public Long getDeptId()
    {
        return deptId;
    }
    public void setHandBy(Long handBy) 
    {
        this.handBy = handBy;
    }
    public Long getHandBy()
    {
        return handBy;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public void setReimburseType(String reimburseType) 
    {
        this.reimburseType = reimburseType;
    }
    public String getReimburseType()
    {
        return reimburseType;
    }
    public void setAnnex(String annex) 
    {
        this.annex = annex;
    }
    public String getAnnex()
    {
        return annex;
    }
    public void setDetails(String details) 
    {
        this.details = details;
    }
    public String getDetails()
    {
        return details;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }
    public String getStatus()
    {
        return status;
    }
    public void setFlowId(Long flowId) 
    {
        this.flowId = flowId;
    }
    public Long getFlowId()
    {
        return flowId;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag()
    {
        return delFlag;
    }
    public SysDept getDept() { return dept; }
    public void setDept(SysDept dept) { this.dept = dept; }
    public SysEmp getEmp() { return emp; }
    public void setEmp(SysEmp emp) { this.emp = emp; }
    public ProProject getProject() { return project; }
    public void setProject(ProProject project) { this.project = project; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reimburseId", getReimburseId())
            .append("reimburseNumber", getReimburseNumber())
            .append("deptId", getDeptId())
            .append("handBy", getHandBy())
            .append("projectId", getProjectId())
            .append("reimburseType", getReimburseType())
            .append("annex", getAnnex())
            .append("details", getDetails())
            .append("status", getStatus())
            .append("flowId", getFlowId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .append("emp", getEmp())
            .append("project", getProject())
            .toString();
    }
}
