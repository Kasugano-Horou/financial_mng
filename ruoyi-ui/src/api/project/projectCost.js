import request from '@/utils/request'

// 查询项目成本列表
export function listProjectCost(query) {
  return request({
    url: '/project/projectCost/list',
    method: 'get',
    params: query
  })
}

// 查询项目成本详细
export function getProjectCost(projectCostId) {
  return request({
    url: '/project/projectCost/' + projectCostId,
    method: 'get'
  })
}

// 新增项目成本
export function addProjectCost(data) {
  return request({
    url: '/project/projectCost',
    method: 'post',
    data: data
  })
}

// 修改项目成本
export function updateProjectCost(data) {
  return request({
    url: '/project/projectCost',
    method: 'put',
    data: data
  })
}

// 删除项目成本
export function delProjectCost(projectCostId) {
  return request({
    url: '/project/projectCost/' + projectCostId,
    method: 'delete'
  })
}
