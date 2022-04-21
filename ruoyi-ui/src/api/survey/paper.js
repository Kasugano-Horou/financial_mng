import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询调查问卷模板列表
export function listPaper(query) {
  return request({
    url: '/survey/paper/list',
    method: 'get',
    params: query
  })
}

// 查询调查问卷模板详细
export function getPaper(id) {
  return request({
    url: '/survey/paper/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 新增调查问卷模板
export function addPaper(data) {
  return request({
    url: '/survey/paper',
    method: 'post',
    data: data
  })
}

// 修改调查问卷模板
export function updatePaper(data) {
  return request({
    url: '/survey/paper',
    method: 'put',
    data: data
  })
}

// 删除调查问卷模板
export function delPaper(id) {
  return request({
    url: '/survey/paper/' + id,
    method: 'delete'
  })
}

// 导出调查问卷模板
export function exportPaper(query) {
  return request({
    url: '/survey/paper/export',
    method: 'get',
    params: query
  })
}

// 查询调查问卷详情
export function getDetail(params) {
  return request({
    url: '/survey/paper/detail/',
    method: 'get',
    params: params
  })
}