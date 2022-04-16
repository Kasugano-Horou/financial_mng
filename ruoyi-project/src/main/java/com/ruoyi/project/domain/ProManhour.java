package com.ruoyi.project.domain;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysEmp;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工时对象 pro_manhour
 * 
 * @author horou
 * @date 2022-03-16
 */
public class ProManhour extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工时ID */
    private Long manhourId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long empId;

    /** 项目工时 */
    @Excel(name = "项目工时")
    private Long manhour;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private ProProject proProject;

    private SysEmp sysEmp;

    public void setManhourId(Long manhourId) 
    {
        this.manhourId = manhourId;
    }
    public Long getManhourId() 
    {
        return manhourId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }
    public Long getProjectId() 
    {
        return projectId;
    }
    public void setEmpId(Long empId)
    {
        this.empId = empId;
    }
    public Long getEmpId()
    {
        return empId;
    }
    public void setManhour(Long manhour) 
    {
        this.manhour = manhour;
    }
    public Long getManhour() 
    {
        return manhour;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setProProject(ProProject proProject) {this.proProject = proProject;}
    public ProProject getProProject(){ return proProject;}
    public void setSysEmp(SysEmp sysEmp) {this.sysEmp = sysEmp;}
    public SysEmp getSysEmp(){ return sysEmp;}



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("manhourId", getManhourId())
            .append("projectId", getProjectId())
            .append("empId", getEmpId())
            .append("manhour", getManhour())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("proproject", getProProject())
            .append("sysEmp", getSysEmp())
            .toString();
    }
}
