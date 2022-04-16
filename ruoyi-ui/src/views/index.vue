<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :span="24">
        <h1>常用功能</h1>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="24">
        <!-- <el-button icon="el-icon-edit">
          录凭证
        </el-button>
        <el-button icon="el-icon-search">
          查凭证
        </el-button>
        <el-button icon="el-icon-search">
          明细账
        </el-button>
        <el-button icon="el-icon-search">
          余额表
        </el-button> -->
        <el-button
          icon="el-icon-document"
          @click="toPage('Contract')"
          v-hasPermi="['system:contract:list']"
        >合同管理</el-button>
        <el-button
          icon="el-icon-box"
          @click="toPage('Reimburse')"
          v-hasPermi="['system:reimburse:list']"
        >报销管理</el-button>
        <el-button
          icon="el-icon-copy-document"
          @click="toPage('ProjectInfo')"
          v-hasPermi="['system:projectInfo:list']"
        >项目管理</el-button>
        <el-button
          icon="el-icon-time"
          @click="toPage('Manhour')"
          v-hasPermi="['system:manhour:list']"
        >工时管理</el-button>
        
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20" >
      <el-col :span="24">
        <h1>服务指标</h1>
      </el-col>
      <el-col :sm="24"  :span="24" style="padding-left: 50px">
        <el-row>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>帐期总收入</span>
              </div>
              <div  class="text item">
                {{ account.generalIncome }}
              </div>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>管理成本</span>
              </div>
              <div  class="text item">
                {{ account.managenmentCost }}
              </div>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>人员成本</span>
              </div>
              <div  class="text item">
                {{ account.personnelCost }}
              </div>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>采购支出</span>
              </div>
              <div  class="text item">
                {{ account.procurementCost }}
              </div>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>其他支出</span>
              </div>
              <div  class="text item">
                {{ account.othersCost }}
              </div>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>税前利润</span>
              </div>
              <div  class="text item">
                {{ account.preTax }}
              </div>
              
            </el-card>
          </el-col>

        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :span="15" class="card-box">
        <h1>利润变化趋势（单位：元）</h1>
        <el-card>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div id="rate" style="width: 800px;height:400px;"/>
          </div>
        </el-card>
      </el-col>

      <el-col :span="9" class="card-box">
        <h1>1月利润结构分析（单位：元）</h1>
        <el-card>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div  style="height: 400px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listProjectCost } from "@/api/financial/projectCost";
import echarts from "echarts";
import { getCache } from "@/api/monitor/cache";


export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.1",


      // 总条数
      total: 0,
      
      // 项目成本表格数据
      projectCostList: [],
      //统计
      account: {
        //帐期总收入
        generalIncome: 0.00,
        //管理成本
        managenmentCost: 0.00,
        //人员成本
        personnelCost: 0.00,
        //采购支出
        procurementCost: 0.00,
        //其他支出
        othersCost: 0.00,
        //税前利润
        preTax: 0.00,
        
      },

       // 统计命令信息
      commandstats: null,
      // cache信息
      cache: [],

      

      //
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectNumber: null,
        projectName: null,
        contractNumber: null,
      },
    };
  },
  created() {
    this.getList();
    
  },
  mounted(){
    this.$nextTick(() => {
      this.showRate();
    });
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    /** 页面跳转 */
    toPage(name) {
      this.$router.push({
        name: name,
        params: {
        }
      })
    },
    /** 查询所有项目成本列表 */
    getList() {
      listProjectCost(this.queryParams).then(response => {
        this.accountProjects(response.rows);
        this.total = response.total;
      });
      
    },

    accountProjects(rows) {
      console.log(rows);
      rows.forEach(projectCost => {
        // console.log("wwww");
        // console.log(projectCost);
        // console.log("ttt");
        // console.log(typeof projectCost.preTax);
        this.account.generalIncome += projectCost.generalIncome;
        this.account.managenmentCost += projectCost.managenmentCost;
        this.account.personnelCost += projectCost.personnelCost;
        this.account.procurementCost += projectCost.procurementCost;
        this.account.othersCost += projectCost.othersCost;
        this.account.preTax += projectCost.preTax;
      });
      this.account.generalIncome =  (parseFloat(this.account.generalIncome) + 0.00).toFixed(2);
      this.account.managenmentCost =   (parseFloat(this.account.managenmentCost ) + 0.00).toFixed(2);
      this.account.personnelCost =  (parseFloat(this.account.personnelCost ) + 0.00).toFixed(2);
      this.account.procurementCost =  (parseFloat(this.account.procurementCost ) + 0.00).toFixed(2);
      this.account.othersCost = (parseFloat(this.account.othersCost ) + 0.00).toFixed(2);
      this.account.preTax = (parseFloat(this.account.preTax ) + 0.00).toFixed(2);
    },
    
    showRate(){
      listProjectCost(this.queryParams).then(response => {
        this.accountProjects(response.rows);
        this.total = response.total;

        var chartDom = document.getElementById('rate');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: ''
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['收入', '成本', '利润', '费用']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
                saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['21年07月', '21年08月', '21年09月', '21年10月', '21年11月', '21年12月', '22年01月', '22年02月', '22年03月']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
                name: '收入',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '成本',
                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: '利润',
                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
                name: '费用',
                type: 'line',
                stack: '总量',
                data: [320, 332, 301, 334, 390, 330, 320]
            },
          ]
        };

        option && myChart.setOption(option);
      });

      
    }





  },
};


</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
.text {
  font-size: 16px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 180px;
  height: 110px;
}
</style>

