<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目名称" prop="questionTitle">
        <el-input
          v-model="queryParams.questionTitle"
          placeholder="请输入题目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别" prop="status">
        <el-select
          v-model="queryParams.answer"
          placeholder="题目类别"
          clearable
          size="small"
          style="width: 240px"
        >
        <el-option
          v-for="dict in typeOptions"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictValue"
        />
        </el-select>
      </el-form-item>
      <el-form-item label="题目编号" prop="paperId">
        <el-input
          v-model="queryParams.paperId"
          placeholder="请输入题目编号"
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
          v-hasPermi="['survey:question:add']"
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
          v-hasPermi="['survey:question:edit']"
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
          v-hasPermi="['survey:question:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['survey:question:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目编号"  width="100" align="center" prop="questionId" />
      <el-table-column label="问卷标题" align="center" prop="paperTitle" />
      <el-table-column label="题目名称" align="center" prop="questionTitle" />
      <el-table-column label="题目类别" prop="answer" :formatter="typeFormat" width="80" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['survey:question:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['survey:question:remove']"
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

    <!-- 添加或修改问卷题目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目名称" prop="questionTitle">
          <el-input v-model="form.questionTitle" placeholder="请输入题目名称" />
        </el-form-item>
        <el-form-item label="类别" prop="answer">
          <el-select v-model="form.answer" placeholder="请选择">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问卷标题" prop="paperId">
          <el-select v-model="form.paperId" placeholder="请选择问卷标题">
            <el-option
              v-for="item in paperOptions"
              :key="item.id"
              :label="item.paperTitle"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider></el-divider>
        <!--表单添加-->
        <div v-show="form.answer == 0 || form.answer == 1">
        <el-button size="mini" plain type="success" @click="addListRow()">新增选项</el-button>
          <el-table :data="form.options" border style="width:100%;margin-top: 15px;" max-height="270" :header-cell-class-name='addHeaderCellClassName' >
            <el-table-column label="问题内容" width="320" align="center">
                <template slot-scope="scope">
                  <el-form-item
                    label-width="0"
                    :prop="'options.' + scope.$index + '.optionContent'"
                    :rules="{ required: true, message: '选项内容不能为空', trigger: 'blur' }">
                    <el-input  type="textarea" v-model="scope.row.optionContent" placeholder="请输入选项内容"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="排序" width="100" align="center">
              <template slot-scope="scope">
                 <el-form-item label-width="0">
                    <el-input v-model="scope.row.optionOrder" type="number" :min="0"></el-input>
                 </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="备注" width="255" align="center">
              <template slot-scope="scope">
                <el-form-item label-width="0">
                    <el-input type="textarea" v-model="scope.row.remark" placeholder="请输入备注"></el-input>
                 </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80" align="center">
              <template slot-scope="scope">
                 <el-form-item label-width="0">
                    <el-button  size="mini" type="text" icon="el-icon-delete" @click="handTableDelete(scope.$index,scope.row.optionId)">删除</el-button>
                 </el-form-item>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestion, getQuestion, delQuestion, addQuestion, updateQuestion, exportQuestion } from "@/api/survey/question";
import { getPaper } from "@/api/survey/paper";
import { delOptions } from "@/api/survey/options";
export default {
  name: "Question",
  components: {
  },
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
      // 问卷题目表格数据
      questionList: [],
      // 状态数据字典
      typeOptions: [],
      // 问题列表
      paperOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // show: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionTitle: null,
        answer: null,
        paperId: null
      },
      // 表单参数
      form: {
      },
      // 表单校验
       // 表单校验
      rules: {
        questionTitle: [
          { required: true, message: "题目标题不能为空", trigger: "blur" }
        ],
        answer: [
          { required: true, message: "请选择选项类别", trigger: "change" }
        ],
        paperId: [
          { required: true, message: "请选择问卷标题", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("question_type").then(response => {
      this.typeOptions = response.data;
    });
    getPaper().then(response => {
      this.paperOptions = response.data;
    });
  },
  methods: {
    /** 查询问卷题目列表 */
    getList() {
      this.loading = true;
      listQuestion(this.queryParams).then(response => {
        this.questionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // paperFormat(row, column) {
    //   this.paperOptions.forEach( k => {
    //     if(row.paperId === k.id ){
    //       // return k.paperTitle
    //       this.questionList.forEach( j =>{
    //         j.paperId = k.paperTitle
    //       })
    //       console.log(this.questionList)
    //     }
    //   });
    // },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        questionId: null,
        questionTitle: null,
        answer: undefined,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        paperId: undefined,
        // 选项列表
        options: [],
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 类别字典翻译
    typeFormat(row, column) {
      if (row.menuType == "F") {
        return "";
      }
      return this.selectDictLabel(this.typeOptions, row.answer);
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.questionId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加问卷题目";
      // getPaper().then(response => {
      //     this.paperOptions = response.data;
      //     this.open = true;
      //     this.title = "添加问卷题目";
      // });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const questionId = row.questionId || this.ids
      // getPaper().then(response => {
      //     this.paperOptions = response.data;
      // });
      getQuestion(questionId).then(response => {
        this.form = response.data;
        this.form.answer = response.data.answer + ''
        this.paperOptions.id = this.form.paperId + ''
        this.open = true;
        this.title = "修改问卷题目";
      });
    },
    //点击加号添加一行数据
    addListRow() {
      this.form.options.push({
        "optionContent":"",
        "optionOrder": 0,
        "remark":""
      });
    },
    handTableDelete(index,optionId) {
      const taht = this
      this.$confirm('是否确认删除题目选项编号为"' + optionId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          taht.form.options.splice(index, 1);
          return delOptions(optionId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.questionId != null) {
            updateQuestion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQuestion(this.form).then(response => {
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
      const questionIds = row.questionId || this.ids;
      this.$confirm('是否确认删除问卷题目编号为"' + questionIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQuestion(questionIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有问卷题目数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportQuestion(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    addHeaderCellClassName({row, column, rowIndex, columnIndex}){
      if(columnIndex===0 || columnIndex===1){
        return 'requiredclass'
      }
    }
  }
};
</script>
<style>
  .requiredclass>div.cell::before{
    content: "*";
    color: #f56c6c;
    margin-right: 4px;
  }
</style>
