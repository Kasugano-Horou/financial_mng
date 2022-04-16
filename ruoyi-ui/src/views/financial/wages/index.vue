<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年月" prop="issuingDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.issuingDate"
          type="month"
          value-format="yyyy-MM"
          placeholder="请选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="员工工号" prop="empCode">
        <el-input
          v-model="queryParams.empCode"
          placeholder="请输入员工工号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="员工姓名" prop="empName">
        <el-input
          v-model="queryParams.empName"
          placeholder="请输入员工姓名"
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
          v-hasPermi="['financial:wages:add']"
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
          v-hasPermi="['financial:wages:edit']"
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
          v-hasPermi="['financial:wages:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['financial:wages:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wagesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工工号" align="center" prop="empCode" />
      <el-table-column label="员工姓名" align="center" prop="empName" />
      <el-table-column label="年月" align="center" prop="issuingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issuingDate, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="基础工资" align="center" prop="baseWages" />
      <el-table-column label="奖金" align="center" prop="bonus" />
      <el-table-column label="五险" align="center" prop="insurance" />
      <el-table-column label="住房公积金" align="center" prop="houseFund" />
      <el-table-column label="扣款项" align="center" prop="deducted" />
      <el-table-column label="其他" align="center" prop="others" />
      <el-table-column label="所得税" align="center" prop="tax" />
      <el-table-column label="实发工资" align="center" prop="finalWage" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['financial:wages:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['financial:wages:remove']"
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

    <!-- 添加或修改工资对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="员工工号" prop="empCode">
          <el-input v-model="form.empCode" placeholder="请输入员工工号" />
        </el-form-item>
        <el-form-item label="员工姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入员工姓名" />
        </el-form-item>
        <el-form-item label="年月" prop="issuingDate">
          <el-date-picker clearable size="small"
            v-model="form.issuingDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="请选择年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="基础工资" prop="baseWages">
          <el-input v-model="form.baseWages" placeholder="请输入基础工资" />
        </el-form-item>
        <el-form-item label="奖金" prop="bonus">
          <el-input v-model="form.bonus" placeholder="请输入奖金" />
        </el-form-item>
        <el-form-item label="五险" prop="insurance">
          <el-input v-model="form.insurance" placeholder="请输入五险" />
        </el-form-item>
        <el-form-item label="住房公积金" prop="houseFund">
          <el-input v-model="form.houseFund" placeholder="请输入住房公积金" />
        </el-form-item>
        <el-form-item label="扣款项" prop="deducted">
          <el-input v-model="form.deducted" placeholder="请输入扣款项" />
        </el-form-item>
        <el-form-item label="其他" prop="others">
          <el-input v-model="form.others" placeholder="请输入其他" />
        </el-form-item>
        <el-form-item label="所得税" prop="tax">
          <el-input v-model="form.tax" placeholder="请输入所得税" />
        </el-form-item>
        <el-form-item label="实发工资" prop="finalWage">
          <el-input v-model="form.finalWage" placeholder="请输入实发工资" />
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
import { listWages, getWages, delWages, addWages, updateWages } from "@/api/financial/wages";

export default {
  name: "Wages",
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
      // 工资表格数据
      wagesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empCode: null,
        empName: null,
        issuingDate: null,
        baseWages: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empCode: [
          { required: true, message: "员工工号不能为空", trigger: "blur" }
        ],
        empName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
        issuingDate: [
          { required: true, message: "年月不能为空", trigger: "blur" }
        ],
        baseWages: [
          { required: true, message: "基础工资不能为空", trigger: "blur" }
        ],
        bonus: [
          { required: true, message: "奖金不能为空", trigger: "blur" }
        ],
        insurance: [
          { required: true, message: "五险不能为空", trigger: "blur" }
        ],
        houseFund: [
          { required: true, message: "住房公积金不能为空", trigger: "blur" }
        ],
        deducted: [
          { required: true, message: "扣款项不能为空", trigger: "blur" }
        ],
        others: [
          { required: true, message: "其他不能为空", trigger: "blur" }
        ],
        tax: [
          { required: true, message: "所得税不能为空", trigger: "blur" }
        ],
        finalWage: [
          { required: true, message: "实发工资不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.queryParams.issuingDate = this.dateFormat("yyyy-MM");
    this.getList();
  },
  methods: {
    /** 查询工资列表 */
    getList() {
      this.loading = true;
      listWages(this.queryParams).then(response => {
        this.wagesList = response.rows;
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
        wageId: null,
        empCode: null,
        empName: null,
        issuingDate: null,
        baseWages: null,
        bonus: null,
        insurance: null,
        houseFund: null,
        deducted: null,
        others: null,
        tax: null,
        finalWage: null,
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
      this.queryParams.issuingDate = this.dateFormat("yyyy-MM");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.wageId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工资";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wageId = row.wageId || this.ids
      getWages(wageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工资";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wageId != null) {
            updateWages(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWages(this.form).then(response => {
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
      const wageIds = row.wageId || this.ids;
      this.$modal.confirm('是否确认删除工资编号为"' + wageIds + '"的数据项？').then(function() {
        return delWages(wageIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('financial/wages/export', {
        ...this.queryParams
      }, `wages_${new Date().getTime()}.xlsx`)
    },


    dateFormat(fmt){ //author: meizz  
      var date = new Date(); 
      var o = {   
        "M+" : date.getMonth()+1,                 //月份   
        "d+" : date.getDate(),                    //日   
        "h+" : date.getHours(),                   //小时   
        "m+" : date.getMinutes(),                 //分   
        "s+" : date.getSeconds(),                 //秒   
        "q+" : Math.floor((date.getMonth()+3)/3), //季度   
        "S"  : date.getMilliseconds()             //毫秒   
      };   
      if(/(y+)/.test(fmt))   
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substring(4 - RegExp.$1.length));   
      for(var k in o)   
        if(new RegExp("("+ k +")").test(fmt))   
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substring((""+ o[k]).length)));   
      return fmt;   
    } 
  }
};


</script>