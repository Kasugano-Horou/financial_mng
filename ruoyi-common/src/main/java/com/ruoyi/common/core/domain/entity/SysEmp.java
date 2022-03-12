package com.ruoyi.common.core.domain.entity;


import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excels;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 员工对象 sys_emp
 *
 * @author horou
 * @date 2022-03-11
 */
public class SysEmp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 员工ID */
    @Excel(name = "员工序号", cellType = ColumnType.NUMERIC, prompt = "员工编号")
    private Long empId;

    /** 工号 */
    @Excel(name = "工号")
    private String empCode;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 性别 */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "1=在职,2=休假,3=离职,4=死亡")
    private String status;

    /** 学历 */
    @Excel(name = "学历", readConverterExp = "1=大专,2=本科,3=研究生,4=博士,5=其他")
    private Long education;

    /** 电话 */
    @Excel(name = "电话")
    private String phonenumber;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 住址 */
    @Excel(name = "住址")
    private String address;

    /** 部门ID */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date hiredate;

    /** 离职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date termdate;

    /** 基础工资 */
    @Excel(name = "基础工资")
    private BigDecimal baseWages;

    /** 住房公积金基数 */
    @Excel(name = "住房公积金基数")
    private String houseWages;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;



    /** 岗位组 */
    private Long[] postIds;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;



    public void setEmpId(Long empId)
    {
        this.empId = empId;
    }

    public Long getEmpId()
    {
        return empId;
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

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setEducation(Long education)
    {
        this.education = education;
    }

    public Long getEducation()
    {
        return education;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public Long getDeptId() { return deptId; }

    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public void setHiredate(Date hiredate)
    {
        this.hiredate = hiredate;
    }

    public Date getHiredate()
    {
        return hiredate;
    }

    public void setTermdate(Date termdate)
    {
        this.termdate = termdate;
    }

    public Date getTermdate()
    {
        return termdate;
    }

    public void setBaseWages(BigDecimal baseWages) { this.baseWages = baseWages; }

    public BigDecimal getBaseWages()
    {
        return baseWages;
    }

    public void setHouseWages(String houseWages)
    {
        this.houseWages = houseWages;
    }

    public String getHouseWages()
    {
        return houseWages;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("empId", getEmpId())
            .append("empCode", getEmpCode())
            .append("empName", getEmpName())
            .append("sex", getSex())
            .append("status", getStatus())
            .append("education", getEducation())
            .append("phonenumber", getPhonenumber())
            .append("email", getEmail())
            .append("address", getAddress())
            .append("hiredate", getHiredate())
            .append("termdate", getTermdate())
            .append("baseWages", getBaseWages())
            .append("houseWages", getHouseWages())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

