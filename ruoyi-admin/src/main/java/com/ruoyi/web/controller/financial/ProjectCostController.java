package com.ruoyi.web.controller.financial;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.financial.domain.ProjectCost;
import com.ruoyi.financial.service.IProjectCostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目成本Controller
 * 
 * @author horou
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/financial/projectCost")
public class ProjectCostController extends BaseController
{
    @Autowired
    private IProjectCostService projectCostService;

    /**
     * 核算项目成本
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:account')")
    @Log(title = "项目成本", businessType = BusinessType.OTHER)
    @PutMapping("/account/{projectIds}")
    public AjaxResult account(@PathVariable Long[] projectIds)
    {
        return toAjax(projectCostService.accountProjectCostByProjectIds(projectIds));
    }

    /**
     * 获取利润分析数据
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:account')")
    @Log(title = "项目成本", businessType = BusinessType.OTHER)
    @GetMapping("/rate")
    public AjaxResult rate()
    {
        AjaxResult ajaxResult = projectCostService.selectRate();
        return ajaxResult;
    }


    /**
     * 查询项目成本列表
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectCost projectCost)
    {
        startPage();
        List<ProjectCost> list = projectCostService.selectProjectCostList(projectCost);
        return getDataTable(list);
    }

    /**
     * 导出项目成本列表
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:export')")
    @Log(title = "项目成本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectCost projectCost)
    {
        List<ProjectCost> list = projectCostService.selectProjectCostList(projectCost);
        ExcelUtil<ProjectCost> util = new ExcelUtil<ProjectCost>(ProjectCost.class);
        util.exportExcel(response, list, "项目成本数据");
    }

    /**
     * 获取项目成本详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:query')")
    @GetMapping(value = "/{projectCostId}")
    public AjaxResult getInfo(@PathVariable("projectCostId") Long projectCostId)
    {
        return AjaxResult.success(projectCostService.selectProjectCostByProjectCostId(projectCostId));
    }

    /**
     * 新增项目成本
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:add')")
    @Log(title = "项目成本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectCost projectCost)
    {
        return toAjax(projectCostService.insertProjectCost(projectCost));
    }

    /**
     * 修改项目成本
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:edit')")
    @Log(title = "项目成本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectCost projectCost)
    {
        return toAjax(projectCostService.updateProjectCost(projectCost));
    }

    /**
     * 删除项目成本
     */
    @PreAuthorize("@ss.hasPermi('financial:projectCost:remove')")
    @Log(title = "项目成本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectCostIds}")
    public AjaxResult remove(@PathVariable Long[] projectCostIds)
    {
        return toAjax(projectCostService.deleteProjectCostByProjectCostIds(projectCostIds));
    }
}
