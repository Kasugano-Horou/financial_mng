package com.ruoyi.web.controller.financial;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.financial.domain.FinReimburse;
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
import com.ruoyi.financial.domain.FinContract;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.financial.service.IFinContractService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 合同管理Controller
 *
 * @author horou
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/financial/contract")
public class FinContractController extends BaseController
{
    @Autowired
    private IFinContractService finContractService;

    /**
     * 查询合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinContract finContract)
    {
        startPage();
        List<FinContract> list = finContractService.selectFinContractList(finContract);
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:export')")
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinContract finContract)
    {
        List<FinContract> list = finContractService.selectFinContractList(finContract);
        ExcelUtil<FinContract> util = new ExcelUtil<FinContract>(FinContract.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    @Log(title = "合同管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('financial:contract:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<FinContract> util = new ExcelUtil<FinContract>(FinContract.class);
        List<FinContract> finContractList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = finContractService.importFinContract(finContractList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<FinContract> util = new ExcelUtil<FinContract>(FinContract.class);
        util.importTemplateExcel(response, "合同数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:query')")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") Long contractId)
    {
        return AjaxResult.success(finContractService.selectFinContractByContractId(contractId));
    }

    /**
     * 新增合同管理
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinContract finContract)
    {
        if (UserConstants.NOT_UNIQUE.equals(finContractService.checkFnContractNumberUnique(finContract)))
        {
            return AjaxResult.error("新增角色'" + finContract.getContractNumber() + "'失败，合同编号已存在");
        }
        finContract.setCreateBy(getUsername());
        return toAjax(finContractService.insertFinContract(finContract));
    }

    /**
     * 修改合同管理
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:edit')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinContract finContract)
    {
        return toAjax(finContractService.updateFinContract(finContract));
    }

    /**
     * 删除合同管理
     */
    @PreAuthorize("@ss.hasPermi('financial:contract:remove')")
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable Long[] contractIds)
    {
        return toAjax(finContractService.deleteFinContractByContractIds(contractIds));
    }

    /**
     * 状态修改
     */
    //@PreAuthorize("@ss.hasPermi('financial:contract:edit')")
    //@Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinContract finContract)
    {
        System.out.println("changeStatus changeStatus");
        System.out.println(finContract);
        //finContract.setStatus("3");
        return toAjax(finContractService.updateFinContract(finContract));
    }

    /**
     * 状态修改
     */
    //@PreAuthorize("@ss.hasPermi('financial:contract:edit')")
    //@Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @GetMapping("/test")
    public String test()
    {
        System.out.println("changeStatus changeStatus");
        System.out.println();
        //finContract.setStatus("3");
        return "wwww";
    }

    /**
     * 合同文件上传
     */
    @Log(title = "合同文件上传", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadInvoice(MultipartFile file, Long contractId) throws Exception
    {
        try
        {
            System.out.println("contractId:" + contractId);
            FinContract finContract = finContractService.selectFinContractByContractId(contractId);
            if(finContract != null ){
                if(Integer.parseInt(finContract.getStatus()) >= 2 ){
                    return AjaxResult.error("合同:("+finContract.getContractName()+")所在状态不能上传合同文件");
                }
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getContractPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            //添加合同文件信息到数据库
            int a = finContractService.addContractFile(contractId, fileName);

            AjaxResult ajax;
            if(a>0){
                ajax = AjaxResult.success();
            }else {
                ajax = AjaxResult.error();
            }

            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
