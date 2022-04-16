import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询发票列表
export function listInvoice(query) {
  return request({
    url: '/financial/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询发票详细
export function getInvoice(invoiceId) {
  return request({
    url: '/financial/invoice/' + invoiceId,
    method: 'get'
  })
}

// 新增发票
export function addInvoice(data) {
  return request({
    url: '/financial/invoice',
    method: 'post',
    data: data
  })
}

// 修改发票
export function updateInvoice(data) {
  return request({
    url: '/financial/invoice',
    method: 'put',
    data: data
  })
}

// 删除发票
export function delInvoice(invoiceId) {
  return request({
    url: '/financial/invoice/' + invoiceId,
    method: 'delete'
  })
}
