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
      <!--员工数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="工号" prop="empCode">
            <el-input
              v-model="queryParams.empCode"
              placeholder="请输入工号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="姓名" prop="empName">
            <el-input
              v-model="queryParams.empName"
              placeholder="请输入姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
              <el-option
                v-for="dict in dict.type.sys_user_sex"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择员工状态" clearable size="small">
              <el-option
                v-for="dict in dict.type.sys_emp_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="入职日期" prop="hiredate">
            <el-date-picker clearable size="small"
              v-model="queryParams.hiredate"
              type="date"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择入职日期">
            </el-date-picker>
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
              v-hasPermi="['system:emp:add']"
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
              v-hasPermi="['system:emp:edit']"
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
              v-hasPermi="['system:emp:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:emp:import']"
            >导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:emp:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="empList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="工号" align="center" prop="empCode" width="100" />
          <el-table-column label="姓名" align="center" prop="empName" width="70" />
          <el-table-column label="性别" align="center" prop="sex" width="50">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
            </template>
          </el-table-column>
          <el-table-column label="部门" align="center" prop="dept.deptName" width="100px"/>
          <el-table-column label="状态" align="center" prop="status" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_emp_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="学历" align="center" prop="education" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_emp_education" :value="scope.row.education"/>
            </template>
          </el-table-column>
          <el-table-column label="电话" align="center" prop="phonenumber" width="130" />
          <el-table-column label="邮箱" align="center" prop="email" width="160"/>
          <el-table-column label="住址" align="center" prop="address" />
          <el-table-column label="入职日期" align="center" prop="hiredate" width="100">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.hiredate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="离职日期" align="center" prop="termdate" width="100">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.termdate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="基础工资" align="center" prop="baseWages" width="80" />
          <el-table-column label="住房公积金基数" align="center" prop="houseWages" width="120"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:emp:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:emp:remove']"
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
      </el-col>
    </el-row>

    <!-- 添加或修改员工对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工号" prop="empCode">
          <el-input v-model="form.empCode" placeholder="请输入工号" />
        </el-form-item>
        <el-form-item label="姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_emp_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学历" prop="education">
          <el-select v-model="form.education" placeholder="请选择学历">
            <el-option
              v-for="dict in dict.type.sys_emp_education"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="form.address" placeholder="请输入住址" />
        </el-form-item>
        <el-form-item label="归属部门" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
        <!-- <el-form-item label="岗位">
          <el-select v-model="form.postIds" multiple placeholder="请选择岗位">
              <el-option
                v-for="item in postOptions"
                :key="item.postId"
                :label="item.postName"
                :value="item.postId"
                :disabled="item.status == 1"
               ></el-option>
             </el-select>
          </el-form-item> -->
        <el-form-item label="入职日期" prop="hiredate">
          <el-date-picker clearable size="small"
            v-model="form.hiredate"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择入职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离职日期" prop="termdate">
          <el-date-picker clearable size="small"
            v-model="form.termdate"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择离职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="基础工资" prop="baseWages">
          <el-input v-model="form.baseWages" placeholder="请输入基础工资" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 员工导入对话框 -->
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
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的员工数据
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
  </div>
</template>

<script>
import { listEmp, getEmp, delEmp, addEmp, updateEmp } from "@/api/system/emp";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Emp",
  dicts: ['sys_emp_education', 'sys_emp_status', 'sys_user_sex'],
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
      // 员工表格数据
      empList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: [],
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empCode: undefined,
        empName: undefined,
        sex: undefined,
        status: undefined,
        deptId: undefined,
        hiredate: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 员工导入参数
      upload: {
        // 是否显示弹出层（员工导入）
        open: false,
        // 弹出层标题（员工导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的员工数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/emp/importData"
      },
      // 表单校验
      rules: {
        empCode: [
          { required: true, message: "工号不能为空", trigger: "blur" }
        ],
        empName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        sex: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" }
        ],
        hiredate: [
          { required: true, message: "入职日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
    watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询员工列表 */
    getList() {
      this.loading = true;
      listEmp(this.queryParams).then(response => {
        this.empList = response.rows;
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
        empId: undefined,
        empCode: undefined,
        empName: undefined,
        sex: undefined,
        status: "0",
        //postIds: [],
        education: undefined,
        phonenumber: undefined,
        email: undefined,
        address: undefined,
        deptId: undefined,
        hiredate: undefined,
        termdate: undefined,
        baseWages: undefined,
        houseWages: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.empId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getEmp().then(response => {
        //console.log(response.posts);
        //this.postOptions = response.posts;
        this.open = true;
        this.title = "添加员工";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const empId = row.empId || this.ids
      getEmp(empId).then(response => {
        this.form = response.data;
        //this.postOptions = response.posts;
        //this.form.postIds = response.postIds;
        this.open = true;
        this.title = "修改员工";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.empId != null) {
            updateEmp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmp(this.form).then(response => {
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
      const empIds = row.empId || this.ids;
      this.$modal.confirm('是否确认删除员工编号为"' + empIds + '"的数据项？').then(function() {
        return delEmp(empIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/emp/export', {
        ...this.queryParams
      }, `emp_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "员工导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/emp/importTemplate', {
      }, `emp_template_${new Date().getTime()}.xlsx`)
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
    }
  }
};
</script>
