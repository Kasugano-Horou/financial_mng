import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

//核算项目成本
export function AccountProjectCost(projectIds) {
  return request({
    url: '/financial/projectCost/account/' +  parseStrEmpty(projectIds),
    method: 'put'
  })
}

//核算所有项目成本
export function AccountAllProjectCost() {
  return request({
    url: '/financial/projectCost/accountAll/',
    method: 'get'
  })
}


// 查询项目成本列表
export function listProjectCost(query) {
  return request({
    url: '/financial/projectCost/list',
    method: 'get',
    params: query
  })
}

// 查询项目成本详细
export function getProjectCost(projectCostId) {
  return request({
    url: '/financial/projectCost/' +  parseStrEmpty(projectCostId),
    method: 'get'
  })
}

// 查询项目成本列表
export function listRate() {
  return request({
    url: '/financial/projectCost/rate',
    method: 'get'
  })
}

// 新增项目成本
export function addProjectCost(data) {
  return request({
    url: '/financial/projectCost',
    method: 'post',
    data: data
  })
}

// 修改项目成本
export function updateProjectCost(data) {
  return request({
    url: '/financial/projectCost',
    method: 'put',
    data: data
  })
}

// 删除项目成本
export function delProjectCost(projectCostId) {
  return request({
    url: '/financial/projectCost/' + projectCostId,
    method: 'delete'
  })
}
