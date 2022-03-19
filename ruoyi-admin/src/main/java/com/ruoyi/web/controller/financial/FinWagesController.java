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
import com.ruoyi.financial.domain.FinWages;
import com.ruoyi.financial.service.IFinWagesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工资Controller
 * 
 * @author horou
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/financial/wages")
public class FinWagesController extends BaseController
{
    @Autowired
    private IFinWagesService finWagesService;

    /**
     * 查询工资列表
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinWages finWages)
    {
        startPage();
        List<FinWages> list = finWagesService.selectFinWagesList(finWages);
        return getDataTable(list);
    }

    /**
     * 导出工资列表
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:export')")
    @Log(title = "工资", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinWages finWages)
    {
        List<FinWages> list = finWagesService.selectFinWagesList(finWages);
        ExcelUtil<FinWages> util = new ExcelUtil<FinWages>(FinWages.class);
        util.exportExcel(response, list, "工资数据");
    }

    /**
     * 获取工资详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:query')")
    @GetMapping(value = "/{wageId}")
    public AjaxResult getInfo(@PathVariable("wageId") Long wageId)
    {
        return AjaxResult.success(finWagesService.selectFinWagesByWageId(wageId));
    }

    /**
     * 新增工资
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:add')")
    @Log(title = "工资", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinWages finWages)
    {
        return toAjax(finWagesService.insertFinWages(finWages));
    }

    /**
     * 修改工资
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:edit')")
    @Log(title = "工资", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinWages finWages)
    {
        return toAjax(finWagesService.updateFinWages(finWages));
    }

    /**
     * 删除工资
     */
    @PreAuthorize("@ss.hasPermi('financial:wages:remove')")
    @Log(title = "工资", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wageIds}")
    public AjaxResult remove(@PathVariable Long[] wageIds)
    {
        return toAjax(finWagesService.deleteFinWagesByWageIds(wageIds));
    }
}
