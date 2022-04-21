<!--执行单个任务-->
<template>
  <div class="app-container">
    <!--表单模块-->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-document">表单信息</span>
        <el-button style="float: right;" type="primary" @click="goBack">返回</el-button>
      </div>

      <!--流程处理表单模块-->
      <el-col :span="16" :offset="6">
        <div>
          <parser :key="new Date().getTime()" :form-conf="variablesData" @submit="submitForm" />
        </div>
        <div style="margin-left: 10%; margin-bottom: 20px; font-size: 14px;" v-if="!isStartNode">
          <el-button  icon="el-icon-edit-outline" type="success" size="mini" @click="handleComplete">审批</el-button>
          <!--
          <el-button  icon="el-icon-edit-outline" type="primary" size="mini" @click="handleDelegate">委派</el-button>
          <el-button  icon="el-icon-edit-outline" type="primary" size="mini" @click="handleAssign">转办</el-button>
          <el-button  icon="el-icon-edit-outline" type="primary" size="mini" @click="handleDelegate">签收</el-button>
          <el-button  icon="el-icon-circle-close" type="danger" size="mini" @click="handleReject">驳回</el-button>
          -->
          <el-button  icon="el-icon-refresh-left" type="warning" size="mini" @click="handleReturnV2">退回</el-button>
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
            <el-timeline-item v-for="(item,index) in flowRecordList" :key="index"
                    :icon="setIcon(item.finishTime)" :color="setColor(item.finishTime)">
              <p style="font-weight: 700">{{item.taskName}}</p>
              <el-card :body-style="{ padding: '10px' }">
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

    <!--审批正常流程-->
    <el-dialog :title="completeTitle" :visible.sync="completeOpen" width="600px" append-to-body v-drag>
      <el-form ref="taskForm" :model="taskForm" label-width="80px">
        <el-form-item label="处理意见" prop="comment" :rules="[{ required: true, message: '请输入处理意见', trigger: 'blur' }]">
          <el-input type="textarea" :rows="6" v-model="taskForm.comment" autocomplete="off" clearable style="width: 95%;" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="completeOpen = false">取 消</el-button>
        <el-button type="primary" @click="taskComplete">确 定</el-button>
      </span>
    </el-dialog>

    <!--退回流程-->
    <el-dialog :title="returnTitle" :visible.sync="returnOpen" width="550px" append-to-body v-drag>
      <el-form ref="returnForm" :model="returnForm" label-width="90px" >
        <!--
        <el-form-item label="退回节点" prop="targetKey" :rules="[{ required: true, message: '请选择退回节点', trigger: 'blur' }]">
          <el-radio-group v-model="returnForm.targetKey">
            <el-radio-button v-for="item in returnTaskList" :key="item.id" :label="item.id">{{item.name}}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        -->
        <el-form-item label="退回意见" prop="comment" :rules="[{ required: true, message: '请输入退回意见', trigger: 'blur' }]">
          <el-input type="textarea" :rows="6" v-model="returnForm.comment" placeholder="请输入退回意见" style="width: 95%"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="returnOpen = false">取 消</el-button>
          <el-button type="primary" @click="submitReturnTask">确 定</el-button>
      </span>
    </el-dialog>

    <!--驳回流程-->
    <el-dialog :title="rejectTitle" :visible.sync="rejectOpen" width="550px" append-to-body v-drag>
      <el-form ref="taskForm" :model="taskForm" label-width="90px" >
        <el-form-item label="驳回意见" prop="comment" :rules="[{ required: true, message: '请输入驳回意见', trigger: 'blur' }]">
          <el-input type="textarea" :rows="4" v-model="taskForm.comment" placeholder="请输入意见" style="width: 95%"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="rejectOpen = false">取 消</el-button>
        <el-button type="primary" @click="taskReject">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {getFlowRecords} from "@/api/flowable/finished";
import Parser from '@/components/parser/Parser'
import {getProcessVariables, readXml, getFlowViewer} from "@/api/flowable/definition";
import {complete, rejectTask, getReturnList, getReturnTask, returnTask, checkIfProcessTaskStartNode, restartTask} from "@/api/flowable/todo";
import flow from '@/views/flowable/task/record/flow'

export default {
  name: "TodoTask",
  components: {
    'parser': Parser,
    'flow': flow
  },
  data() {
    return {
      taskId: null,   // 流程任务编号
      returnForm:{
        targetKey: null,  //回退目标节点
        comment: null     //意见内容
      },

      taskForm:{
        deployId: "",  // 流程定义编号
        procInsId: "", // 流程实例编号
        taskId: "" ,   // 流程任务编号
        comment:"",    // 意见内容
        vars: "",
        targetKey: ""
      },
      isStartNode: false,
      variablesData: {}, // 流程变量数据
      returnTaskList: [],  // 回退列表数据
      completeTitle: null,
      completeOpen: false,
      returnTitle: null,
      returnOpen: false,
      rejectOpen: false,
      rejectTitle: null,

      flowRecordList: [], // 流程流转数据

      xmlData: "",
      taskList: []
    };
  },
  created() {

  },
  mounted() {
    this.taskForm.deployId = this.$route.query && this.$route.query.deployId;
    this.taskForm.procInsId = this.$route.query && this.$route.query.procInsId;
    this.taskId = this.$route.query && this.$route.query.taskId;
    this.taskForm.taskId = this.$route.query && this.$route.query.taskId;

    //获取表单数据，如果是起始节点，允许修改表单，否则不允许
    this.checkIfStartTask(this.taskForm.taskId);

    //获取审批记录
    this.getFlowRecordList(this.taskForm.procInsId);

    //画流程图
    this.getModelDetail(this.taskForm.deployId);
    //回显流程图流转记录
    if (this.taskForm.procInsId) {
      this.getFlowViewer(this.taskForm.procInsId);
    }
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
    /** 检查是否为起始节点 */
    checkIfStartTask(taskId) {
      checkIfProcessTaskStartNode({taskId: taskId}).then(res => {
        this.isStartNode = res.data;
        getProcessVariables(taskId).then(res2 => {
          this.variablesData = res2.data.variables;
          if (this.isStartNode) {//如果是起始节点，允许编辑表单
            this.variablesData.disabled = false;
            this.variablesData.formBtns = true;
          }
        }).catch(() => {

        });
      }).catch(res => {

      });
    },
    /** 流程流转记录 */
    getFlowRecordList(procInsId) {

      const params = {procInsId: procInsId};
      onsole.log("ppppppppp");
      console.log(params);
      getFlowRecords(params).then(res => {
        console.log("fffffffff");
        console.log(res.data);
        this.flowRecordList = res.data;
      }).catch(res => {
        this.goBack();
      });
    },
    /** 审批任务选择 */
    handleComplete() {
      this.completeOpen = true;
      this.completeTitle = "审批流程";
      this.resetForm("taskForm");
    },
    /** 审批任务 */
    taskComplete() {
      this.$refs.taskForm.validate((valid) => {
        if (valid) {
          complete(this.taskForm).then(response => {
            this.$modal.msgSuccess(response.msg);
            this.goBack();
          }).catch(() => {

          });
        }
      });
    },
    /** 委派任务 */
    handleDelegate() {

    },
    handleAssign() {

    },
    /** 返回页面 */
    goBack() {
      // 关闭当前标签页并返回上个页面
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.go(-1);
    },
    /** 申请流程表单数据提交 */
    submitForm(data) {
      if (data) {
        const valData = data.valData;
        const formData = data.formData;
        formData.disabled = true;
        formData.formBtns = false;
        valData.variables = formData;
         // 启动流程并将表单数据加入流程变量
        restartTask(this.taskForm.taskId, JSON.stringify(valData)).then(res => {
          this.$modal.msgSuccess(res.msg);
          this.goBack();
        }).catch(() => {

        });
      }
    },
    /** 驳回任务 */
    handleReject() {
      this.rejectOpen = true;
      this.rejectTitle = "驳回流程";
      this.resetForm("taskForm");
    },
    /** 驳回任务 */
    taskReject() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          rejectTask(this.taskForm).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          }).catch(() => {

          });
        }
      });
    },
    /** 可退回任务列表 */
    handleReturn() {
      this.returnTitle = "退回流程";
      getReturnList({taskId: this.taskId}).then(res => {
        this.returnTaskList = res.data;
        if (this.returnTaskList && this.returnTaskList.length > 0) {
          this.returnOpen = true;
        } else {
          this.returnOpen = false;
          this.msgError("当前节点不可退回");
        }
      }).catch(() => {

      });
      this.resetForm("returnForm");
    },
    /** 可退回任务列表(起始节点) */
    handleReturnV2() {
      this.returnTitle = "退回流程";
      getReturnTask({taskId: this.taskId}).then(res => {
        this.returnTaskList = [res.data];
        this.returnForm.targetKey = res.data.id;
        this.returnOpen = true;
      }).catch(() => {
        this.returnOpen = false;
      });
      this.resetForm("returnForm");
    },
    /** 提交退回任务 */
    submitReturnTask() {
      this.$refs["returnForm"].validate(valid => {
        if (valid) {
            var params = "taskId=" + this.taskId
              + (this.returnForm.targetKey ? "&targetKey=" + this.returnForm.targetKey : "")
              + (this.returnForm.comment ? "&comment=" + this.returnForm.comment : "");
          returnTask(params).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          }).catch(() => {

          });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.test-form {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}

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
