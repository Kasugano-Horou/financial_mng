package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;


import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysPostService;
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
import com.ruoyi.common.core.domain.entity.SysEmp;
import com.ruoyi.system.service.ISysEmpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 员工Controller
 *
 * @author horou
 * @date 2022-03-11
 */
@RestController
@RequestMapping("/system/emp")
public class SysEmpController extends BaseController
{
    @Autowired
    private ISysEmpService sysEmpService;

    @Autowired
    private ISysPostService postService;

    /**
     * 查询员工列表
     */
    @PreAuthorize("@ss.hasPermi('system:emp:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEmp sysEmp)
    {
        startPage();
        System.out.println("EMP:"+sysEmp.toString());
        List<SysEmp> list = sysEmpService.selectEmpList(sysEmp);
        return getDataTable(list);
    }

    /**
     * 导出员工列表
     */
    @PreAuthorize("@ss.hasPermi('system:emp:export')")
    @Log(title = "员工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEmp sysEmp)
    {
        List<SysEmp> list = sysEmpService.selectEmpList(sysEmp);
        ExcelUtil<SysEmp> util = new ExcelUtil<SysEmp>(SysEmp.class);
        util.exportExcel(response, list, "员工数据");
    }

    @Log(title = "员工管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:emp:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysEmp> util = new ExcelUtil<SysEmp>(SysEmp.class);
        List<SysEmp> empList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = sysEmpService.importEmp(empList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysEmp> util = new ExcelUtil<SysEmp>(SysEmp.class);
        util.importTemplateExcel(response, "员工数据");
    }

    /**
     * 获取员工详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:emp:query')")
    @GetMapping(value = { "/", "/{empId}" })
    public AjaxResult getInfo(@PathVariable(value = "empId", required = false) Long empId)
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(empId))
        {
            SysEmp sysEmp = sysEmpService.selectEmpByEmpId(empId);
            ajax.put(AjaxResult.DATA_TAG, sysEmp);
            ajax.put("postIds", postService.selectPostListByEmpId(empId));
        }
        return ajax;
    }

    /**
     * 新增员工
     */
    @PreAuthorize("@ss.hasPermi('system:emp:add')")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEmp sysEmp)
    {
        return toAjax(sysEmpService.insertEmp(sysEmp));
    }

    /**
     * 修改员工
     */
    @PreAuthorize("@ss.hasPermi('system:emp:edit')")
    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEmp sysEmp)
    {
        return toAjax(sysEmpService.updateEmp(sysEmp));
    }

    /**
     * 删除员工
     */
    @PreAuthorize("@ss.hasPermi('system:emp:remove')")
    @Log(title = "员工", businessType = BusinessType.DELETE)
    @DeleteMapping("/{empIds}")
    public AjaxResult remove(@PathVariable Long[] empIds)
    {
        return toAjax(sysEmpService.deleteEmpByEmpIds(empIds));
    }
}