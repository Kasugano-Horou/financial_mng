<!--我的流程-->
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
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                  @click="handleAdd" v-hasPermi="['flowable:deployment:add']">新增流程</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                  @click="handleDelete" v-hasPermi="['flowable:deployment:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini"
                  @click="handleExport" v-hasPermi="['flowable:deployment:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="myProcessList" border @selection-change="handleSelectionChange">
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
              <el-dropdown-item icon="el-icon-circle-close" @click.native="handleStop(scope.row)">取消申请</el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="handleDelete(scope.row)"
                    v-hasPermi="['flowable:deployment:remove']">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 发起流程 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body v-drag>
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
      <pagination v-show="processTotal>0" :total="processTotal" :page.sync="queryParams.pageNo"
            :limit.sync="queryParams.pageSize" @pagination="listDefinition" />
    </el-dialog>
  </div>
</template>

<script>
import {exportDeployment} from "@/api/flowable/finished";
import {myProcessList, stopProcess, delProcIns} from "@/api/flowable/process";
import {listDefinition} from "@/api/flowable/definition";
export default {
  name: "MyProcess",
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
      // 我发起的流程列表数据
      myProcessList: [],
      // 弹出层标题
      title: "",
      // 是否显示发起流程弹出层
      open: false,
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
      myProcessList(params).then(resp => {
        this.myProcessList = resp.data.list;
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
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.title = "发起流程";
      this.listDefinition();
    },
    listDefinition() {
      listDefinition(this.queryParams).then(resp => {
        this.definitionList = resp.data.list;
        this.processTotal = resp.data.size;
        this.processLoading = false;
      });
    },
    /**  发起流程申请 */
    handleStartProcess(def) {
      this.$router.push({path: '/flowable/task/start',
        query: {
          deployId: def.deploymentId,
          procDefId: def.id
        }
      });
    },
    /**  取消流程申请 */
    handleStop(row) {
      const params = {
        instanceId: row.procInsId
      }
      this.$confirm('是否确定取消编号为"' + row.procInsId + '"的流程实例?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        stopProcess(params).then(res => {
          this.$modal.msgSuccess(res.msg);
          this.getList();
        }).catch(() => {

        });
      }).catch(() => {

      });
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

