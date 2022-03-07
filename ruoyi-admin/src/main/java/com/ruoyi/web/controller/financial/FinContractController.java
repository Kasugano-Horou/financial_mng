package com.ruoyi.web.controller.financial;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.FinContract;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.financial.service.IFinContractService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同信息
 * 
 * @author horou
 */
@RestController
@RequestMapping("/financial/contract")
public class FinContractController extends BaseController
{
    @Autowired
    private IFinContractService contractService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    /**
     * 获取合同列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FinContract contract)
    {
        startPage();
        List<FinContract> list = contractService.selectContractList(contract);
        return getDataTable(list);
    }

    /**
     * 导出合同
     */
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinContract contract)
    {
        List<FinContract> list = contractService.selectContractList(contract);
        ExcelUtil<FinContract> util = new ExcelUtil<FinContract>(FinContract.class);
        util.exportExcel(response, list, "合同数据");
    }

    /**
     * 导入合同
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        return null;
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<FinContract> util = new ExcelUtil<FinContract>(FinContract.class);
        util.importTemplateExcel(response, "合同数据");
    }

    /**
     * 根据合同编号获取详细信息
     */
    @GetMapping(value = { "/", "/{contractId}" })
    public AjaxResult getInfo(@PathVariable(value = "contractId", required = false) Long contractId)
    {
        return null;
    }

    /**
     * 新增合同
     */
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinContract contract)
    {
        return null;
    }

    /**
     * 修改合同
     */
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinContract contract)
    {
        return null;
    }

    /**
     * 删除合同
     */
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable Long[] contractIds)
    {
        return null;
    }

    /**
     * 状态修改
     */
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinContract contract)
    {
        return null;
    }

    /**
     * 根据合同编号获取授权角色
     */
    @GetMapping("/authRole/{contractId}")
    public AjaxResult authRole(@PathVariable("contractId") Long contractId)
    {
        return null;
    }

}
