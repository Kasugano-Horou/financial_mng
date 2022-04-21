import request from '@/utils/request'

// 查询问卷填写列表
export function listAnswer(query) {
  return request({
    url: '/survey/answer/list',
    method: 'get',
    params: query
  })
}

// 查询问卷填写详细
export function getAnswer(answerId) {
  return request({
    url: '/survey/answer/' + answerId,
    method: 'get'
  })
}

// 新增问卷填写
export function addAnswer(data) {
  return request({
    url: '/survey/answer',
    method: 'post',
    data: data
  })
}

// 修改问卷填写
export function updateAnswer(data) {
  return request({
    url: '/survey/answer',
    method: 'put',
    data: data
  })
}

// 删除问卷填写
export function delAnswer(answerId) {
  return request({
    url: '/survey/answer/' + answerId,
    method: 'delete'
  })
}

// 导出问卷填写
export function exportAnswer(query) {
  return request({
    url: '/survey/answer/export',
    method: 'get',
    params: query
  })
}

// 新增问卷填写
export function addAnswerBatch(data) {
  return request({
    url: '/survey/answer/addBatch',
    method: 'post',
    data: data
  })
}

// 分组查询列表
export function allList(query) {
  return request({
    url: '/survey/answer/allList',
    method: 'get',
    params: query
  })
}

// 查询问卷填写详细
export function getAnswerDetail(paperId,userId) {
  return request({
    url: '/survey/answer/detail/' + paperId +"/"+ userId,
    method: 'get'
  })
}
