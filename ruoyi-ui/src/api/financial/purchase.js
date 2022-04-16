import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询采购订单列表
export function listPurchase(query) {
  return request({
    url: '/financial/purchase/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单详细
export function getPurchase(purchaseId) {
  return request({
    url: '/financial/purchase/' + purchaseId,
    method: 'get'
  })
}

// 新增采购订单
export function addPurchase(data) {
  return request({
    url: '/financial/purchase',
    method: 'post',
    data: data
  })
}

// 修改采购订单
export function updatePurchase(data) {
  return request({
    url: '/financial/purchase',
    method: 'put',
    data: data
  })
}

// 删除采购订单
export function delPurchase(purchaseId) {
  return request({
    url: '/financial/purchase/' + purchaseId,
    method: 'delete'
  })
}
