import request from '@/utils/request'

// 查询工资列表
export function listWages(query) {
  return request({
    url: '/financial/wages/list',
    method: 'get',
    params: query
  })
}

// 查询工资详细
export function getWages(wageId) {
  return request({
    url: '/financial/wages/' + wageId,
    method: 'get'
  })
}

// 新增工资
export function addWages(data) {
  return request({
    url: '/financial/wages',
    method: 'post',
    data: data
  })
}

// 修改工资
export function updateWages(data) {
  return request({
    url: '/financial/wages',
    method: 'put',
    data: data
  })
}

// 删除工资
export function delWages(wageId) {
  return request({
    url: '/financial/wages/' + wageId,
    method: 'delete'
  })
}
