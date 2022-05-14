<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报销单号" prop="reimburseNumber">
        <el-input
          v-model="queryParams.reimburseNumber"
          placeholder="请输入报销单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报销部门" prop="deptName">
        <el-input
          v-model="queryParams['dept.deptName']"
          placeholder="请输入报销部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经手人" prop="empName">
        <el-input
          v-model="queryParams['emp.empName']"
          placeholder="请输入经手人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目编号" prop="projectId">
        <el-input
          v-model="queryParams.projectId"
          placeholder="请输入项目编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报销类型" prop="reimburseType">
        <el-select v-model="queryParams.reimburseType" placeholder="请选择报销类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_reimburse_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报销状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择报销状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_reimburse_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['financial:reimburse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['financial:reimburse:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['financial:reimburse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['financial:reimburse:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="single"
          @click="handleApproval"
          v-hasPermi="['financial:reimburse:approval']"
          >提交申请</el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reimburseList" @selection-change="handleSelectionChange" @select="handleSelection">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报销单号" align="center" prop="reimburseNumber" />
      <el-table-column label="报销部门" align="center" prop="dept.deptName" />
      <el-table-column label="经手人"  align="center" prop="emp.empName" />
      <el-table-column label="项目编号" align="center" prop="project.projectId" />
      <el-table-column label="项目名称" align="center" prop="project.projectName" />
      <el-table-column label="报销金额" align="center" prop="amount" />
      <el-table-column label="报销类型" align="center" prop="reimburseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_reimburse_type" :value="scope.row.reimburseType"/>
        </template>
      </el-table-column>
      <el-table-column label="报销状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_reimburse_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="附件" align="center" prop="annex" /> -->
      <el-table-column label="发票" align="center" prop="sysFileInfo.filePath" width="160">
        <template slot-scope="scope">
          <div class="demo-image__lazy">
              <el-image
              style="width: 140px; height: 70px"
              :src="scope.row.sysFileInfo.filePath"
              :preview-src-list="[scope.row.sysFileInfo.filePath]"
              fit="fill">
            </el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="费用说明" align="center" prop="details" />

      <!-- <el-table-column label="流程ID" align="center" prop="flowId" /> -->
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleUpload(scope.row)"
            v-hasPermi="['financial:reimburse:upload']"
          >上传发票</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['financial:reimburse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['financial:reimburse:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改报销对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="报销单号" prop="reimburseNumber">
              <el-input v-model="form.reimburseNumber" placeholder="请输入报销单号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报销部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经手人" prop="handBy">
              <el-input v-model="form.handBy" placeholder="请输入经手人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号" prop="projectId">
              <el-input v-model="form.projectId" placeholder="请输入项目编号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报销类型" prop="reimburseType">
              <el-select v-model="form.reimburseType" placeholder="请选择报销类型">
                <el-option
                  v-for="dict in dict.type.fin_reimburse_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报销金额" prop="amount">
              <el-input v-model="form.amount" placeholder="请输入报销金额" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          
          <el-col :span="24">
            <el-form-item label="费用说明" prop="details">
              <el-input v-model="form.details" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 上传发票对话框 -->
    <el-dialog :title="uploadTitle" :visible.sync="uploadOpen" width="500px" append-to-body>
      <el-row>
        <el-col :span="24">
            <el-upload
              ref="upload"
              :limit="1"
              accept=".jpg, .png"
              :data="upload.uploadData"
              :action="upload.url"
              :headers="upload.headers"
              :file-list="upload.fileList"
              :on-progress="handleFileUploadProgress"
              :on-success="handleFileSuccess"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading" @click="submitUpload">确认上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>

            <!-- <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDownload()"
            >下载</el-button> -->
            <!-- <el-input v-model="form.annex" placeholder="请输入附件" /> -->

        </el-col>
      </el-row>

    </el-dialog>

    <!-- 发起流程 -->
    <el-dialog :title="approval.title" :visible.sync="approval.open" width="650px" append-to-body v-drag>
      <el-table v-loading="processLoading" fit :data="definitionList" border>
        <el-table-column label="流程名称" align="left" prop="name" />
        <el-table-column label="流程版本" align="left" width="85px">
          <template slot-scope="scope">
            <el-tag size="medium" >v{{scope.row.version}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="流程分类" align="left" prop="category" width="150px" />
        <el-table-column label="操作" align="left" width="120px" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click="handleStartProcess(scope.row)">发起流程</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="processTotal>0" :total="processTotal" :page.sync="processQueryParams.pageNo"
            :limit.sync="processQueryParams.pageSize" @pagination="listDefinition" />
    </el-dialog>
  </div>
</template>

<script>
import { listReimburse, getReimburse, delReimburse, addReimburse, updateReimburse } from "@/api/financial/reimburse";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import { getToken } from "@/utils/auth";
import { parseStrEmpty } from "@/utils/ruoyi";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listDefinition} from "@/api/flowable/definition";

export default {
  name: "Reimburse",
  dicts: ['fin_reimburse_status', 'fin_reimburse_type'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
      // 选中数组
      ids: [],
      // 最后选中数据
      selection: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报销表格数据
      reimburseList: [],
      // 部门树选项
      deptOptions: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      uploadTitle: "",
      // 是否显示弹出层
      uploadOpen: false,
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
              // 上传的附加数据
        uploadData:{
          reimburseId: undefined,
        },
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/financial/reimburse/upload",
        // 上传的文件列表
        fileList: [],
      },

      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reimburseNumber: undefined,
        deptId: undefined,
        handBy: undefined,
        projectId: undefined,
        reimburseType: undefined,
        status: undefined,
        'dept.deptName': undefined,
        'emp.emptName': undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        reimburseNumber: [
          { required: true, message: "报销单号不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "报销部门不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "报销金额不能为空", trigger: "blur" }
        ],
        handBy: [
          { required: true, message: "经手人不能为空", trigger: "blur" }
        ],
        projectId: [
          { required: true, message: "项目编号不能为空", trigger: "blur" }
        ],
      },
      //流程列表数据
      definitionList:[],
      //流程总条数
      processTotal:0,
      // 是否显示弹出层（流程列表）
      approval:{
        open: false,
        title: undefined,

        reimburseId: undefined,
        formData: {},
      },
      // 流程查询参数
      processQueryParams: {
        pageNo: 1,
        pageSize: 10,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      },
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询报销列表 */
    getList() {
      this.loading = true;
      listReimburse(this.queryParams).then(response => {
        console.log(response.rows);
        this.reimburseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // /** 判断是否有发票 */
    // getSysFileInfo(row){
    //   if(row.sysFileInfo){
    //     return [];
    //   }
    // },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reimburseId: null,
        reimburseNumber: null,
        deptId: null,
        handBy: null,
        projectId: null,
        amount: null,
        reimburseType: null,
        annex: null,
        details: null,
        status: null,
        flowId: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection, row) {
      this.ids = selection.map(item => item.reimburseId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 最后选中数据
    handleSelection(selection, row) {
      console.log('rowrow');
      console.log(row);
      this.selection = row
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报销";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reimburseId = row.reimburseId || this.ids
      this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
      
      getReimburse(reimburseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报销";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reimburseId != null) {
            updateReimburse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReimburse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reimburseIds = row.reimburseId || this.ids;
      this.$modal.confirm('是否确认删除报销编号为"' + reimburseIds + '"的数据项？').then(function() {
        return delReimburse(reimburseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('financial/reimburse/export', {
        ...this.queryParams
      }, `reimburse_${new Date().getTime()}.xlsx`)
    },

    /** 上传发票按钮操作 */
    handleUpload(row) {
      if(row.status>=2){
        this.uploadOpen=false;
        this.$modal.msgWarning("不能上传发票");
      }else{
        this.uploadOpen = true;
        this.upload.uploadData.reimburseId = row.reimburseId||this.ids
        console.log("rererere");
        console.log(this.upload.uploadData.reimburseId);
        this.upload.fileList = [];
        this.uploadTitle = "上传发票";
      }
    },

    // 文件提交处理
    submitUpload() {
      //this.upload.url += parseStrEmpty(this.upload.reimburseId) + "/upload";
      console.log(this.upload.url)
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      console.log("dddddddddddddd");
      console.log(response);
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.uploadOpen = false;
      this.$modal.msgSuccess(response.msg);
      this.getList();
    },
    // 文件下载处理
    handleDownload() {
      var name = "blob_20220303194154A001";
      var url = "http://localhost:8080/profile/upload/2022/04/10/22年4月_20220410173629A001.rar";
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', name + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      a.click()
    },

    // 提交审批
    handleApproval(row) {
      if(this.selection.status == '2'){
        this.approval.reimburseId = row.reimburseId || this.ids;
        this.approval.open = true;
        this.approval.title = "发起流程";
        this.listDefinition();
      }else if(this.selection.status == '1'){
        this.$modal.msgWarning("请先上传发票！");
      }else{
        this.$modal.msgWarning("此报销信息处于不能提交审核的状态！");
      }
    },
    //打开流程列表
    listDefinition() {
      this.processQueryParams.category = "reimburse";
      listDefinition(this.processQueryParams).then(resp => {
        this.definitionList = resp.data.list;
        this.processTotal = resp.data.size;
        this.processLoading = false;
        
      });
    },
    /**  发起流程申请 */
    handleStartProcess(def) {
      console.log("contractId");
      console.log(this.approval.reimburseId);
      getReimburse(this.approval.reimburseId).then((response) => {
        console.log(response.data);
        this.approval.formData = response.data;
        console.log("contract process");
        console.log(this.approval.formData);
        this.$router.push({path: '/flowable/task/start',
          query: {
            deployId: def.deploymentId,
            procDefId: def.id,

            formData: this.approval.formData,
          }
        });
      });
      this.approval.open = false;
    },
  }
};
</script>
