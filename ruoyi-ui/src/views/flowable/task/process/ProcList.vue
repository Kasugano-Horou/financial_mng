<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="流程类别" prop="category">
        <el-select v-model="queryParams.category" clearable size="small" placeholder="请选择流程类别">
          <el-option v-for="dict in procCategoryList" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="deployTime">
        <el-date-picker clearable size="small" v-model="queryParams.deployTime" type="date"
                        value-format="yyyy-MM-dd" placeholder="选择时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                  @click="handleDelete" v-hasPermi="['flowable:instance:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini"
                  @click="handleExport" v-hasPermi="['system:deployment:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="procList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程编号" align="center" prop="procInsId" :show-overflow-tooltip="true" width="120"/>
      <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true" width="120"/>
      <el-table-column label="流程类别" align="center" prop="category" width="100px" />
      <el-table-column label="流程版本" align="center" width="80px">
        <template slot-scope="scope">
          <el-tag size="medium" >v{{ scope.row.procDefVersion }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="流程状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.finishTime == null" size="mini">进行中</el-tag>
          <el-tag type="success" v-if="scope.row.finishTime != null" size="mini">已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" width="180"/>
      <el-table-column label="当前节点" align="center" prop="taskName"/>
      <el-table-column label="办理" align="center">
        <template slot-scope="scope">
          <label v-if="scope.row.assigneeName">{{scope.row.assigneeName}} <el-tag type="info" size="mini">{{scope.row.deptName}}</el-tag></label>
          <label v-if="scope.row.candidate">{{scope.row.candidate}}</label>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="120">
        <template slot-scope="scope">
          <el-dropdown>
            <span class="el-dropdown-link">更多操作<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-tickets" @click.native="handleFlowRecord(scope.row)">详情</el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="handleDelete(scope.row)"
                    v-hasPermi="['flowable:instance:remove']">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import {exportDeployment} from "@/api/flowable/finished";
import {queryProcessList, delProcIns} from "@/api/flowable/process";
export default {
  name: "ProcList",
  components: {

  },
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      processTotal:0,
      // 流程列表数据
      procList: [],
      definitionList:[],
      // 查询参数
      queryParams: {
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
      procCategoryList: []    //流程类别列表
    };
  },
  created() {
    this.getList();
    this.getProcCategoryList();
  },
  methods: {
    getProcCategoryList() {
      this.getDicts("sys_process_category").then(resp => {
        this.procCategoryList = resp.data;
      });
    },
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      var params = {
        category: this.queryParams.category ? this.queryParams.category : null,
        pageNo: this.queryParams.pageNo,
        pageSize: this.queryParams.pageSize
      }
      queryProcessList(params).then(resp => {
        this.procList = resp.data.list;
        this.total = resp.data.size;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procInsId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 流程详情 */
    handleFlowRecord(row) {
      this.$router.push({path: '/flowable/task/process/detail',
        query: {
          deployId: row.deployId,
          procInsId: row.procInsId,
          taskId: row.taskId
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.procInsId || this.ids;
      this.$confirm('是否确认删除编号为"' + ids + '"的流程实例?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delProcIns(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {

      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有流程定义数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportDeployment(queryParams);
      }).then(resp => {
        this.download(resp.msg);
      });
    }
  }
};
</script>

