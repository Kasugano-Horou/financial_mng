import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";

// 查询待办任务列表
export function getTodoList(query) {
  return request({
    url: '/flowable/task/todoList',
    method: 'get',
    params: query
  })
}

// 检查是否为起始节点
export function checkIfProcessTaskStartNode(query) {
  return request({
    url: '/flowable/task/isStartNode',
    method: 'get',
    params: query
  })
}

// 重启流程实例
export function restartTask(taskId, data) {
  return request({
    url: '/flowable/task/restart',
    method: 'post',
    params: {taskId: taskId},
    data: data
  })
}

// 完成任务
export function complete(data) {
  return request({
    url: '/flowable/task/complete',
    method: 'post',
    data: data
  })
}

// 委派任务
export function delegate(data) {
  return request({
    url: '/flowable/task/delegate',
    method: 'post',
    data: data
  })
}

// 退回任务
export function returnTask(data) {
  return request({
    url: '/flowable/task/return',
    method: 'post',
    // headers: {
    //   'Content-Type': "application/x-www-form-urlencoded; charset=utf-8"
    // },
    data: data
  })
}

// 驳回任务
export function rejectTask(data) {
  return request({
    url: '/flowable/task/reject',
    method: 'post',
    data: data
  })
}

// 可退回任务列表
export function returnList(query) {
  return request({
    url: '/flowable/task/returnList',
    method: 'get',
    params: query
  })
}
// 获取可回退的节点(起始节点)
export function getReturnTask(query) {
  return request({
    url: '/flowable/task/returnTask',
    method: 'get',
    params: query
  })
}

// 下一节点
export function getNextFlowNode(data) {
  return request({
    url: '/flowable/task/nextFlowNode',
    method: 'post',
    data: data
  })
}

// 部署流程实例
export function deployStart(deployId) {
  return request({
    url: '/flowable/process/startFlow/' + deployId,
    method: 'get',
  })
}

// 查询流程定义详细
export function getDeployment(id) {
  return request({
    url: '/system/deployment/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addDeployment(data) {
  return request({
    url: '/system/deployment',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateDeployment(data) {
  return request({
    url: '/system/deployment',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delDeployment(id) {
  return request({
    url: '/system/deployment/' + id,
    method: 'delete'
  })
}

// 导出流程定义
export function exportDeployment(query) {
  return request({
    url: '/system/deployment/export',
    method: 'get',
    params: query
  })
}
