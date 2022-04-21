<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目编号" prop="questionId">
        <el-input
          v-model="queryParams.questionId"
          placeholder="请输入题目编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选项编号" prop="optionId">
        <el-input
          v-model="queryParams.optionId"
          placeholder="请输入选项编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户编号" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户编号"
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
          v-hasPermi="['survey:answer:add']"
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
          v-hasPermi="['survey:answer:edit']"
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
          v-hasPermi="['survey:answer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['survey:answer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="answerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="填写编号" align="center" prop="answerId" />
      <el-table-column label="问卷标题" align="center" prop="paperTitle" />
      <el-table-column label="用户名称" align="center" prop="nickName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['survey:answer:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >填写详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['survey:answer:remove']"
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

    <!-- 添加或修改问卷填写对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目编号" prop="questionId">
          <el-input v-model="form.questionId" placeholder="请输入题目编号" />
        </el-form-item>
        <el-form-item label="选项编号" prop="optionId">
          <el-input v-model="form.optionId" placeholder="请输入选项编号" />
        </el-form-item>
        <el-form-item label="用户编号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户编号" />
        </el-form-item>
        <el-form-item label="自行填写内容">
          <editor v-model="form.content" :min-height="192"/>
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
                <el-radio-group size="small" v-if="question.answer === 0" v-model="question.selected">
                  <el-radio
                    v-for="(option, index) in question.options"
                    :key="option.optionId"
                    :label="option.optionId"
                    disabled
                    style="display:block;margin-left:10px;margin-top:10px"
                  >{{index + 1}}. {{option.optionContent}}</el-radio>
                </el-radio-group>
                <!--多选-->
                <el-checkbox-group size="small" v-if="question.answer === 1" v-model="question.checkList">
                  <el-checkbox
                    v-for="(option, index) in question.options"
                    :key="option.optionId"
                    :label="option.optionId"
                    disabled
                    style="display:block;margin-left:10px;margin-top:10px"
                  >{{index + 1}}. {{option.optionContent}}</el-checkbox>
                </el-checkbox-group>
                <!--自行输入-->
                <el-input
                  v-if="question.answer === 2"
                  v-model="question.remark"
                  disabled="true"
                  style="margin-left:10px;margin-top:10px"
                  type="textarea" placeholder="请输入内容" />
              </div>
            </ul>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { listAnswer, getAnswer, delAnswer, addAnswer, updateAnswer, exportAnswer, allList, getAnswerDetail } from "@/api/survey/answer";
import Editor from '@/components/Editor';
import {getDetail} from "@/api/survey/paper";

export default {
  name: "Answer",
  components: {
    Editor,
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
      // 问卷填写表格数据
      answerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      detailOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionId: null,
        optionId: null,
        userId: null,
        content: null
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
  },
  methods: {
    /** 查询问卷填写列表 */
    getList() {
      this.loading = true;
      // listAnswer(this.queryParams).then(response => {
      //   this.answerList = response.rows;
      //   this.total = response.total;
      //   this.loading = false;
      // });
      allList(this.queryParams).then(response => {
        this.answerList = response.rows;
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
        answerId: null,
        questionId: null,
        optionId: null,
        userId: null,
        createTime: null,
        content: null
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
      this.ids = selection.map(item => item.answerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加问卷填写";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const answerId = row.answerId || this.ids
      getAnswer(answerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改问卷填写";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.answerId != null) {
            updateAnswer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAnswer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDetail(row){
      getAnswerDetail(row.paperId,row.userId).then(response => {
        this.paperDetailForm = response.data;
        this.detailOpen = true;
        this.title = "调查问卷详情";
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const answerIds = row.answerId || this.ids;
      this.$confirm('是否确认删除问卷填写编号为"' + answerIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAnswer(answerIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有问卷填写数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAnswer(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
<style>
/* 选中后的字体颜色 */
.el-radio__input.is-checked + .el-radio__label {
  color: #409eff !important;
}
/*!* 选中后小圆点的颜色 *!*/
.el-radio__input.is-checked .el-radio__inner {
  background: #409eff !important;
  border-color: #409eff !important;
}
.el-radio__input.is-checked .el-radio__inner:after {
  background-color: #fff !important;
}
/* 选中后的字体颜色 */
.el-checkbox__input.is-checked + .el-checkbox__label {
  color: #409eff !important;
}
/*!* 选中后小圆点的颜色 *!*/
.el-checkbox__input.is-checked .el-checkbox__inner {
  background: #409eff !important;
  border-color: #409eff !important;
}
</style>
