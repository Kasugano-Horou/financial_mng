import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询合同列表
export function listContract(query) {
  return request({
    url: '/financial/contract/list',
    method: 'get',
    params: query
  })
}

// 查询合同详细
export function getContract(contractId) {
  return request({
    url: '/financial/contract/' + contractId,
    method: 'get'
  })
}

// 新增合同
export function addContract(data) {
  return request({
    url: '/financial/contract',
    method: 'post',
    data: data
  })
}

// 修改合同
export function updateContract(data) {
  return request({
    url: '/financial/contract',
    method: 'put',
    data: data
  })
}

// 删除合同
export function delContract(contractId) {
  return request({
    url: '/financial/contract/' + contractId,
    method: 'delete'
  })
}