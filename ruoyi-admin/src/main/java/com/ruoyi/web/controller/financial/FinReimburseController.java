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
import com.ruoyi.financial.domain.FinReimburse;
import com.ruoyi.financial.service.IFinReimburseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报销Controller
 *
 * @author horou
 * @date 2022-03-20
 */
@RestController
@RequestMapping("/financial/reimburse")
public class FinReimburseController extends BaseController
{
    @Autowired
    private IFinReimburseService finReimburseService;

    /**
     * 查询报销列表
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinReimburse finReimburse)
    {
        startPage();
        System.out.println(finReimburse);
        List<FinReimburse> list = finReimburseService.selectFinReimburseList(finReimburse);
        return getDataTable(list);
    }

    /**
     * 导出报销列表
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:export')")
    @Log(title = "报销", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinReimburse finReimburse)
    {
        List<FinReimburse> list = finReimburseService.selectFinReimburseList(finReimburse);
        ExcelUtil<FinReimburse> util = new ExcelUtil<FinReimburse>(FinReimburse.class);
        util.exportExcel(response, list, "报销数据");
    }

    /**
     * 获取报销详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:query')")
    @GetMapping(value = "/{reimburseId}")
    public AjaxResult getInfo(@PathVariable("reimburseId") Long reimburseId)
    {
        return AjaxResult.success(finReimburseService.selectFinReimburseByReimburseId(reimburseId));
    }

    /**
     * 新增报销
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:add')")
    @Log(title = "报销", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinReimburse finReimburse)
    {
        return toAjax(finReimburseService.insertFinReimburse(finReimburse));
    }

    /**
     * 修改报销
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:edit')")
    @Log(title = "报销", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinReimburse finReimburse)
    {
        return toAjax(finReimburseService.updateFinReimburse(finReimburse));
    }

    /**
     * 删除报销
     */
    @PreAuthorize("@ss.hasPermi('financial:reimburse:remove')")
    @Log(title = "报销", businessType = BusinessType.DELETE)
    @DeleteMapping("/{reimburseIds}")
    public AjaxResult remove(@PathVariable Long[] reimburseIds)
    {
        return toAjax(finReimburseService.deleteFinReimburseByReimburseIds(reimburseIds));
    }
}
