<!--开始任务-->
<template>
  <div class="app-container">
    <!--表单模块-->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-document">表单信息</span>
        <el-button style="float: right;" type="primary" @click="goBack">返回</el-button>
      </div>
      <!--初始化流程加载表单信息-->
      <el-col :span="16" :offset="4">
        <div class="test-form">
          <parser :key="new Date().getTime()" :form-conf="formConf" @submit="submitForm"/>
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
import {getFlowForm} from "@/api/flowable/finished";
import Parser from '@/components/parser/Parser'
import {definitionStart, readXml} from "@/api/flowable/definition";
import flow from '@/views/flowable/task/record/flow'

export default {
  name: "StartTask",
  components: {
    'parser': Parser,
    'flow': flow
  },
  props: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      deployId: null,   // 流程定义编号
      procDefId: null,  // 流程定义编号

      formConf: {}, // 默认表单数据

      //数据回显
      formData: {},
      

      xmlData: "",
      taskList: []
    };
  },
  created() {

  },
  mounted() {
    this.deployId = this.$route.query && this.$route.query.deployId;
    this.procDefId = this.$route.query && this.$route.query.procDefId;

    this.formData = this.$route.query && this.$route.query.formData;
    console.log("start process");
    console.log(this.formData);

    //获取流程表单数据
    this.getFlowFrm(this.deployId);

    //画流程图
    this.getModelDetail(this.deployId);
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
    /**流程表单**/
    getFlowFrm(deployId) {
      if (deployId == null) {
        return;
      }
      const params = {deployId: deployId};
      getFlowForm(params).then(res => {
        this.formConf = res.data;
        console.log("formConf1");
        console.log(this.formConf);
        //数据回显
        this.formConf.fields.map(item => {
          var __config__ = item.__config__;
          var __vModel__ = item.__vModel__;
          //__config__.defaultValue = this.formData[item.__vModel__] ? this.formData[item.__vModel__] : __config__.defaultValue;
          var data = this.formData;
          if(item.__vModel__.indexOf(".")>0){
            var models = item.__vModel__.split(".");
            //遍历子对象
            for(var i=0;i<models.length;i++){
              var o = models[i];
              if(data[o]==null){
                console.log("sss");
                data = __config__.defaultValue;
                break;
              }else{
                
                data = data[o];
                //更改props，不然会报Error in mounted hook: "Error: please transfer a valid prop path to form item!"错
                item.__vModel__ = o;

              }
            }
          }else{
            data = this.formData[item.__vModel__];
          }
         
          __config__.defaultValue = data;
          if(typeof(__config__.defaultValue) == "undefined")__config__.defaultValue=null;
          return item
        })
        console.log("formConf2");
        console.log(this.formConf);
      }).catch(res => {
        this.goBack();
      });

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
        if (this.procDefId) {
          valData.variables = formData;
           // 启动流程并将表单数据加入流程变量
          this.loading = true;
          definitionStart(this.procDefId, JSON.stringify(valData)).then(res => {
            this.loading = false;
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          }).catch(() => {

          });
        }
      }
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
