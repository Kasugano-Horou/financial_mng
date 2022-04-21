import request from '@/utils/request'

// 查询题目选项列表
export function listOptions(query) {
  return request({
    url: '/survey/options/list',
    method: 'get',
    params: query
  })
}

// 查询题目选项详细
export function getOptions(optionId) {
  return request({
    url: '/survey/options/' + optionId,
    method: 'get'
  })
}

// 新增题目选项
export function addOptions(data) {
  return request({
    url: '/survey/options',
    method: 'post',
    data: data
  })
}

// 修改题目选项
export function updateOptions(data) {
  return request({
    url: '/survey/options',
    method: 'put',
    data: data
  })
}

// 删除题目选项
export function delOptions(optionId) {
  return request({
    url: '/survey/options/' + optionId,
    method: 'delete'
  })
}

// 导出题目选项
export function exportOptions(query) {
  return request({
    url: '/survey/options/export',
    method: 'get',
    params: query
  })
}
