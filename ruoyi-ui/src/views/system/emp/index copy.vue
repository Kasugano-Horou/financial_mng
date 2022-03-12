<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
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
      <el-col :span="20" :xs="24">
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
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['financial:contract:export']"
              >导出</el-button
            >
          </el-col>
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="合同名称" align="center" prop="contractName" />
          <el-table-columnlabel="合同编号" align="center" prop="contractNumber" />
          <el-table-column label="合同状态" align="center" prop="status">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.fin_contract_status"
                :value="scope.row.status"
              />
            </template>
          </el-table-column>
          <el-table-column label="合同类型" align="center" prop="contractType">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.fin_contract_type"
                :value="scope.row.contractType"
              />
            </template>
          </el-table-column>
          <el-table-column label="签订部门" align="center" key="deptName" prop="dept.deptName" />
          <el-table-column
            label="对方单位"
            align="center"
            prop="oppositePartyUnit"
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

          <el-table-column label="应收款" align="center" prop="debtors" />
          <el-table-column label="已收款" align="center" prop="receivable" />
          <el-table-column label="授权委托人" align="center" prop="principal" />
          
          <el-table-column
            label="合同文件地址"
            align="center"
            prop="fileAddress"
          />

          <el-table-column
            label="合同签订地点"
            align="center"
            prop="contractLocation"
          />
          <el-table-column
            label="合同简介"
            align="center"
            prop="introduction"
          />
          <el-table-column label="备注" align="center" prop="remark" />
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

export default {
  name: "Contract",
  dicts: ["fin_contract_status", "fin_contract_type"],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
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
  },
};
</script>