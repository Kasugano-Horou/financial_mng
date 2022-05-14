<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购主题" prop="purchaseName">
        <el-input
          v-model="queryParams.purchaseName"
          placeholder="请输入采购主题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购类别" prop="purchaseType">
        <el-select v-model="queryParams.purchaseType" placeholder="请选择采购类别" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_purchase_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入供应商"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_purchase_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="daterangePurchaseDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['financial:purchase:add']"
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
          v-hasPermi="['financial:purchase:edit']"
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
          v-hasPermi="['financial:purchase:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['financial:purchase:export']"
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
          v-hasPermi="['financial:purchase:approval']"
          >提交申请</el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange" @select="handleSelection">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购ID" align="center" prop="purchaseId" />
      <el-table-column label="项目名称" align="center" prop="project.projectName" />
      <el-table-column label="采购主题" align="center" prop="purchaseName" />
      <el-table-column label="采购类别" align="center" prop="purchaseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_purchase_type" :value="scope.row.purchaseType"/>
        </template>
      </el-table-column>
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="供应商" align="center" prop="supplier" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_purchase_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="采购日期" align="center" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleUpload(scope.row)"
            v-hasPermi="['financial:purchase:upload']"
          >上传发票</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['financial:purchase:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['financial:purchase:remove']"
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID" />
        </el-form-item>
        <el-form-item label="发票ID" prop="invoiceId">
          <el-input v-model="form.invoiceId" placeholder="请输入发票ID" />
        </el-form-item>
        <el-form-item label="采购主题" prop="purchaseName">
          <el-input v-model="form.purchaseName" placeholder="请输入采购主题" />
        </el-form-item>
        <el-form-item label="采购类别" prop="purchaseType">
          <el-select v-model="form.purchaseType" placeholder="请选择采购类别">
            <el-option
              v-for="dict in dict.type.fin_purchase_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供应商" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.fin_purchase_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购日期" prop="purchaseDate">
          <el-date-picker clearable size="small"
            v-model="form.purchaseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择采购日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
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
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase } from "@/api/financial/purchase";
import { getToken } from "@/utils/auth";
import {listDefinition} from "@/api/flowable/definition";

export default {
  name: "Purchase",
  dicts: ['fin_purchase_type', 'fin_purchase_status'],
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
      // 采购订单表格数据
      purchaseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangePurchaseDate: [],

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
        purchaseId: undefined,
      },
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/financial/purchase/upload",
        // 上传的文件列表
        fileList: [],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purchaseName: null,
        purchaseType: null,
        supplier: null,
        status: null,
        purchaseDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        invoiceId: [
          { required: true, message: "发票ID不能为空", trigger: "blur" }
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

        purchaseId: undefined,
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
  },
  methods: {
    /** 查询采购订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePurchaseDate && '' != this.daterangePurchaseDate) {
        this.queryParams.params["beginPurchaseDate"] = this.daterangePurchaseDate[0];
        this.queryParams.params["endPurchaseDate"] = this.daterangePurchaseDate[1];
      }
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        purchaseId: null,
        projectId: null,
        invoiceId: null,
        purchaseName: null,
        purchaseType: null,
        purchaser: null,
        supplier: null,
        status: null,
        purchaseDate: null,
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
      this.daterangePurchaseDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.purchaseId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 最后选中数据
    handleSelection(selection, row) {
      this.selection = row
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const purchaseId = row.purchaseId || this.ids
      getPurchase(purchaseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.purchaseId != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchase(this.form).then(response => {
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
      const purchaseIds = row.purchaseId || this.ids;
      this.$modal.confirm('是否确认删除采购订单编号为"' + purchaseIds + '"的数据项？').then(function() {
        return delPurchase(purchaseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('financial/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    },
    /** 上传发票按钮操作 */
    handleUpload(row) {
      if(row.status>=2){
        this.uploadOpen=false;
        this.$modal.msgWarning("不能上传发票");
      }else{
        this.uploadOpen = true;
        this.upload.uploadData.purchaseId = row.purchaseId||this.ids
        console.log("rererere");
        console.log(this.upload.uploadData.purchaseId);
        this.upload.fileList = [];
        this.uploadTitle = "上传发票";
      }
    },

    // 文件提交处理
    submitUpload() {
      //this.upload.url += parseStrEmpty(this.upload.purchaseId) + "/upload";
      console.log(this.upload.url)
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.uploadOpen = false;
      this.$modal.msgSuccess(response.msg);
      this.getList();
    },
    // // 文件下载处理
    // handleDownload() {
    //   var name = "blob_20220303194154A001";
    //   var url = "http://localhost:8080/profile/upload/2022/04/10/22年4月_20220410173629A001.rar";
    //   var suffix = url.substring(url.lastIndexOf("."), url.length);
    //   const a = document.createElement('a')
    //   a.setAttribute('download', name + suffix)
    //   a.setAttribute('target', '_blank')
    //   a.setAttribute('href', url)
    //   a.click()
    // },
    // 提交审批
    handleApproval(row) {
      if(this.selection.status == '2'){
        this.approval.purchaseId = row.purchaseId || this.ids;
        this.approval.open = true;
        this.approval.title = "发起流程";
        this.listDefinition();
      }else if(this.selection.status == '1'){
        this.$modal.msgWarning("请先上传发票！");
      }else{
        this.$modal.msgWarning("此采购信息处于不能提交审核的状态！");
      }
      

    },
    //打开流程列表
    listDefinition() {
      this.processQueryParams.category = "purchase";
      listDefinition(this.processQueryParams).then(resp => {
        this.definitionList = resp.data.list;
        this.processTotal = resp.data.size;
        this.processLoading = false;
        
      });
    },
    /**  发起流程申请 */
    handleStartProcess(def) {
      console.log("purchaseId");
      console.log(this.approval.purchaseId);
      getPurchase(this.approval.purchaseId).then((response) => {
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
