<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--项目数据-->
      <el-col :span="8" :xs="24">
        <div class="head-container">
          <el-input
            v-model="search"
            placeholder="请输入 项目编号/项目名称/项目负责人"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">



          <el-table
            :data="tables"
            style="width: 97%"
            :row-class-name="tableRowClassName"
            @row-click="onClickItem">
            <el-table-column
              type="String"
              prop="projectNumber"
              label="项目编号"
              width="155"
            >
            </el-table-column>
            <el-table-column
              type="String"
              prop="projectName"
              label="项目名称"
              width="230"
            >
            </el-table-column>
            <el-table-column
              type="String"
              prop="leader"
              label="负责人"
              width="80">
            </el-table-column>
            <el-table-column label="项目状态" align="center" prop="status">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.pro_project_status" :value="scope.row.status"/>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="projectTotal>0"
            :total="projectTotal"
            :page.sync="projectQueryParams.pageNum"
            :limit.sync="projectQueryParams.pageSize"
            @pagination="getProjectList"
          />



        </div>
      </el-col>
      <!--工时数据-->
      <el-col :span="16" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="项目ID" prop="projectId">
            <el-input
              v-model="queryParams.projectId"
              placeholder="请输入项目ID"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="员工ID" prop="empId">
            <el-input
              v-model="queryParams.empId"
              placeholder="请输入员工ID"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="项目工时" prop="manhour">
            <el-input
              v-model="queryParams.manhour"
              placeholder="请输入项目工时"
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
              v-hasPermi="['project:manhour:add']"
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
              v-hasPermi="['project:manhour:edit']"
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
              v-hasPermi="['project:manhour:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['project:manhour:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getManhourList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="manhourList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="项目名称" align="center" key="projectName" prop="proProject.projectName" />
          <el-table-column label="员工姓名" align="center" key="empName" prop="sysEmp.empName" />
          <el-table-column label="项目工时" align="center" prop="manhour" />
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['project:manhour:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['project:manhour:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getManhourList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改工时对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择项目" filterable style="width:350px">
            <el-option
              v-for="project in tableData"
              :key="project.projectId"
              :label="project.projectName"
              :value="project.projectId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工" prop="empId">
          <el-select v-model="form.empId" placeholder="请选择员工" filterable style="width:350px">
            <el-option
              v-for="emp in empList"
              :key="emp.empId"
              :label="emp.empName"
              :value="emp.empId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目工时" prop="manhour">
          <el-input v-model="form.manhour" placeholder="请输入项目工时" />
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
  </div>
</template>

<script>
import { listManhour, listManhourByProjectId, getManhour, delManhour, addManhour, updateManhour } from "@/api/project/manhour";
import { tableDataProject} from "@/api/project/projectInfo";
import { listEmp } from "@/api/system/emp";

export default {
  name: "Manhour",
  dicts: ['pro_project_type','pro_project_status'], 
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 项目总条数
      projectTotal: 0,
      // 工时表格数据
      manhourList: [],
      // 员工表格数据
      empList: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //当前选择项目ID
      projectId: undefined,
      //项目名称搜索
      search: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 15,
        projectId: undefined,
        empId: undefined,
        manhour: undefined,
      },
      projectQueryParams: {
        pageNum: 1,
        pageSize: 10,
        projectNumber: null,
        projectName: null,
        contractId: null,
        projectType: null,
        status: null,
        parta: null,
        leader: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectId: [
          { required: true, message: "项目ID不能为空", trigger: "blur" }
        ],
        empId: [
          { required: true, message: "员工ID不能为空", trigger: "blur" }
        ],
        manhour: [
          { required: true, message: "项目工时不能为空", trigger: "blur" }
        ],
      },
      tableData: [],

    };
  },
  watch: {
    // // 根据名称筛选部门树
    // ProjectName(val) {
    //   this.$refs.filterTable.clearFilter(val);
    // }
  },

  computed:{
    tables:function(){
      const search=this.search;
      if(search){
        return  this.tableData.filter(dataNews =>{
          return Object.keys(dataNews).some(function(key){
            return String(dataNews[key]).toLowerCase().indexOf(search) > -1
          })
        })
      }
      return this.tableData
    },
  },

	   
  created() {
    this.getProjectList();
    this.getManhourList();
    this.getEmpList();
  },

  methods: {

    onClickItem(row) {
      this.projectId = row.projectId;

      this.getManhourListByProjectId();
    },
    
    // formatcolor (Value) {
    //   console.log(Value);
    //   const projectName = Value.projectName;
    //   console.log(projectName);
    //   if (projectName.indexOf(this.search) !== -1 && this.search !== '') {
    //     return  projectName.replace(this.search, '<div v-html="rawHtml" style="color:red" >' + this.search + '</div>');
    //   } else {
    //     return projectName;
    //   }
    // },
    

    tableRowClassName({row, rowIndex}) {
      if (rowIndex%4 === 1) {
        return 'warning-row';
      } else if (rowIndex%4 === 3) {
        return 'success-row';
      }
      return '';
    },


    /** 查询项目列表 */
    getProjectList() {
      tableDataProject(this.projectQueryParams).then(response => {
        this.tableData = response.rows;
        console.log("tableData");
        console.log(this.tableData);
        this.projectTotal = response.total;
      });
    },

    /** 根据项目ID查工时 */
    getManhourListByProjectId() {
      this.loading = true;
      listManhourByProjectId(this.projectId).then(response => {
        this.manhourList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },



    /** 查询工时列表 */
    getManhourList() {
      this.loading = true;
      if(this.projectId!=null){
        this.queryParams.projectId = this.projectId;
      }
      listManhour(this.queryParams).then(response => {
        this.manhourList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询员工列表 */
    getEmpList(){
      listEmp().then(response => {
        this.empList = response.rows;
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
        manhourId: null,
        projectId: null,
        empId: null,
        manhour: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.projectId = null;
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getManhourList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.reset();
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.manhourId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if(this.projectId!=null&&this.projectId!=''){
        this.form.projectId = this.projectId;
      }
      this.open = true;
      this.title = "添加工时";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const manhourId = row.manhourId || this.ids
      getManhour(manhourId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工时";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.manhourId != null) {
            updateManhour(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getManhourList();
            });
          } else {
            addManhour(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getManhourList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const manhourIds = row.manhourId || this.ids;
      this.$modal.confirm('是否确认删除工时编号为"' + manhourIds + '"的数据项？').then(function() {
        return delManhour(manhourIds);
      }).then(() => {
        this.getManhourList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/manhour/export', {
        ...this.queryParams
      }, `manhour_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>


<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>