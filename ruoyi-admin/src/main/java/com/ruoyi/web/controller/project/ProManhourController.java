package com.ruoyi.web.controller.project;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.domain.ProProject;
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
import com.ruoyi.project.domain.ProManhour;
import com.ruoyi.project.service.IProManhourService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工时Controller
 * 
 * @author horou
 * @date 2022-03-16
 */
@RestController
@RequestMapping("/project/manhour")
public class ProManhourController extends BaseController
{
    @Autowired
    private IProManhourService proManhourService;

    /**
     * 查询工时列表
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProManhour proManhour)
    {
        startPage();
        List<ProManhour> list = proManhourService.selectProManhourList(proManhour);
        return getDataTable(list);
    }

    /**
     * 根据项目ID查询工时列表
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:list')")
    @GetMapping("/list/{projectId}")
    public TableDataInfo list(@PathVariable("projectId") Long projectId)
    {
        startPage();
        System.out.println("projectId"+projectId);
        ProManhour proManhour = new ProManhour();
        proManhour.setProjectId(projectId);
        List<ProManhour> list = proManhourService.selectProManhourList(proManhour);
        return getDataTable(list);
    }

    /**
     * 导出工时列表
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:export')")
    @Log(title = "工时", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProManhour proManhour)
    {
        List<ProManhour> list = proManhourService.selectProManhourList(proManhour);
        ExcelUtil<ProManhour> util = new ExcelUtil<ProManhour>(ProManhour.class);
        util.exportExcel(response, list, "工时数据");
    }

    /**
     * 获取工时详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:query')")
    @GetMapping(value = "/{manhourId}")
    public AjaxResult getInfo(@PathVariable("manhourId") Long manhourId)
    {
        return AjaxResult.success(proManhourService.selectProManhourByManhourId(manhourId));
    }

    /**
     * 新增工时
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:add')")
    @Log(title = "工时", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProManhour proManhour)
    {
        proManhour.setCreateBy(getUsername());
        return toAjax(proManhourService.insertProManhour(proManhour));
    }

    /**
     * 修改工时
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:edit')")
    @Log(title = "工时", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProManhour proManhour)
    {
        proManhour.setUpdateBy(getUsername());
        return toAjax(proManhourService.updateProManhour(proManhour));
    }

    /**
     * 删除工时
     */
    @PreAuthorize("@ss.hasPermi('project:manhour:remove')")
    @Log(title = "工时", businessType = BusinessType.DELETE)
	@DeleteMapping("/{manhourIds}")
    public AjaxResult remove(@PathVariable Long[] manhourIds)
    {
        return toAjax(proManhourService.deleteProManhourByManhourIds(manhourIds));
    }
}
