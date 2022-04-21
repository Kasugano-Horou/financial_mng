package com.ruoyi.flowable.controller;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.flowable.domain.SysDeployForm;
import com.ruoyi.flowable.domain.SysForm;
import com.ruoyi.flowable.service.ISysDeployFormService;
import com.ruoyi.system.domain.vo.OptionVo;
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
import com.ruoyi.flowable.service.ISysFormService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;



/**
 * 流程表单Controller
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@RestController
public class SysFormController extends BaseController {
    @Autowired
    private ISysFormService SysFormService;

    @Autowired
    private ISysDeployFormService sysDeployFormService;

    /**
     * 查询流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:list')")
    @GetMapping("/flowable/form/list")
    public TableDataInfo list(SysForm sysForm) {
        startPage();
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        return getDataTable(list);
    }

    /**
     * 下拉框默认下拉列表
     */
    @GetMapping("/flowable/form/option/default-list")
    public AjaxResult getDefaultOptionList() {
        List<OptionVo> optionList = new ArrayList<>();
        optionList.add(OptionVo.builder().value("1").label("选项一").build());
        optionList.add(OptionVo.builder().value("2").label("选项二").build());
        return AjaxResult.success(optionList);
    }

    /**
     * 导出流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:export')")
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @GetMapping("/flowable/form/export")
    public AjaxResult export(SysForm sysForm) {
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        ExcelUtil<SysForm> util = new ExcelUtil<SysForm>(SysForm.class);
        return util.exportExcel(list, "form");
    }

    /**
     * 获取流程表单详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:query')")
    @GetMapping(value = "/flowable/form/{formId}")
    public AjaxResult getInfo(@PathVariable("formId") Long formId) {
        return AjaxResult.success(SysFormService.selectSysFormById(formId));
    }

    /**
     * 新增流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:add')")
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping(value = "/flowable/form")
    public AjaxResult add(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.insertSysForm(sysForm));
    }

    /**
     * 修改流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:edit')")
    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/flowable/form")
    public AjaxResult edit(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.updateSysForm(sysForm));
    }

    /**
     * 删除流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:remove')")
    @Log(title = "流程表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/flowable/form/{formIds}")
    public AjaxResult remove(@PathVariable Long[] formIds) {
        return toAjax(SysFormService.deleteSysFormByIds(formIds));
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/flowable/form/addDeployForm")
    public AjaxResult addDeployForm(@RequestBody SysDeployForm sysDeployForm) {
        return toAjax(sysDeployFormService.insertSysDeployForm(sysDeployForm));
    }
}

//
///**
// * 流程表单Controller
// *
// * @author XuanXuan
// * @date 2021-04-03
// */
//@RestController
//@RequestMapping("/flowable/form")
//public class SysFormController extends BaseController {
//    @Autowired
//    private ISysFormService SysFormService;
//
//    @Autowired
//    private ISysDeployFormService sysDeployFormService;
//
//    /**
//     * 查询流程表单列表
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(SysForm sysForm) {
//        startPage();
//        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出流程表单列表
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:export')")
//    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(SysForm sysForm) {
//        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
//        ExcelUtil<SysForm> util = new ExcelUtil<SysForm>(SysForm.class);
//        return util.exportExcel(list, "form");
//    }
//
//    /**
//     * 获取流程表单详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:query')")
//    @GetMapping(value = "/{formId}")
//    public AjaxResult getInfo(@PathVariable("formId") Long formId) {
//        return AjaxResult.success(SysFormService.selectSysFormById(formId));
//    }
//
//    /**
//     * 新增流程表单
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:add')")
//    @Log(title = "流程表单", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SysForm sysForm) {
//        return toAjax(SysFormService.insertSysForm(sysForm));
//    }
//
//    /**
//     * 修改流程表单
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:edit')")
//    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SysForm sysForm) {
//        return toAjax(SysFormService.updateSysForm(sysForm));
//    }
//
//    /**
//     * 删除流程表单
//     */
//    @PreAuthorize("@ss.hasPermi('flowable:form:remove')")
//    @Log(title = "流程表单", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{formIds}")
//    public AjaxResult remove(@PathVariable Long[] formIds) {
//        return toAjax(SysFormService.deleteSysFormByIds(formIds));
//    }
//
//
//    /**
//     * 挂载流程表单
//     */
//    @Log(title = "流程表单", businessType = BusinessType.INSERT)
//    @PostMapping("/addDeployForm")
//    public AjaxResult addDeployForm(@RequestBody SysDeployForm sysDeployForm) {
//        return toAjax(sysDeployFormService.insertSysDeployForm(sysDeployForm));
//    }
//}
