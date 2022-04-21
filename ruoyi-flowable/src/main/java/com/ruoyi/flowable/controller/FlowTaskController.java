package com.ruoyi.flowable.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.flowable.domain.dto.FlowTaskDto;
import com.ruoyi.flowable.domain.vo.FlowTaskVo;
import com.ruoyi.flowable.domain.vo.PageResponseVo;
import com.ruoyi.flowable.domain.vo.UserTaskVo;
import com.ruoyi.flowable.service.IFlowTaskService;
import com.yuweix.assist4j.core.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * <p>工作流任务管理<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Slf4j
@Api(tags = "工作流流程任务管理")
@RestController
public class FlowTaskController {
    @Autowired
    private IFlowTaskService flowTaskService;


    @ApiOperation(value = "我发起的流程", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/myProcess", method = GET)
    @ResponseBody
    public Response<Integer, PageResponseVo<FlowTaskDto>> queryMyProcessList(
            @RequestParam(value = "category", required = false) String category
            , @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        Long userId = sysUser.getUserId();

        int size = flowTaskService.queryProcessCount(String.valueOf(userId), category);
        List<FlowTaskDto> list = flowTaskService.queryProcessList(String.valueOf(userId), category, pageNo, pageSize);

        PageResponseVo<FlowTaskDto> pageVo = PageResponseVo.<FlowTaskDto>builder()
                .size(size)
                .list(list)
                .build();
        return new Response<>(HttpStatus.SUCCESS, "ok", pageVo);
    }

    @PreAuthorize("@ss.hasPermi('flowable:proc:list')")
    @ApiOperation(value = "全部流程", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/procList", method = GET)
    @ResponseBody
    public Response<Integer, PageResponseVo<FlowTaskDto>> queryProcessList(
            @RequestParam(value = "category", required = false) String category
            , @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        int size = flowTaskService.queryProcessCount(null, category);
        List<FlowTaskDto> list = flowTaskService.queryProcessList(null, category, pageNo, pageSize);

        PageResponseVo<FlowTaskDto> pageVo = PageResponseVo.<FlowTaskDto>builder()
                .size(size)
                .list(list)
                .build();
        return new Response<>(HttpStatus.SUCCESS, "ok", pageVo);
    }

    @ApiOperation(value = "取消申请", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/stopProcess", method = POST)
    @ResponseBody
    public Response<Integer, Void> stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.stopProcess(flowTaskVo.getInstanceId());
    }

    @ApiOperation(value = "撤回流程", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/revokeProcess", method = POST)
    @ResponseBody
    public AjaxResult revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.revokeProcess(flowTaskVo.getInstanceId());
    }

    @ApiOperation(value = "获取待办列表", notes = "......")
    @RequestMapping(value = "/flowable/task/todoList", method = GET)
    @ResponseBody
    public Response<Integer, PageResponseVo<FlowTaskDto>> queryTodoList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        int size = flowTaskService.queryTodoCount();
        List<FlowTaskDto> list = flowTaskService.queryTodoList(pageNo, pageSize);

        PageResponseVo<FlowTaskDto> pageVo = PageResponseVo.<FlowTaskDto>builder()
                .size(size)
                .list(list)
                .build();
        return new Response<>(HttpStatus.SUCCESS, "ok", pageVo);
    }

    @ApiOperation(value = "获取已办任务", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/finishedList", method = GET)
    @ResponseBody
    public AjaxResult finishedList(@ApiParam(value = "当前页码", required = true) @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                   @ApiParam(value = "每页条数", required = true) @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return flowTaskService.finishedList(pageNum, pageSize);
    }

    @ApiOperation(value = "流程历史流转记录", response = FlowTaskDto.class)
    @RequestMapping(value = "/flowable/task/flowRecord", method = GET)
    @ResponseBody
    public Response<Integer, List<FlowTaskDto>> getFlowRecords(@RequestParam(value = "procInsId", required = true) String procInsId) {
        return flowTaskService.getFlowRecords(procInsId);
    }

    @ApiOperation(value = "查询流程表单")
    @RequestMapping(value = "/flowable/task/form", method = GET)
    @ResponseBody
    public Response<Integer, Map<String, Object>> getFlowForm(@RequestParam(value = "deployId", required = true) String deployId) {
        return flowTaskService.getFlowForm(deployId);
    }

    @ApiOperation(value = "获取流程变量")
    @RequestMapping(value = "/flowable/task/processVariables/{taskId}", method = GET)
    @ResponseBody
    public Response<Integer, Map<String, Object>> processVariables(@ApiParam(value = "流程任务Id")  @PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @ApiOperation(value = "检查是否为起始节点", notes = "......")
    @RequestMapping(value = "/flowable/task/isStartNode", method = GET)
    @ResponseBody
    public Response<Integer, Boolean> checkIfProcessTaskStartNode(@RequestParam(value = "taskId", required = true) String taskId) {
        return flowTaskService.checkIfProcessTaskStartNode(taskId);
    }

    @ApiOperation(value = "重启流程实例")
    @RequestMapping(value = "/flowable/task/restart", method = POST)
    @ResponseBody
    public Response<Integer, Void> restartTask(@RequestParam(value = "taskId", required = true) String taskId,
                                               @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowTaskService.restartTask(taskId, variables);
    }

    @ApiOperation(value = "审批任务")
    @RequestMapping(value = "/flowable/task/complete", method = POST)
    @ResponseBody
    public Response<Integer, Void> complete(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.complete(flowTaskVo.getTaskId(), flowTaskVo.getInstanceId()
                , flowTaskVo.getComment(), flowTaskVo.getValues());
    }

    @ApiOperation(value = "驳回任务")
    @RequestMapping(value = "/flowable/task/reject", method = POST)
    @ResponseBody
    public AjaxResult taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo.getTaskId(), flowTaskVo.getComment());
        return AjaxResult.success();
    }

    @ApiOperation(value = "退回任务")
    @RequestMapping(value = "/flowable/task/return", method = POST)
    @ResponseBody
    public Response<Integer, Void> taskReturn(@RequestParam(value = "taskId", required = true) String taskId
            , @RequestParam(value = "targetKey", required = true) String targetKey
            , @RequestParam(value = "comment", required = true) String comment) {
        flowTaskService.taskReturn(taskId, targetKey, comment);
        return Response.create(HttpStatus.SUCCESS, "ok");
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @RequestMapping(value = "/flowable/task/returnList", method = GET)
    @ResponseBody
    public Response<Integer, List<UserTaskVo>> findReturnTaskList(@RequestParam(value = "taskId", required = true) String taskId) {
        return flowTaskService.findReturnTaskList(taskId);
    }

    @ApiOperation(value = "获取可回退的节点(起始节点)")
    @RequestMapping(value = "/flowable/task/returnTask", method = GET)
    @ResponseBody
    public Response<Integer, UserTaskVo> findReturnTask(@RequestParam(value = "taskId", required = true) String taskId) {
        return flowTaskService.findReturnTask(taskId);
    }

    @ApiOperation(value = "删除任务")
    @RequestMapping(value = "/flowable/task/delete", method = DELETE)
    @ResponseBody
    public AjaxResult delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo.getTaskId(), flowTaskVo.getComment());
        return AjaxResult.success();
    }

    @ApiOperation(value = "认领/签收任务")
    @RequestMapping(value = "/flowable/task/claim", method = POST)
    @ResponseBody
    public AjaxResult claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo.getTaskId(), flowTaskVo.getUserId());
        return AjaxResult.success();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @RequestMapping(value = "/flowable/task/unclaim", method = POST)
    @ResponseBody
    public AjaxResult unclaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unclaim(flowTaskVo.getTaskId());
        return AjaxResult.success();
    }

    @ApiOperation(value = "委派任务")
    @RequestMapping(value = "/flowable/task/delegate", method = POST)
    @ResponseBody
    public AjaxResult delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo.getTaskId(), flowTaskVo.getAssignee());
        return AjaxResult.success();
    }

    @ApiOperation(value = "转办任务")
    @RequestMapping(value = "/flowable/task/assign", method = POST)
    @ResponseBody
    public AjaxResult assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo.getTaskId(), flowTaskVo.getUserId());
        return AjaxResult.success();
    }

    @ApiOperation(value = "获取下一节点")
    @RequestMapping(value = "/flowable/task/nextFlowNode", method = POST)
    @ResponseBody
    public AjaxResult getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo.getTaskId());
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/flowable/task/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
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

    /**
     * 获取流程执行过程
     *
     * @param procInsId 任务ID
     */
    @RequestMapping(value = "/flowable/task/flowViewer/{procInsId}", method = GET)
    @ResponseBody
    public AjaxResult getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }
}


//
///**
// * <p>工作流任务管理<p>
// *
// * @author XuanXuan
// * @date 2021-04-03
// */
//@Slf4j
//@Api(tags = "工作流流程任务管理")
//@RestController
//@RequestMapping("/flowable/task")
//public class FlowTaskController {
//
//    @Autowired
//    private IFlowTaskService flowTaskService;
//
//    @ApiOperation(value = "我发起的流程", response = FlowTaskDto.class)
//    @GetMapping(value = "/myProcess")
//    public AjaxResult myProcess(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
//                             @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
//        return flowTaskService.myProcess(pageNum, pageSize);
//    }
//
//    @ApiOperation(value = "取消申请", response = FlowTaskDto.class)
//    @PostMapping(value = "/stopProcess")
//    public AjaxResult stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
//        return flowTaskService.stopProcess(flowTaskVo);
//    }
//
//    @ApiOperation(value = "撤回流程", response = FlowTaskDto.class)
//    @PostMapping(value = "/revokeProcess")
//    public AjaxResult revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
//        return flowTaskService.revokeProcess(flowTaskVo);
//    }
//
//    @ApiOperation(value = "获取待办列表", response = FlowTaskDto.class)
//    @GetMapping(value = "/todoList")
//    public AjaxResult todoList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
//                               @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
//        return flowTaskService.todoList(pageNum, pageSize);
//    }
//
//    @ApiOperation(value = "获取已办任务", response = FlowTaskDto.class)
//    @GetMapping(value = "/finishedList")
//    public AjaxResult finishedList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
//                                   @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
//        return flowTaskService.finishedList(pageNum, pageSize);
//    }
//
//
//    @ApiOperation(value = "流程历史流转记录", response = FlowTaskDto.class)
//    @GetMapping(value = "/flowRecord")
//    public AjaxResult flowRecord(String procInsId,String deployId) {
//        return flowTaskService.flowRecord(procInsId,deployId);
//    }
//
//    @ApiOperation(value = "获取流程变量", response = FlowTaskDto.class)
//    @GetMapping(value = "/processVariables/{taskId}")
//    public AjaxResult processVariables(@ApiParam(value = "流程任务Id")  @PathVariable(value = "taskId") String taskId) {
//        return flowTaskService.processVariables(taskId);
//    }
//
//    @ApiOperation(value = "审批任务")
//    @PostMapping(value = "/complete")
//    public AjaxResult complete(@RequestBody FlowTaskVo flowTaskVo) {
//       return flowTaskService.complete(flowTaskVo);
//    }
//
//    @ApiOperation(value = "驳回任务")
//    @PostMapping(value = "/reject")
//    public AjaxResult taskReject(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.taskReject(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "退回任务")
//    @PostMapping(value = "/return")
//    public AjaxResult taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.taskReturn(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "获取所有可回退的节点")
//    @PostMapping(value = "/returnList")
//    public AjaxResult findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
//        return flowTaskService.findReturnTaskList(flowTaskVo);
//    }
//
//    @ApiOperation(value = "删除任务")
//    @DeleteMapping(value = "/delete")
//    public AjaxResult delete(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.deleteTask(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "认领/签收任务")
//    @PostMapping(value = "/claim")
//    public AjaxResult claim(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.claim(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "取消认领/签收任务")
//    @PostMapping(value = "/unClaim")
//    public AjaxResult unClaim(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.unClaim(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "委派任务")
//    @PostMapping(value = "/delegate")
//    public AjaxResult delegate(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.delegateTask(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "转办任务")
//    @PostMapping(value = "/assign")
//    public AjaxResult assign(@RequestBody FlowTaskVo flowTaskVo) {
//        flowTaskService.assignTask(flowTaskVo);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation(value = "获取下一节点")
//    @PostMapping(value = "/nextFlowNode")
//    public AjaxResult getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
//        return flowTaskService.getNextFlowNode(flowTaskVo);
//    }
//
//    /**
//     * 生成流程图
//     *
//     * @param processId 任务ID
//     */
//    @RequestMapping("/diagram/{processId}")
//    public void genProcessDiagram(HttpServletResponse response,
//                                  @PathVariable("processId") String processId) {
//        InputStream inputStream =  flowTaskService.diagram(processId);
//        OutputStream os = null;
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(inputStream);
//            response.setContentType("image/png");
//            os = response.getOutputStream();
//            if (image != null) {
//                ImageIO.write(image, "png", os);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (os != null) {
//                    os.flush();
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 生成流程图
//     *
//     * @param procInsId 任务ID
//     */
//    @RequestMapping("/flowViewer/{procInsId}")
//    public AjaxResult getFlowViewer(@PathVariable("procInsId") String procInsId) {
//        return flowTaskService.getFlowViewer(procInsId);
//    }
//}
