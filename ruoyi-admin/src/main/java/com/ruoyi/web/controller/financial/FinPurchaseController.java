package com.ruoyi.web.controller.financial;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.financial.domain.FinReimburse;
import org.apache.ibatis.annotations.Param;
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
import com.ruoyi.financial.domain.FinPurchase;
import com.ruoyi.financial.service.IFinPurchaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 采购订单Controller
 * 
 * @author horou
 * @date 2022-03-23
 */
@RestController
@RequestMapping("/financial/purchase")
public class FinPurchaseController extends BaseController
{
    @Autowired
    private IFinPurchaseService finPurchaseService;

    /**
     * 查询采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinPurchase finPurchase)
    {
        startPage();
        List<FinPurchase> list = finPurchaseService.selectFinPurchaseList(finPurchase);
        return getDataTable(list);
    }

    /**
     * 导出采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:export')")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinPurchase finPurchase)
    {
        List<FinPurchase> list = finPurchaseService.selectFinPurchaseList(finPurchase);
        ExcelUtil<FinPurchase> util = new ExcelUtil<FinPurchase>(FinPurchase.class);
        util.exportExcel(response, list, "采购订单数据");
    }

    /**
     * 获取采购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:query')")
    @GetMapping(value = "/{purchaseId}")
    public AjaxResult getInfo(@PathVariable("purchaseId") Long purchaseId)
    {
        return AjaxResult.success(finPurchaseService.selectFinPurchaseByPurchaseId(purchaseId));
    }

    /**
     * 新增采购订单
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:add')")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinPurchase finPurchase)
    {
        finPurchase.setCreateBy(getUsername());
        return toAjax(finPurchaseService.insertFinPurchase(finPurchase));
    }

    /**
     * 修改采购订单
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:edit')")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinPurchase finPurchase)
    {
        return toAjax(finPurchaseService.updateFinPurchase(finPurchase));
    }

    /**
     * 删除采购订单
     */
    @PreAuthorize("@ss.hasPermi('financial:purchase:remove')")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{purchaseIds}")
    public AjaxResult remove(@PathVariable Long[] purchaseIds)
    {
        return toAjax(finPurchaseService.deleteFinPurchaseByPurchaseIds(purchaseIds));
    }

    /**
     * 状态修改
     */
    //@PreAuthorize("@ss.hasPermi('financial:purchase:edit')")
    //@Log(title = "采购管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinPurchase finPurchase)
    {
        System.out.println("FinPurchase changeStatus");
        System.out.println(finPurchase);
        return toAjax(finPurchaseService.updateFinPurchase(finPurchase));
    }

    /**
     * 采购发票上传请求
     */
    @Log(title = "采购发票", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadInvoice(MultipartFile file, @Param("purchaseId") Long purchaseId) throws Exception
    {
        try
        {
            FinPurchase finPurchase = finPurchaseService.selectFinPurchaseByPurchaseId(purchaseId);
            if(finPurchase != null ){
                if(Integer.parseInt(finPurchase.getStatus()) >= 2 ){
                    return AjaxResult.error("采购ID:("+finPurchase.getPurchaseId()+")所在状态不能上传发票");
                }
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getInvoicePath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            //添加发票文件信息到数据库
            finPurchaseService.addInvoice(purchaseId, fileName);
            AjaxResult ajax = AjaxResult.success();
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
