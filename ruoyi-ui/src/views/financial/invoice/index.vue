<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams['project.projectName']"
          placeholder="请输入项目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票类型" prop="invoiceType">
        <el-select v-model="queryParams.invoiceType" placeholder="请选择发票类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_invoice_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发票名称" prop="invoiceName">
        <el-input
          v-model="queryParams.invoiceName"
          placeholder="请输入发票名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票号码" prop="invoiceNumber">
        <el-input
          v-model="queryParams.invoiceNumber"
          placeholder="请输入发票号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开票日期" prop="invoiceDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.invoiceDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开票日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="购方名称" prop="purchasesName">
        <el-input
          v-model="queryParams.purchasesName"
          placeholder="请输入购方名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售方名称" prop="salersName" label-width="90px">
        <el-input
          v-model="queryParams.salersName"
          placeholder="请输入销售方名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收款人" prop="payee">
        <el-input
          v-model="queryParams.payee"
          placeholder="请输入收款人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开票人" prop="drawer">
        <el-input
          v-model="queryParams.drawer"
          placeholder="请输入开票人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.fin_invoice_status"
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
          v-hasPermi="['financial:invoice:add']"
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
          v-hasPermi="['financial:invoice:edit']"
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
          v-hasPermi="['financial:invoice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['financial:invoice:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="single"
          @click="handleCheck"
          v-hasPermi="['financial:invoice:edit']"
        >确认录入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invoiceList" @selection-change="handleSelectionChange" @select="handleSelection">
      
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="发票ID" align="center" prop="invoiceId" /> -->
      <el-table-column label="发票" align="center" prop="sysFileInfo.filePath" width="140">
        <template slot-scope="scope">
          <div class="demo-image__lazy">
              <el-image
              style="width: 120px; height: 60px"
              :src="scope.row.sysFileInfo.filePath"
              :preview-src-list="[scope.row.sysFileInfo.filePath]"
              fit="fill">
            </el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="发票来源" align="center" prop="invoiceFrom" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_invoice_from" :value="scope.row.invoiceFrom"/>
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_invoice_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="项目名称" align="center" prop="project.projectName" width="280" />
      <el-table-column label="发票号码" align="center" prop="invoiceNumber" width="90" />
      <el-table-column label="开票日期" align="center" prop="invoiceDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.invoiceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="购方名称" align="center" prop="purchasesName" width="120" />
      <el-table-column label="销售方名称" align="center" prop="salersName" width="120" /> -->
      <el-table-column label="合计金额" align="center" prop="total" width="100" />
      <el-table-column label="合计税额" align="center" prop="tax" width="100" />
      <el-table-column label="价税合计(大写)" align="center" prop="pricePlusChinese" width="260"/>
      <el-table-column label="价税合计(小写)" align="center" prop="pricePlusArabic" width="120"/>
      <!-- <el-table-column label="收款人" align="center" prop="payee" />
      <el-table-column label="复核" align="center" prop="review" />
      <el-table-column label="开票人" align="center" prop="drawer" />
      <el-table-column label="发票名称" align="center" prop="invoiceName" width="150" />
      <el-table-column label="发票类型" align="center" prop="invoiceType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fin_invoice_type" :value="scope.row.invoiceType"/>
        </template>
      </el-table-column>
      <el-table-column label="发票代码" align="center" prop="invoiceCode" width="120" />
      <el-table-column label="购方纳税人识别号" align="center" prop="purchasesNumber" width="180" />
      <el-table-column label="购方地址及电话" align="center" prop="purchasesAddressTel" width="260" />
      <el-table-column label="购方开户行及账号" align="center" prop="purchasesBankersAccount" width="260" />
      <el-table-column label="销售方纳税人识别号" align="center" prop="salersNumber" width="180" />
      <el-table-column label="销售方地址及电话" align="center" prop="salersAddressTel" width="260" />
      <el-table-column label="销售方开户行及账号" align="center" prop="salersBankersCcount" width="260" /> -->
      <!-- <el-table-column label="密码区" align="center" prop="password" /> -->
      <!-- <el-table-column label="费用说明" align="center" prop="details" />
      <el-table-column label="备注" align="center" prop="memo" /> -->
      
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="260">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleUpload(scope.row)"
            v-hasPermi="['financial:invoice:upload']"
          >上传发票</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['financial:invoice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['financial:invoice:remove']"
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

    <!-- 添加或修改发票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目" prop="projectId">
              <el-select v-model="form.projectId" placeholder="请选择项目" filterable>
                <el-option
                  v-for="project in projectList"
                  :key="project.projectId"
                  :label="project.projectName"
                  :value="project.projectId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="发票来源" prop="invoiceFrom">
              <el-select v-model="form.invoiceFrom" placeholder="请选择发票来源">
                <el-option
                  v-for="dict in dict.type.fin_invoice_from"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="发票类型" prop="invoiceType">
              <el-select v-model="form.invoiceType" placeholder="请选择发票类型">
                <el-option
                  v-for="dict in dict.type.fin_invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="发票名称" prop="invoiceName">
              <el-input v-model="form.invoiceName" placeholder="请输入发票名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="发票代码" prop="invoiceCode">
              <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
             <el-form-item label="发票号码" prop="invoiceNumber">
              <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开票日期" prop="invoiceDate">
              <el-date-picker clearable size="small"
                v-model="form.invoiceDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择开票日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购方名称" prop="purchasesName" label-width="140px">
              <el-input v-model="form.purchasesName" placeholder="请输入购方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="购方纳税人识别号" prop="purchasesNumber" label-width="140px">
              <el-input v-model="form.purchasesNumber" placeholder="请输入购方纳税人识别号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购方地址及电话" prop="purchasesAddressTel" label-width="140px">
              <el-input v-model="form.purchasesAddressTel" placeholder="请输入购方地址及电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购方开户行及账号" prop="purchasesBankersAccount" label-width="140px">
              <el-input v-model="form.purchasesBankersAccount" placeholder="请输入购方开户行及账号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销售方名称" prop="salersName" label-width="140px">
              <el-input v-model="form.salersName" placeholder="请输入销售方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售方纳税人识别号" prop="salersNumber" label-width="140px">
              <el-input v-model="form.salersNumber" placeholder="请输入销售方纳税人识别号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="销售方地址及电话" prop="salersAddressTel" label-width="140px">
              <el-input v-model="form.salersAddressTel" placeholder="请输入销售方地址及电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售方开户行及账号" prop="salersBankersCcount" label-width="140px">
              <el-input v-model="form.salersBankersCcount" placeholder="请输入销售方开户行及账号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="合计金额" prop="total">
              <el-input v-model="form.total" placeholder="请输入合计金额" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合计税额" prop="tax">
              <el-input v-model="form.tax" placeholder="请输入合计税额" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="价税合计(小写)" prop="pricePlusArabic" label-width="140px">
              <el-input v-model="form.pricePlusArabic" placeholder="请输入价税合计(小写)" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="价税合计(大写)" prop="pricePlusChinese" label-width="140px">
              <el-input v-model="form.pricePlusChinese" placeholder="请输入价税合计(大写)" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="密码区" prop="password">
              <el-input v-model="form.password" placeholder="请输入密码区" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="收款人" prop="payee">
              <el-input v-model="form.payee" placeholder="请输入收款人" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="复核" prop="review">
              <el-input v-model="form.review" placeholder="请输入复核" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开票人" prop="drawer">
              <el-input v-model="form.drawer" placeholder="请输入开票人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="发票备注" prop="memo">
              <el-input v-model="form.memo" placeholder="请输入备注" />
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
  </div>
</template>

<script>
import { listInvoice, getInvoice, delInvoice, addInvoice, updateInvoice } from "@/api/financial/invoice";
import { getToken } from "@/utils/auth";
import { listProject } from "@/api/project/projectInfo";

export default {
  name: "Invoice",
  dicts: ['fin_invoice_type', 'fin_invoice_status', 'fin_invoice_from'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 最后选中数据
      selection: null,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 发票表格数据
      invoiceList: [],
      // 项目列表选择
      projectList: undefined,
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
          invoiceId: undefined,
        },
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/financial/invoice/upload",
        // 上传的文件列表
        fileList: [],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
        invoiceType: null,
        invoiceName: null,
        invoiceNumber: null,
        invoiceDate: null,
        salersName: null,
        payee: null,
        drawer: null,
        status: null,
        'project.projectName':null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getProjectList();
  },
  methods: {
    /** 查询发票列表 */
    getList() {
      this.loading = true;
      listInvoice(this.queryParams).then(response => {
        this.invoiceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询项目列表 */
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
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
        invoiceId: null,
        projectId: null,
        invoiceType: null,
        invoiceName: null,
        invoiceCode: null,
        invoiceNumber: null,
        invoiceDate: null,
        purchasesName: null,
        purchasesNumber: null,
        purchasesAddressTel: null,
        purchasesBankersAccount: null,
        salersName: null,
        salersNumber: null,
        salersAddressTel: null,
        salersBankersCcount: null,
        total: null,
        tax: null,
        pricePlusChinese: null,
        pricePlusArabic: null,
        password: null,
        payee: null,
        review: null,
        drawer: null,
        memo: null,
        status: null,
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
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.invoiceId)
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
      this.title = "添加发票";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const invoiceId = row.invoiceId || this.ids
      getInvoice(invoiceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改发票";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.invoiceId != null) {
            updateInvoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInvoice(this.form).then(response => {
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
      const invoiceIds = row.invoiceId || this.ids;
      this.$modal.confirm('是否确认删除发票编号为"' + invoiceIds + '"的数据项？').then(function() {
        return delInvoice(invoiceIds);
      }).then(() => {
        this.getList();row
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 核查按钮操作 */
    handleCheck(row) {
      if(this.selection.status=='2'){
        this.reset();
        const invoiceIds = row.invoiceId || this.ids;
        this.form.invoiceId = row.invoiceId || this.ids[0];
        this.form.status = '3';
        updateInvoice(this.form).then(response => {
          this.$modal.msgSuccess("录入成功");
          this.getList();
        });
      }else{
        this.$modal.msgWarning("该发票信息不处于可录入状态");
      }
      
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('financial/invoice/export', {
        ...this.queryParams
      }, `invoice_${new Date().getTime()}.xlsx`)
    },

    /** 上传发票按钮操作 */
    handleUpload(row) {
      if(row.status!='1'){
        this.uploadOpen=false;
        this.$modal.msgWarning("不能上传发票");
      }else{
        this.uploadOpen = true;
        this.upload.uploadData.invoiceId = row.invoiceId||this.ids
        console.log("rererere");
        console.log(this.upload.uploadData.invoiceId);
        this.upload.fileList = [];
        this.uploadTitle = "上传发票";
      }
    },
    // 文件提交处理
    submitUpload() {
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
      this.uploadOpen = false;
      this.$modal.msgSuccess(response.msg);
      this.getList();
    },
  }
};
</script>
