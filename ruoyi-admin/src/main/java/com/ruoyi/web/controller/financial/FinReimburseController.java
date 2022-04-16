package com.ruoyi.web.controller.financial;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.financial.domain.FinInvoice;
import com.ruoyi.financial.service.IFinInvoiceService;
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
import com.ruoyi.financial.domain.FinReimburse;
import com.ruoyi.financial.service.IFinReimburseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private IFinInvoiceService finInvoiceService;

    @Autowired
    private ServerConfig serverConfig;

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

    /**
     * 报销发票上传请求
     */
    @Log(title = "报销发票", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadInvoice(MultipartFile file, @PathVariable("reimburseId") Long reimburseId) throws Exception
    {
        try
        {
            FinReimburse finReimburse = finReimburseService.selectFinReimburseByReimburseId(reimburseId);
            if(finReimburse != null ){
                if(Integer.parseInt(finReimburse.getStatus()) >= 2 ){
                    return AjaxResult.error("报销单号:("+finReimburse.getReimburseNumber()+")所在状态不能上传发票");
                }
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getInvoicePath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            //添加发票文件信息到数据库
            finReimburseService.addInvoice(reimburseId, fileName);
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
