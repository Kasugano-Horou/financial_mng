import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询工时列表
export function listManhour(query) {
  return request({
    url: '/project/manhour/list',
    method: 'get',
    params: query
  })
}

// 根据项目ID查询工时列表
export function listManhourByProjectId(projectId) {
  return request({
    url: '/project/manhour/list/' + parseStrEmpty(projectId),
    method: 'get'
  })
}

// 查询工时详细
export function getManhour(manhourId) {
  return request({
    url: '/project/manhour/' + parseStrEmpty(manhourId),
    method: 'get'
  })
}

// 新增工时
export function addManhour(data) {
  return request({
    url: '/project/manhour',
    method: 'post',
    data: data
  })
}

// 修改工时
export function updateManhour(data) {
  return request({
    url: '/project/manhour',
    method: 'put',
    data: data
  })
}

// 删除工时
export function delManhour(manhourId) {
  return request({
    url: '/project/manhour/' + parseStrEmpty(manhourId),
    method: 'delete'
  })
}
