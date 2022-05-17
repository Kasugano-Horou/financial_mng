package com.ruoyi.web.controller.financial;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.financial.domain.FinPurchase;
import com.ruoyi.framework.config.ServerConfig;
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
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.financial.service.IFinInvoiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 发票Controller
 * 
 * @author horou
 * @date 2022-03-23
 */
@RestController
@RequestMapping("/financial/invoice")
public class FinInvoiceController extends BaseController
{

    @Autowired
    private IFinInvoiceService finInvoiceService;


    /**
     * 查询发票列表
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinInvoice finInvoice)
    {
        startPage();
        List<FinInvoice> list = finInvoiceService.selectFinInvoiceList(finInvoice);
        return getDataTable(list);
    }

    /**
     * 导出发票列表
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:export')")
    @Log(title = "发票", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinInvoice finInvoice)
    {
        List<FinInvoice> list = finInvoiceService.selectFinInvoiceList(finInvoice);
        ExcelUtil<FinInvoice> util = new ExcelUtil<FinInvoice>(FinInvoice.class);
        util.exportExcel(response, list, "发票数据");
    }

    /**
     * 获取发票详细信息
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:query')")
    @GetMapping(value = "/{invoiceId}")
    public AjaxResult getInfo(@PathVariable("invoiceId") Long invoiceId)
    {
        return AjaxResult.success(finInvoiceService.selectFinInvoiceByInvoiceId(invoiceId));
    }

    /**
     * 新增发票
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:add')")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinInvoice finInvoice)
    {
        finInvoice.setCreateBy(getUsername());
        return toAjax(finInvoiceService.insertFinInvoice(finInvoice));
    }

    /**
     * 修改发票
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:edit')")
    @Log(title = "发票", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinInvoice finInvoice)
    {
        finInvoice.setUpdateBy(getUsername());
        return toAjax(finInvoiceService.updateFinInvoice(finInvoice));
    }

    /**
     * 删除发票
     */
    @PreAuthorize("@ss.hasPermi('financial:invoice:remove')")
    @Log(title = "发票", businessType = BusinessType.DELETE)
	@DeleteMapping("/{invoiceIds}")
    public AjaxResult remove(@PathVariable Long[] invoiceIds)
    {
        return toAjax(finInvoiceService.deleteFinInvoiceByInvoiceIds(invoiceIds));
    }

    /**
     * 发票图片上传请求
     */
    @Log(title = "发票管理", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadInvoice(MultipartFile file, @Param("invoiceId") Long invoiceId) throws Exception
    {
        try
        {
            FinInvoice finInvoice = finInvoiceService.selectFinInvoiceByInvoiceId(invoiceId);
            if(finInvoice != null ){
                if(Integer.parseInt(finInvoice.getStatus()) > 2 ){
                    return AjaxResult.error("发票ID:("+finInvoice.getInvoiceCode()+")所在状态不能上传发票");
                }
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getInvoicePath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            //添加发票文件信息到数据库
            finInvoiceService.addInvoice(finInvoice, fileName);
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
