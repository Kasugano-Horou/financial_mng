<template>
  <div class="app-container">
    <!--表单模块-->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-document">表单信息</span>
        <el-button style="float: right;" type="primary" @click="goBack">返回</el-button>
      </div>
      <el-col :span="16" :offset="6">
        <div>
          <parser :key="new Date().getTime()" :form-conf="formConf" />
        </div>
      </el-col>
    </el-card>

    <!--流程审批记录-->
    <el-card class="box-card" v-if="flowRecordList">
      <div slot="header" class="clearfix">
        <span class="el-icon-notebook-1">审批记录</span>
      </div>
      <el-col :span="16" :offset="4" >
        <div class="block">
          <el-timeline>
            <el-timeline-item v-for="(item, index) in flowRecordList" :key="index"
                    :icon="setIcon(item.finishTime)" :color="setColor(item.finishTime)">
              <p style="font-weight: 700">{{item.taskName}}</p>
              <el-card :body-style="{padding: '10px'}">
                <label v-if="item.assigneeName" style="font-weight: normal;margin-right: 30px;">实际办理： {{item.assigneeName}} <el-tag type="info" size="mini">{{item.deptName}}</el-tag></label>
                <label v-if="item.candidate" style="font-weight: normal;margin-right: 30px;">候选办理： {{item.candidate}}</label>
                <label style="font-weight: normal">接收时间： </label><label style="color:#8a909c;font-weight: normal">{{item.createTime}}</label>
                <label v-if="item.finishTime" style="margin-left: 30px;font-weight: normal">办结时间： </label><label style="color:#8a909c;font-weight: normal">{{item.finishTime}}</label>
                <label v-if="item.duration" style="margin-left: 30px;font-weight: normal">耗时： </label><label style="color:#8a909c;font-weight: normal">{{item.duration}}</label>

                <p v-if="item.comment">
                  <el-tag type="success" v-if="item.comment.type === '1'">  {{item.comment.comment}}</el-tag>
                  <el-tag type="warning" v-if="item.comment.type === '2'">  {{item.comment.comment}}</el-tag>
                  <el-tag type="danger" v-if="item.comment.type === '3'">  {{item.comment.comment}}</el-tag>
                </p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-card>

    <!--流程图-->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-picture-outline">流程图</span>
      </div>
      <flow :xmlData="xmlData" :taskData="taskList"></flow>
    </el-card>
  </div>
</template>

<script>
import {getFlowRecords} from "@/api/flowable/finished";
import Parser from '@/components/parser/Parser'
import {getProcessVariables, readXml, getFlowViewer} from "@/api/flowable/definition";
import flow from '@/views/flowable/task/record/flow';

export default {
  name: "FlowDetail",
  components: {
    'parser': Parser,
    'flow': flow
  },
  data() {
    return {
      formConf: {}, // 流程表单变量数据

      flowRecordList: [], // 流程审批记录数据

      xmlData: "",
      taskList: []
    };
  },
  created() {

  },
  mounted() {
    var deployId = this.$route.query && this.$route.query.deployId;
    var procInsId = this.$route.query && this.$route.query.procInsId;
    var taskId = this.$route.query && this.$route.query.taskId;

    //获取流程表单数据
    this.processVariables(taskId);

    //获取审批记录
    this.getFlowRecordList(procInsId);

    //画流程图
    this.getModelDetail(deployId);
    //回显流程图流转记录
    this.getFlowViewer(procInsId);
  },
  methods: {
    /** xml 文件 */
    getModelDetail(deployId) {
      // 发送请求，获取xml
      readXml(deployId).then(res => {
        this.xmlData = res.data;
      }).catch(res => {

      });
    },
    getFlowViewer(procInsId) {
      getFlowViewer(procInsId).then(res => {
        this.taskList = res.data;
      }).catch(res => {

      });
    },
    setIcon(val) {
      if (val) {
        return "el-icon-check";
      } else {
        return "el-icon-time";
      }
    },
    setColor(val) {
      if (val) {
        return "#2bc418";
      } else {
        return "#b3bdbb";
      }
    },
    /** 流程流转记录 */
    getFlowRecordList(procInsId) {
      const params = {procInsId: procInsId};
      getFlowRecords(params).then(res => {
        this.flowRecordList = res.data;
      }).catch(res => {
        this.goBack();
      });
    },
    /** 获取流程表单内容 */
    processVariables(taskId) {
      if (taskId) {
        // 提交流程申请时填写的表单存入了流程变量中后续任务处理时需要展示
        getProcessVariables(taskId).then(res => {
          this.formConf = res.data.variables;
        }).catch(() => {

        });
      }
    },
    /** 返回页面 */
    goBack() {
      // 关闭当前标签页并返回上个页面
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.go(-1);
    }
  }
};
</script>
<style lang="scss" scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
