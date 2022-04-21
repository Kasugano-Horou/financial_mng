package com.ruoyi.flowable.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;

import com.ruoyi.flowable.domain.FlowProcDefDto;
import com.ruoyi.flowable.domain.dto.FlowSaveXmlVo;
import com.ruoyi.flowable.domain.vo.PageResponseVo;
import com.ruoyi.flowable.service.IFlowDefinitionService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.yuweix.assist4j.core.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * <p>
 * 工作流程定义
 * </p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Slf4j
@Api(tags = "流程定义")
@RestController
public class FlowDefinitionController {
    @Autowired
    private IFlowDefinitionService flowDefinitionService;
    @Autowired
    private ISysUserService userService;
    @Resource
    private ISysRoleService sysRoleService;


    @ApiOperation(value = "流程定义列表", response = FlowProcDefDto.class)
    @RequestMapping(value = "/flowable/definition/list", method = GET)
    @ResponseBody
    public Response<Integer, PageResponseVo<FlowProcDefDto>> queryProcDefList(
            @RequestParam(value = "category", required = false) String category
            , @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        int size = flowDefinitionService.queryProcDefCount(category);
        List<FlowProcDefDto> list = flowDefinitionService.queryProcDefList(category, pageNo, pageSize);

        PageResponseVo<FlowProcDefDto> pageVo = PageResponseVo.<FlowProcDefDto>builder()
                .size(size)
                .list(list)
                .build();
        return new Response<>(HttpStatus.SUCCESS, "ok", pageVo);
    }


    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
    @RequestMapping(value = "/flowable/definition/import", method = POST)
    @ResponseBody
    public AjaxResult importFile(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category,
                                 MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return AjaxResult.success(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return AjaxResult.success("导入成功");
    }


    @ApiOperation(value = "读取xml文件")
    @RequestMapping(value = "/flowable/definition/readXml/{deployId}", method = GET)
    @ResponseBody
    public AjaxResult readXml(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId) {
        try {
            return flowDefinitionService.readXml(deployId);
        } catch (Exception e) {
            return AjaxResult.error("加载xml文件异常");
        }

    }

    @ApiOperation(value = "读取图片文件")
    @RequestMapping(value = "/flowable/definition/readImage/{deployId}", method = GET)
    @ResponseBody
    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(flowDefinitionService.readImage(deployId));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ApiOperation(value = "保存流程设计器内的xml文件")
    @RequestMapping(value = "/flowable/definition/save", method = POST)
    @ResponseBody
    public Response<Integer, Void> save(@RequestParam(value = "name", required = true) String name
            , @RequestParam(value = "category", required = true) String category
            , @RequestParam(value = "xml", required = true) String xml) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("保存失败:", e);
            return Response.create(HttpStatus.ERROR, e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }
        return Response.create(HttpStatus.SUCCESS, "保存成功");
    }


    @ApiOperation(value = "根据流程定义ID启动流程实例")
    @RequestMapping(value = "/flowable/definition/start/{procDefId}", method = POST)
    @ResponseBody
    public Response<Integer, Void> start(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                                         @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowDefinitionService.startProcessInstanceById(procDefId, variables);
    }

    @ApiOperation(value = "激活或挂起流程定义")
    @RequestMapping(value = "/flowable/definition/updateState", method = PUT)
    @ResponseBody
    public AjaxResult updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('flowable:definition:remove')")
    @ApiOperation(value = "删除流程")
    @RequestMapping(value = "/flowable/definition/{deployIds}", method = DELETE)
    @ResponseBody
    public AjaxResult delete(@ApiParam(value = "流程部署ID", required = true) @PathVariable String[] deployIds) {
        flowDefinitionService.delete(deployIds);
        return AjaxResult.success();
    }

    @ApiOperation(value = "指定流程办理人员列表")
    @RequestMapping(value = "/flowable/definition/userList", method = GET)
    @ResponseBody
    public AjaxResult getUserList(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "指定流程办理组列表")
    @RequestMapping(value = "/flowable/definition/roleList", method = GET)
    @ResponseBody
    public AjaxResult getRoleList(SysRole role) {
        List<SysRole> list = sysRoleService.selectRoleList(role);
        return AjaxResult.success(list);
    }
}


//
///**
// * <p>
// * 工作流程定义
// * </p>
// *
// * @author XuanXuan
// * @date 2021-04-03
// */
//@Slf4j
//@Api(tags = "流程定义")
//@RestController
//@RequestMapping("/flowable/definition")
//public class FlowDefinitionController {
//
//    @Autowired
//    private IFlowDefinitionService flowDefinitionService;
//
//    @Autowired
//    private ISysUserService userService;
//
//    @Resource
//    private ISysRoleService sysRoleService;
//
//
//    @GetMapping(value = "/list")
//    @ApiOperation(value = "流程定义列表", response = FlowProcDefDto.class)
//    public AjaxResult list(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
//                           @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize,
//                           @ApiParam(value = "流程名称", required = false) @RequestParam(required = false) String name) {
//        return AjaxResult.success(flowDefinitionService.list(name,pageNum, pageSize));
//    }
//
//
//    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
//    @PostMapping("/import")
//    public AjaxResult importFile(@RequestParam(required = false) String name,
//                                 @RequestParam(required = false) String category,
//                                 MultipartFile file) {
//        InputStream in = null;
//        try {
//            in = file.getInputStream();
//            flowDefinitionService.importFile(name, category, in);
//        } catch (Exception e) {
//            log.error("导入失败:", e);
//            return AjaxResult.success(e.getMessage());
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                log.error("关闭输入流出错", e);
//            }
//        }
//
//        return AjaxResult.success("导入成功");
//    }
//
//
//    @ApiOperation(value = "读取xml文件")
//    @GetMapping("/readXml/{deployId}")
//    public AjaxResult readXml(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId) {
//        try {
//            return flowDefinitionService.readXml(deployId);
//        } catch (Exception e) {
//            return AjaxResult.error("加载xml文件异常");
//        }
//
//    }
//
//    @ApiOperation(value = "读取图片文件")
//    @GetMapping("/readImage/{deployId}")
//    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
//        OutputStream os = null;
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(flowDefinitionService.readImage(deployId));
//            response.setContentType("image/png");
//            os = response.getOutputStream();
//            if (image != null) {
//                ImageIO.write(image, "png", os);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) {
//                    os.flush();
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//    @ApiOperation(value = "保存流程设计器内的xml文件")
//    @PostMapping("/save")
//    public AjaxResult save(@RequestBody FlowSaveXmlVo vo) {
//        InputStream in = null;
//        try {
//            in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8));
//            flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
//        } catch (Exception e) {
//            log.error("导入失败:", e);
//            return AjaxResult.success(e.getMessage());
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                log.error("关闭输入流出错", e);
//            }
//        }
//
//        return AjaxResult.success("导入成功");
//    }
//
//
//    @ApiOperation(value = "根据流程定义id启动流程实例")
//    @PostMapping("/start/{procDefId}")
//    public AjaxResult start(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
//                            @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
//        return flowDefinitionService.startProcessInstanceById(procDefId, variables);
//
//    }
//
//    @ApiOperation(value = "激活或挂起流程定义")
//    @PutMapping(value = "/updateState")
//    public AjaxResult updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
//                                  @ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
//        flowDefinitionService.updateState(state, deployId);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "删除流程")
//    @DeleteMapping(value = "/delete")
//    public AjaxResult delete(@ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
//        flowDefinitionService.delete(deployId);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "指定流程办理人员列表")
//    @GetMapping("/userList")
//    public AjaxResult userList(SysUser user) {
//        List<SysUser> list = userService.selectUserList(user);
//        return AjaxResult.success(list);
//    }
//
//    @ApiOperation(value = "指定流程办理组列表")
//    @GetMapping("/roleList")
//    public AjaxResult roleList(SysRole role) {
//        List<SysRole> list = sysRoleService.selectRoleList(role);
//        return AjaxResult.success(list);
//    }
//
//}