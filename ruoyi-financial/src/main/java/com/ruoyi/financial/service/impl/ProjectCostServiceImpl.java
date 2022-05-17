package com.ruoyi.financial.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.financial.domain.*;
import com.ruoyi.financial.mapper.*;
import com.ruoyi.financial.service.IProjectCostService;
import com.ruoyi.project.domain.ProManhour;
import com.ruoyi.project.domain.ProProject;
import com.ruoyi.project.mapper.ProManhourMapper;
import com.ruoyi.project.mapper.ProProjectMapper;
import com.ruoyi.system.mapper.SysEmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.OptionalUtil;


/**
 * 项目成本Service业务层处理
 * 
 * @author horou
 * @date 2022-03-19
 */
@Service
public class ProjectCostServiceImpl implements IProjectCostService
{
    @Autowired
    private ProjectCostMapper projectCostMapper;
    @Autowired
    private FinContractMapper finContractMapper;
    @Autowired
    private ProManhourMapper proManhourMapper;
    @Autowired
    private SysEmpMapper sysEmpMapper;
    @Autowired
    private ProProjectMapper proProjectMapper;
    @Autowired
    private FinReimburseMapper finReimburseMapper;
    @Autowired
    private FinInvoiceMapper finInvoiceMapper;
    @Autowired
    private FinPurchaseMapper finPurchaseMapper;
    @Autowired
    private FinWagesMapper finWagesMapper;

    //每月工作时间 365/12*5/7
    private static final String DATE = "21.75";

    //天工作时间 8h
    private static final String HOUR = "8";

    //项目维护成本比率
    private static final String MAINTANCERATE = "0.10";

    /**
     * 核算项目成本
     *
     * @param projectIds 项目编号
     * @return 结果
     */
    public int accountProjectCostByProjectIds(Long[] projectIds){
        int flag = 0;
        System.out.println("projectNumbers:::");
        if(projectIds.length>0)
        System.out.println(projectIds[0]);
        for(Long projectId : projectIds){
            ProjectCost projectCost = projectCostMapper.selectProjectCostByProjectId(projectId);
            ProProject proProject = proProjectMapper.selectProProjectByProjectId(projectId);
            //合同已收款
            BigDecimal receivable = OptionalUtil.or(() ->finContractMapper.selectFinContractByContractId(projectCost.getFinContract().getContractId()).getReceivable(), new BigDecimal("0.00"));
            //帐期总收入=合同已收款+...
            projectCost.setGeneralIncome(receivable);

            //营业税费=出具发票所交税金总额
            BigDecimal tax = new BigDecimal("0.00");
            FinInvoice finInvoice = new FinInvoice();
            finInvoice.setProjectId(projectId);
            finInvoice.setStatus("3");
            finInvoice.setInvoiceFrom("4");
            List<FinInvoice> finInvoices = finInvoiceMapper.selectFinInvoiceList(finInvoice);
            for(FinInvoice invoice : finInvoices){
                tax = tax.add(invoice.getTax());
            }
            projectCost.setBusinessTax(tax);


            //项目管理成本=合同费用*对应管理费比例
            if(receivable!=null) {
                BigDecimal rate = null;
                if(receivable.compareTo(new BigDecimal("200000")) == -1 ) {
                    rate = new BigDecimal("0.16");
                }else if(receivable.compareTo(new BigDecimal("500000")) == -1 ) {
                    rate = new BigDecimal("0.15");
                }else if(receivable.compareTo(new BigDecimal("10000000")) == -1 ) {
                    rate = new BigDecimal("0.14");
                }else{
                    rate = new BigDecimal("0.13");
                }
                if(proProject.getStatus().equals("7")){
                    projectCost.setManagenmentCost(receivable.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    projectCost.setManagenmentCost(new BigDecimal(0));
                }

            }

            //项目人员成本（人员基本成本、项目人员奖金和、项目负责人成本）
            //人员基本成本=人员工资*1.5
            BigDecimal wages = new BigDecimal("0.00");
            BigDecimal leaderWages = new BigDecimal("0.00");
            BigDecimal peerHour = new BigDecimal(DATE).multiply(new BigDecimal(HOUR));
            ProManhour proManhour = new ProManhour();
            proManhour.setProjectId(projectId);
            List<ProManhour> proManhours = proManhourMapper.selectProManhourList(proManhour);
            for(ProManhour manhour : proManhours) {
                SysEmp emp = sysEmpMapper.selectEmpByEmpId(manhour.getEmpId());
                if (emp.getEmpName().equals(proProject.getLeader())) {
                    //项目负责人成本*40%
                    leaderWages = emp.getBaseWages().multiply(new BigDecimal(Double.toString(manhour.getManhour() != null ? manhour.getManhour() : 0.0)).divide(peerHour, 2, BigDecimal.ROUND_HALF_UP));
                    wages = wages.add(leaderWages);
                }else {
                    //员工工资相加
//                System.out.println("wwww"+new BigDecimal("5.55").multiply(new BigDecimal("8.44")));
//                System.out.println("wwww"+new BigDecimal("5.55").multiply(new BigDecimal("8.44")).setScale(2,BigDecimal.ROUND_HALF_UP));
//                System.out.println("muty2:"+emp.getBaseWages().multiply((new BigDecimal(Double.toString(manhour.getManhour())))).divide(new BigDecimal(DATE), 2, BigDecimal.ROUND_HALF_UP));
//                System.out.println("muty3:"+emp.getBaseWages().multiply(new BigDecimal(Double.toString(manhour.getManhour()))).divide(new BigDecimal(DATE), 2, BigDecimal.ROUND_HALF_UP));
//                System.out.println(".div:"+emp.getBaseWages().multiply(new BigDecimal(Double.toString(manhour.getManhour()))));
                    wages = wages.add(emp.getBaseWages().multiply(new BigDecimal(Double.toString(manhour.getManhour() != null ? manhour.getManhour() : 0.0)).divide(peerHour, 2, BigDecimal.ROUND_HALF_UP)));

                }
            }
            //项目人员奖金和


            //项目人员成本统计
            projectCost.setPersonnelCost(wages.multiply(new BigDecimal("1.5")).add(leaderWages.multiply(new BigDecimal("0.4"))).setScale(2, BigDecimal.ROUND_HALF_UP));

            //采购支出
            BigDecimal procurement = new BigDecimal("0.00");
            FinPurchase finPurchase = new FinPurchase();
            finPurchase.setProjectId(projectId);
            finPurchase.setStatus("4");
            List<FinPurchase> finPurchases = finPurchaseMapper.selectFinPurchaseList(finPurchase);
            for(FinPurchase purchase : finPurchases){
                procurement = procurement.add(OptionalUtil.or(() -> finInvoiceMapper.selectFinInvoiceByInvoiceId(purchase.getInvoiceId()).getTotal(), new BigDecimal("0.00")));
            }
            projectCost.setProcurementCost(procurement);

            //其他相关支出
            BigDecimal others = new BigDecimal("0.00");
            FinReimburse finReimburse = new FinReimburse();
            finReimburse.setProjectId(projectId);
            finReimburse.setStatus("4");
            List<FinReimburse> finReimburses = finReimburseMapper.selectFinReimburseList(finReimburse);
            for(FinReimburse reimburse : finReimburses){
                others = others.add(reimburse.getAmount());
            }
            projectCost.setOthersCost(others);

            //项目维护成本
            BigDecimal maintenance_cost = new BigDecimal("0.00");
            //取消维护成本计算
            projectCost.setMaintenanceCost(maintenance_cost);
//            if(receivable!=null) {
//
//                maintenance_cost = receivable.multiply(new BigDecimal(MAINTANCERATE)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                projectCost.setMaintenanceCost(maintenance_cost);
//            }

            //计算税前利润=项目帐期总收入-(营业税费+项目管理成本+项目人员成本+采购支出+其他相关支出+项目维护成本)
            projectCost.setPreTax(projectCost.getGeneralIncome().subtract(projectCost.getBusinessTax()).subtract(projectCost.getManagenmentCost()).subtract(projectCost.getPersonnelCost()).subtract(projectCost.getProcurementCost()).subtract(projectCost.getOthersCost()).subtract(projectCost.getMaintenanceCost()).setScale(2, BigDecimal.ROUND_HALF_UP));

            System.out.println("projectId"+projectId + "projectCost"+projectCost.toString());
            flag = projectCostMapper.updateProjectCost(projectCost);

        }


        return flag;
    }

    /**
     * 查询项目成本
     * 
     * @param projectCostId 项目成本主键
     * @return 项目成本
     */
    @Override
    public ProjectCost selectProjectCostByProjectCostId(Long projectCostId)
    {
        return projectCostMapper.selectProjectCostByProjectCostId(projectCostId);
    }

    /**
     * 查询项目成本列表
     * 
     * @param projectCost 项目成本
     * @return 项目成本
     */
    @Override
    public List<ProjectCost> selectProjectCostList(ProjectCost projectCost)
    {
        return projectCostMapper.selectProjectCostList(projectCost);
    }

    /**
     * 新增项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    @Override
    public int insertProjectCost(ProjectCost projectCost)
    {
        projectCost.setCreateTime(DateUtils.getNowDate());
        return projectCostMapper.insertProjectCost(projectCost);
    }

    /**
     * 修改项目成本
     * 
     * @param projectCost 项目成本
     * @return 结果
     */
    @Override
    public int updateProjectCost(ProjectCost projectCost)
    {
        projectCost.setUpdateTime(DateUtils.getNowDate());
        return projectCostMapper.updateProjectCost(projectCost);
    }

    /**
     * 批量删除项目成本
     * 
     * @param projectCostIds 需要删除的项目成本主键
     * @return 结果
     */
    @Override
    public int deleteProjectCostByProjectCostIds(Long[] projectCostIds)
    {
        return projectCostMapper.deleteProjectCostByProjectCostIds(projectCostIds);
    }

    /**
     * 删除项目成本信息
     * 
     * @param projectCostId 项目成本主键
     * @return 结果
     */
    @Override
    public int deleteProjectCostByProjectCostId(Long projectCostId)
    {
        return projectCostMapper.deleteProjectCostByProjectCostId(projectCostId);
    }

//    /**
//     * 查询利润走势列表
//     *
//     * @param
//     * @return 项目成本
//     */
//    @Override
//    public List<ProjectCost> selectRateTrend()
//    {
//
//
//        return
//    }
    /**
     * 获取利润分析数据
     *
     * @param
     * @return 项目成本
     */
    @Override
    public AjaxResult selectRate(){

        AjaxResult ajaxResult = new AjaxResult();
        FinInvoice finInvoice = new FinInvoice();
        finInvoice.setStatus("3");
        ajaxResult.put("invoiceList", finInvoiceMapper.selectFinInvoiceList(finInvoice));
        ajaxResult.put("finWagesList", finWagesMapper.selectFinWagesList(new FinWages()));


        return ajaxResult;
    }


}
