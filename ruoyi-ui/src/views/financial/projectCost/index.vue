<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目编号" prop="projectNumber">
        <el-input
          v-model="queryParams.projectNumber"
          placeholder="请输入项目编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
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
          v-hasPermi="['project:projectCost:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:projectCost:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:projectCost:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:projectCost:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="2.0">
        <el-button
          type="danger"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleAccount"
          v-hasPermi="['project:projectCost:account']"
        >成本核算</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectCostList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="项目编号" align="center" prop="proProject.projectNumber" width="160"/>
      <el-table-column label="项目名称" align="center" prop="proProject.projectName" width="280"/>
      <el-table-column label="合同编号" align="center" prop="finContract.contractNumber" width="160"/>
      <el-table-column label="帐期总收入" align="center" prop="generalIncome" width="105"/>
      <el-table-column label="营业税金" align="center" prop="businessTax" width="105"/>
      <el-table-column label="管理成本" align="center" prop="managenmentCost" width="105"/>
      <el-table-column label="人员成本" align="center" prop="personnelCost" width="105"/>
      <el-table-column label="采购支出" align="center" prop="procurementCost" width="105"/>
      <el-table-column label="其他支出" align="center" prop="othersCost" width="105"/>
      <!-- <el-table-column label="维护成本" align="center" prop="maintenanceCost" width="105"/> -->
      <el-table-column label="税前利润" align="center" prop="preTax" width="105"/>
      <el-table-column label="最后核算时间" align="center" prop="updateTime"  width="100">
      <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.updateTime, "{y}-{m}-{d}")
              }}</span>
            </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:projectCost:edit']"
          >修改</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:projectCost:remove']"
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
    
    <!-- 添加或修改项目成本对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改项目成本对话框
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID" />
        </el-form-item>
        <el-form-item label="营业税金" prop="businessTax">
          <el-input v-model="form.businessTax" placeholder="请输入营业税金" />
        </el-form-item>
        <el-form-item label="管理成本" prop="managenmentCost">
          <el-input v-model="form.managenmentCost" placeholder="请输入管理成本" />
        </el-form-item>
        <el-form-item label="人员成本" prop="personnelCost">
          <el-input v-model="form.personnelCost" placeholder="请输入人员成本" />
        </el-form-item>
        <el-form-item label="采购支出" prop="procurementCost">
          <el-input v-model="form.procurementCost" placeholder="请输入采购支出" />
        </el-form-item>
        <el-form-item label="其他支出" prop="othersCost">
          <el-input v-model="form.othersCost" placeholder="请输入其他支出" />
        </el-form-item>
        <el-form-item label="维护成本" prop="maintenanceCost">
          <el-input v-model="form.maintenanceCost" placeholder="请输入维护成本" />
        </el-form-item>
        <el-form-item label="帐期总收入" prop="generalIncome" label-width="80px">
          <el-input v-model="form.generalIncome" placeholder="请输入帐期总收入" />
        </el-form-item>
        <el-form-item label="税前利润" prop="preTax">
          <el-input v-model="form.preTax" placeholder="请输入税前利润" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import { listProjectCost, getProjectCost, delProjectCost, addProjectCost, updateProjectCost, AccountProjectCost } from "@/api/financial/projectCost";
import { listProject } from "@/api/project/projectInfo";

export default {
  name: "ProjectCost",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组-项目成本ID
      ids: [],
      // 选中数组-项目ID
      projectIds: [],
      // 选中数组-项目编号
      projectNumbers: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目成本表格数据
      projectCostList: [],
      // 项目列表选择
      projectList: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectNumber: null,
        projectName: null,
        contractNumber: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectId: [
          { required: true, message: "项目ID不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getProjectList();
  },
  methods: {
    /** 查询项目成本列表 */
    getList() {
      this.loading = true;
      listProjectCost(this.queryParams).then(response => {
        
        this.account(response.rows);
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
    account(rows){
      this.projectCostList = rows;
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        projectCostId: null,
        projectId: null,
        businessTax: null,
        managenmentCost: null,
        personnelCost: null,
        procurementCost: null,
        othersCost: null,
        // maintenanceCost: null,
        generalIncome: null,
        preTax: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },

    /**核算按钮操作 */
    handleAccount(row) {
      const projectNumbers = row.projectNumber || this.projectNumbers;
      const projectIds = row.projectId || this.projectIds;
      this.$modal.confirm('是否确认核算项目编号为"' + projectNumbers + '"的数据项？').then(function() {
        return AccountProjectCost(projectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("核算完成");
      }).catch(() => {});
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
      this.ids = selection.map(item => item.projectCostId);
      this.projectNumbers = selection.map(item => item.proProject.projectNumber);
      this.projectIds = selection.map(item => item.projectId);
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目成本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectCostId = row.projectCostId || this.ids
      getProjectCost(projectCostId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目成本";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.projectCostId != null) {
            updateProjectCost(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProjectCost(this.form).then(response => {
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
      const projectCostIds = row.projectCostId || this.ids;
      this.$modal.confirm('是否确认删除项目成本编号为"' + projectCostIds + '"的数据项？').then(function() {
        return delProjectCost(projectCostIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/projectCost/export', {
        ...this.queryParams
      }, `projectCost_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
