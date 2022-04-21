<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="问卷名称" prop="paperTitle">
        <el-input
          v-model="queryParams.paperTitle"
          placeholder="请输入问卷名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="序言" prop="paperPreface">
        <el-input
          v-model="queryParams.paperPreface"
          placeholder="请输入序言"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束时间">
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
          v-hasPermi="['survey:paper:add']"
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
          v-hasPermi="['survey:paper:edit']"
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
          v-hasPermi="['survey:paper:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['survey:paper:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paperList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="问卷编号" align="center" prop="id" />
      <el-table-column label="问卷名称" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
            {{ scope.row.paperTitle }}
        </template>
      </el-table-column>
      <el-table-column label="序言" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.paperPreface }}
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row.id)"
            v-hasPermi="['survey:paper:query']"
          >预览</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['survey:paper:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['survey:paper:remove']"
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

    <!-- 添加或修改调查问卷模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="问卷名称" prop="paperTitle">
          <el-input v-model="form.paperTitle" placeholder="请输入问卷名称" />
        </el-form-item>
        <el-form-item label="序言" prop="paperPreface">
          <el-input v-model="form.paperPreface" placeholder="请输入序言" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
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

     <!-- 调查问卷展示 -->
    <el-dialog :title="title" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-form ref="paperDetailForm" :model="paperDetailForm">
        <h2 style="text-align: center">{{ paperDetailForm.paperTitle }}</h2>
<!--        <lable>{{ paperDetailForm.paperPreface }}</lable>-->
         <el-row>
           <el-col :span="24">
             <ul v-for="(question, index) in paperDetailForm.questions" :key="question.questionId">
              {{index + 1}}. {{question.questionTitle}}
              <div>
                <!--单选-->
                <el-radio-group size="small" v-if="question.answer === 0" v-model="radioList[question.questionId]">
                  <el-radio
                     v-for="(option, index) in question.options"
                    :key="option.optionId"
                    :label="option.optionId"
                     style="display:block;margin-left:10px;margin-top:10px;color: #929292;"
                  >{{index + 1}}. {{option.optionContent}}</el-radio>
                </el-radio-group>
                <!--多选-->
                <el-checkbox-group size="small" v-if="question.answer === 1" v-model="checkList[question.questionId]">
                  <el-checkbox
                     v-for="(option, index) in question.options"
                    :key="option.optionId"
                    :label="option.optionId"
                     style="display:block;margin-left:10px;margin-top:10px;color: #929292"
                  >{{index + 1}}. {{option.optionContent}}</el-checkbox>
                </el-checkbox-group>
                <!--自行输入-->
                 <el-input
                   v-if="question.answer === 2"
                   v-model="contentList[question.questionId]"
                   style="margin-left:10px;margin-top:10px"
                   type="textarea" placeholder="请输入内容" />
              </div>
             </ul>
           </el-col>
         </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPaperForm">提 交</el-button>
        <el-button @click="cancelPaper">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPaper, getPaper, delPaper, addPaper, updatePaper, exportPaper, getDetail } from "@/api/survey/paper";
import { addAnswerBatch } from "@/api/survey/answer";
export default {
  name: "Paper",
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
      // 调查问卷模板表格数据
      paperList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情
      detailOpen: false,
      // 多选列表数据
      checkList:{},
      // 单选列表数据
      radioList:{},
      // 自填数据
      contentList:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paperTitle: null,
        paperPreface: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      paperDetailForm:{},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    // this.checkboxFromt();
  },
  methods: {
    /** 查询调查问卷模板列表 */
    getList() {
      this.loading = true;
      listPaper(this.queryParams).then(response => {
        this.paperList = response.rows;
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
        id: null,
        paperTitle: null,
        paperPreface: null,
        startTime: null,
        endTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /**刷新问卷列表**/
    resetPaper(){
      this.resetForm("paperDetailForm");
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加调查问卷模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPaper(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改调查问卷模板";
      });
    },
    handleDetail(id){
      const params ={
        id: id
      }
      getDetail(params).then(response => {
        this.paperDetailForm = response.data;
        this.detailOpen = true;
        this.title = "调查问卷详情";
        for (let key in response.data.questions) {
          const question = response.data.questions[key];
          if(question.answer === 0){
            this.$set(this.radioList, question.questionId, null)
          }
          if(question.answer === 1){
            this.$set(this.checkList, question.questionId, [])
          }
          if(question.answer === 2){
            this.$set(this.contentList, question.questionId, null)
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaper(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaper(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitPaperForm(){
      let answerList = [];
      this.paperDetailForm.questions.forEach(questions => {
            // 单选循环选项
            if (questions.answer === 0) {
              let answer = {};
              const id = questions.questionId;
              const optionId = this.radioList[id];
              if (optionId != null) {
                answer.paperId = questions.paperId;
                answer.questionId = questions.questionId;
                answer.content = null;
                answer.optionId = optionId;
                answerList.push(answer);
              }
            // 多选从checkList中拿数据
            }else if(questions.answer === 1){
              const id = questions.questionId;
              const ops = this.checkList[id];
                ops.forEach(val => {
                  let answer = {};
                  answer.paperId = questions.paperId;
                  answer.questionId = questions.questionId;
                  answer.content = null;
                  answer.optionId = val;
                  answerList.push(answer);
                })
              // 自行填写
            }else if(questions.answer === 2) {
              let answer = {};
              const id = questions.questionId;
              const remark = this.contentList[id];
              if (remark != null) {
                answer.paperId = questions.paperId;
                answer.questionId = questions.questionId;
                answer.content = remark;
                answer.optionId = null;
                answerList.push(answer);
              }
            }
      });
      // 新增调查问卷回答
      addAnswerBatch(answerList).then(response => {
        this.$modal.msgSuccess("已成功提交");
        this.detailOpen = false;
      });
    },
    // 取消按钮
    cancelPaper() {
      this.detailOpen = false;
      this.resetPaper();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除调查问卷模板编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPaper(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有调查问卷模板数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPaper(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
