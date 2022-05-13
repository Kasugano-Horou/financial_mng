<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <!--合同数据-->
      <el-col :span="21" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="100px"
        >
          <el-form-item label="合同名称" prop="contractName">
            <el-input
              v-model="queryParams.contractName"
              placeholder="请输入合同名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="合同编号" prop="contractNumber">
            <el-input
              v-model="queryParams.contractNumber"
              placeholder="请输入合同编号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="合同状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="请选择合同状态"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in dict.type.fin_contract_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="合同类型" prop="contractType">
            <el-select
              v-model="queryParams.contractType"
              placeholder="请选择合同类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in dict.type.fin_contract_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="合同日期" prop="contractDate">
            <el-date-picker
              clearable
              size="small"
              v-model="queryParams.contractDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择合同日期"
            >
            </el-date-picker>
          </el-form-item>
          <!--
          <el-form-item label="合同开始日期" prop="contractStart">
            <el-date-picker clearable size="small"
              v-model="queryParams.contractStart"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择合同开始日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="合同结束日期" prop="contractEnd">
            <el-date-picker clearable size="small"
              v-model="queryParams.contractEnd"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择合同结束日期">
            </el-date-picker>
          </el-form-item>
          -->
          <el-form-item label="授权委托人" prop="principal">
            <el-input
              v-model="queryParams.principal"
              placeholder="请输入授权委托人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="对方单位" prop="oppositePartyUnit">
            <el-input
              v-model="queryParams.oppositePartyUnit"
              placeholder="请输入对方单位"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
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
              v-hasPermi="['financial:contract:add']"
              >新增</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['financial:contract:edit']"
              >修改</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['financial:contract:remove']"
              >删除</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['financial:contract:import']"
            >导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['financial:contract:export']"
              >导出</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-check"
              size="mini"
              :disabled="single"
              @click="handleApproval"
              v-hasPermi="['financial:contract:approval']"
              >提交申请</el-button
            >
          </el-col>
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="合同名称" align="center" prop="contractName" width="280" />
          <el-table-column label="合同编号" align="center" prop="contractNumber" width="120" />
          <el-table-column label="合同状态" align="center" prop="status" width="120">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.fin_contract_status"
                :value="scope.row.status"
              />
            </template>
          </el-table-column>
          <el-table-column label="合同类型" align="center" prop="contractType" width="130">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.fin_contract_type"
                :value="scope.row.contractType"
              />
            </template>
          </el-table-column>
          <el-table-column label="签订部门" align="center" key="deptName" prop="dept.deptName" width="100" />
          <el-table-column
            label="对方单位"
            align="center"
            prop="oppositePartyUnit"
            width="150"
          />
          <el-table-column label="合同日期" align="center" prop="contractDate" width="100">
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.contractDate, "{y}-{m}-{d}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column label="合同开始日期" align="center" prop="contractStart" width="100">
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.contractStart, "{y}-{m}-{d}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="合同结束日期"
            align="center"
            prop="contractEnd"
            width="100"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.contractEnd, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>

          <el-table-column label="应收款" align="center" prop="debtors" width="90" />
          <el-table-column label="已收款" align="center" prop="receivable" width="90" />
          <el-table-column label="授权委托人" align="center" prop="principal" width="80" />

          <!-- <el-table-column
            label="合同文件地址"
            align="center"
            prop="fileAddress"
          /> -->

          <el-table-column
            label="合同签订地点"
            align="center"
            prop="contractLocation"
            width="120"
          />
          <!-- <el-table-column
            label="合同简介"
            align="center"
            prop="introduction"
            width="0"
          /> -->
          <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
          <!--
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['financial:contract:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['financial:contract:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
          -->
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="合同名称" prop="contractName">
              <el-input
                v-model="form.contractName"
                placeholder="请输入合同名称"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="签订部门" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择签订部门"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合同类型">
              <el-radio-group v-model="form.contractType">
                <el-radio
                  v-for="dict in dict.type.fin_contract_type"
                  :key="dict.value"
                  :label="dict.value"
                  >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同日期" prop="contractDate">
              <el-date-picker
                clearable
                size="small"
                v-model="form.contractDate"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择合同日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合同开始日期" prop="contractStart">
              <el-date-picker
                clearable
                size="small"
                v-model="form.contractStart"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择合同开始日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同结束日期" prop="contractEnd">
              <el-date-picker
                clearable
                size="small"
                v-model="form.contractEnd"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择合同结束日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应收款" prop="debtors">
              <el-input
                v-model="form.debtors"
                placeholder="请输入应收款"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="授权委托人" prop="principal">
              <el-input
                v-model="form.principal"
                placeholder="请输入授权委托人"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="对方单位" prop="oppositePartyUnit">
              <el-input
                v-model="form.oppositePartyUnit"
                placeholder="请输入对方单位"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同签订地点" prop="contractLocation">
              <el-input
                v-model="form.contractLocation"
                placeholder="请输入合同签订地点"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="合同简介" prop="introduction">
            <el-input
              v-model="form.introduction"
              type="textarea"
              placeholder="请输入内容"
              maxlength="30"
            />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              placeholder="请输入备注"
              maxlength="30"
            />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的合同数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
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
import {
  listContract,
  getContract,
  delContract,
  addContract,
  updateContract,
} from "@/api/financial/contract";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listDefinition} from "@/api/flowable/definition";

export default {
  name: "Contract",
  dicts: ["fin_contract_status", "fin_contract_type"],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 合同管理表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: undefined,
        contractNumber: undefined,
        status: undefined,
        contractType: undefined,
        contractDate: undefined,
        contractStart: undefined,
        contractEnd: undefined,
        debtors: undefined,
        receivable: undefined,
        principal: undefined,
        deptId: undefined,
        oppositePartyUnit: undefined,
        fileAddress: undefined,
        contractLocation: undefined,
        introduction: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 合同导入参数
      upload: {
        // 是否显示弹出层（合同导入）
        open: false,
        // 弹出层标题（合同导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的合同数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/financial/contract/importData"
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
      // 表单校验
      rules: {
        contractName: [
          { required: true, message: "合同名称不能为空", trigger: "blur" },
          {
            min: 2,
            max: 255,
            message: "合同名称长度必须介于 2 和 20 之间",
            trigger: "blur",
          },
        ],
        contractNumber: [
          { required: true, message: "合同编号不能为空", trigger: "blur" },
        ],
        contractType: [
          { required: true, message: "合同类型不能为空", trigger: "change" },
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

        contractId: undefined,
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

  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询合同管理列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then((response) => {
        console.log("contractListresponse");
        console.log(response);
        console.log("contractList");
        console.log(response.rows);
        this.contractList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        contractId: undefined,
        contractName: undefined,
        contractNumber: undefined,
        status: "0",
        contractType: "0",
        contractDate: undefined,
        contractStart: undefined,
        contractEnd: undefined,
        debtors: undefined,
        receivable: undefined,
        principal: undefined,
        deptId: undefined,
        oppositePartyUnit: undefined,
        fileAddress: undefined,
        contractLocation: undefined,
        introduction: undefined,
        remark: undefined,
        delFlag: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
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
      this.ids = selection.map((item) => item.contractId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      //this.getTreeselect();
      this.open = true;
      this.title = "添加合同管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId = row.contractId || this.ids;
      getContract(contractId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改合同管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.contractId != null) {
            updateContract(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then((response) => {
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
      const contractIds = row.contractId || this.ids;
      this.$modal
        .confirm('是否确认删除合同管理编号为"' + contractIds + '"的数据项？')
        .then(function () {
          return delContract(contractIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "financial/contract/export",
        {
          ...this.queryParams,
        },
        `contract_${new Date().getTime()}.xlsx`
      );
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "合同导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('financial/contract/importTemplate', {
      }, `contract_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 提交审批
    handleApproval(row) {
      this.approval.contractId = row.contractId || this.ids;
      this.approval.open = true;
      this.approval.title = "发起流程";
      this.listDefinition();

    },
    //打开流程列表
    listDefinition() {
      this.processQueryParams.category = "contract";
      listDefinition(this.processQueryParams).then(resp => {
        this.definitionList = resp.data.list;
        this.processTotal = resp.data.size;
        this.processLoading = false;
        
      });
    },
    /**  发起流程申请 */
    handleStartProcess(def) {
      console.log("contractId");
      console.log(this.approval.contractId);
      getContract(this.approval.contractId).then((response) => {
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
  },
};
</script>
