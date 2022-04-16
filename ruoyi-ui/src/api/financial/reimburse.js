import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询报销列表
export function listReimburse(query) {
  return request({
    url: '/financial/reimburse/list',
    method: 'get',
    params: query
  })
}

// 查询报销详细
export function getReimburse(reimburseId) {
  return request({
    url: '/financial/reimburse/' + parseStrEmpty(reimburseId),
    method: 'get'
  })
}

// 新增报销
export function addReimburse(data) {
  return request({
    url: '/financial/reimburse',
    method: 'post',
    data: data
  })
}

// 修改报销
export function updateReimburse(data) {
  return request({
    url: '/financial/reimburse',
    method: 'put',
    data: data
  })
}

// 删除报销
export function delReimburse(reimburseId) {
  return request({
    url: '/financial/reimburse/' + parseStrEmpty(reimburseId),
    method: 'delete'
  })
}
