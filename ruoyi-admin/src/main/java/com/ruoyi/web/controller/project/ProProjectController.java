package com.ruoyi.web.controller.project;

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
import com.ruoyi.project.domain.ProProject;
import com.ruoyi.project.service.IProProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目Controller
 *
 * @author horou
 * @date 2022-03-15
 */
@RestController
@RequestMapping("/project/projectInfo")
public class ProProjectController extends BaseController
{
    @Autowired
    private IProProjectService proProjectService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProProject proProject)
    {
        startPage();
        List<ProProject> list = proProjectService.selectProProjectList(proProject);
        return getDataTable(list);
    }

    /**
     * 查询项目表格数据
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:list')")
    @GetMapping("/table")
    public TableDataInfo tableData(ProProject proProject)
    {
        startPage();
        List<ProProject> list = proProjectService.selectProProjectTableData(proProject);
        return getDataTable(list);
    }



    /**
     * 导出项目列表
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:export')")
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProProject proProject)
    {
        List<ProProject> list = proProjectService.selectProProjectList(proProject);
        ExcelUtil<ProProject> util = new ExcelUtil<ProProject>(ProProject.class);
        util.exportExcel(response, list, "项目数据");
    }

    /**
     * 获取项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return AjaxResult.success(proProjectService.selectProProjectByProjectId(projectId));
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProProject proProject)
    {
        proProject.setCreateBy(getUsername());

        return toAjax(proProjectService.insertProProject(proProject));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProProject proProject)
    {
        proProject.setUpdateBy(getUsername());
        return toAjax(proProjectService.updateProProject(proProject));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('project:projectInfo:remove')")
    @Log(title = "项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(proProjectService.deleteProProjectByProjectIds(projectIds));
    }
}
