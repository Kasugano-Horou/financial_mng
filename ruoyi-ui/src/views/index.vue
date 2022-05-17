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
          icon="el-icon-s-unfold"
          @click="toPage('Todo')"
          
        >待办任务</el-button>
        <el-button
          icon="el-icon-document"
          @click="toPage('Contract')"
          v-hasPermi="['financial:contract:list']"
        >合同管理</el-button>
        <el-button
          icon="el-icon-box"
          @click="toPage('Reimburse')"
          v-hasPermi="['system:reimburse:list']"
        >报销管理</el-button>
        <el-button
          icon="el-icon-copy-document"
          @click="toPage('ProjectInfo')"
          v-hasPermi="['project:projectInfo:list']"
        >项目管理</el-button>
        <el-button
          icon="el-icon-time"
          @click="toPage('Manhour')"
          v-hasPermi="['project:manhour:list']"
        >工时管理</el-button>
        
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20" v-hasPermi="['financial:contract:list']">
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
                <span>项目人员成本</span>
              </div>
              <div  class="text item">
                {{ account.personnelCost }}
              </div>
            </el-card>
          </el-col>
          <!-- <el-col :span="4">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>总发放工资</span>
              </div>
              <div  class="text item">
                {{ account.totalWages }}
              </div>
            </el-card>
          </el-col> -->
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
                <span>总计利润</span>
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
    <el-row :gutter="20" v-hasPermi="['financial:contract:list']">
      <el-col :span="15" class="card-box">
        <h1>利润变化趋势（单位：元）</h1>
        <el-card>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div id="rate" style="width: 800px;height:400px;"/>
          </div>
        </el-card>
      </el-col>

      <el-col :span="9" class="card-box">
        <h1>20{{ month }}利润结构分析（单位：元）</h1>
        <el-card>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div id="rateStructure" style="height: 400px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listProjectCost, listRate } from "@/api/financial/projectCost";
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
      // 统计
      account: {
        // 帐期总收入
        generalIncome: 0.00,
        // 管理成本
        managenmentCost: 0.00,
        // 人员成本
        personnelCost: 0.00,
        // 总发放工资
        totalWages: 0.00,
        // 采购支出
        procurementCost: 0.00,
        // 其他支出
        othersCost: 0.00,
        // 总计利润
        preTax: 0.00,
        
      },


      month:null,
      // 利润分析数据
      rateDate: {
        month : ['2021-09', '2021-10', '2021-11', '2021-12', '2022-01', '2022-02', '2022-03', '2022-04', '2022-05'],
        // var personnel_cost = [0, 0, 0, 0, 0, 0, 0, 0, 0],
        wages_cost : [0, 0, 0, 0, 0, 0, 0, 0, 0],
        general_income : [0, 0, 0, 0, 0, 0, 0, 0, 0],
        procurement_cost : [0, 0, 0, 0, 0, 0, 0, 0, 0],
        others_cost : [0, 0, 0, 0, 0, 0, 0, 0, 0],
        business_tax : [0, 0, 0, 0, 0, 0, 0, 0, 0],
        pre_tax : [0, 0, 0, 0, 0, 0, 0, 0, 0],
      },
      // 利润结构数据
      rateStructureDate: {
        // 帐期总收入
        generalIncome: 0.00,
        // 管理成本
        managenmentCost: 0.00,
        // 人员成本
        personnelCost: 0.00,
        // 发放工资
        wagesCost: 0.00,
        // 采购支出
        procurementCost: 0.00,
        // 其他支出
        othersCost: 0.00,
        // 税
        tax: 0.00,
        // 总计利润
        preTax: 0.00,
        // 总计利润绝对值
        preTaxx: 0.00,
        
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
      // this.showRateStructure();
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
        this.projectCostList = response.rows;
        this.total = response.total;
        this.accountProjects(this.projectCostList);
      });
      
    },

    accountProjects(rows) {
      // console.log(rows);
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

    preChangeData(){
      
    },

    preDataMonth(){
      
    },


    
    showRate(){
      listRate().then(response => {
        console.log("listRateresponse");
        console.log(response);

        var invoiceList = response.invoiceList;
        var FinWagesList = response.finWagesList;

        // var month = ['2021-09', '2021-10', '2021-11', '2021-12', '2022-01', '2022-02', '2022-03', '2022-04', '2022-05'];
        // // var personnel_cost = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var wages_cost = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var general_income = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var procurement_cost = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var others_cost = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var business_tax = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        // var pre_tax = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        
        for(var one in invoiceList){

          // console.log("one");
          // console.log(invoiceList[one]);
          var invoice = invoiceList[one];
          // console.log("invoice");
          // console.log(invoice);


          //统计每个月的其他支出、采购支出、合同收入
          for(var i=0; i<this.rateDate.month.length; i++){
            
            if(invoice.invoiceDate != null && invoice.invoiceDate.substring(0,7) == this.rateDate.month[i]){
              // console.log("substring:"+invoice.invoiceDate.substring(0,7));
              // console.log("invoice.invoiceFrom:"+invoice.invoiceFrom );
              if(invoice.invoiceFrom == '2'){
                // console.log("2"+invoice.total);
                this.rateDate.others_cost[i] += invoice.total;
              }else if(invoice.invoiceFrom == '3'){
                this.rateDate.procurement_cost[i] += invoice.total;
              }else if(invoice.invoiceFrom == '4'){
                this.rateDate.business_tax[i] += invoice.tax;
                this.rateDate.general_income[i] += invoice.total;
              }
            }

            //获取最后一个月的其他支出、采购支出、合同收入
            if(i==this.rateDate.month.length-1){
              this.rateStructureDate.generalIncome = this.rateDate.general_income[i];
              this.rateStructureDate.procurementCost = this.rateDate.procurement_cost[i];
              this.rateStructureDate.othersCost = this.rateDate.others_cost[i];
            }
          }
        }

        //统计每个月的发放工资（工资）
        for(var one in FinWagesList){
          var finWage = FinWagesList[one];
          this.account.totalWages += finWage.finalWage;
          for(var i=0; i<this.rateDate.month.length; i++){
            // console.log("issuingDate");
            // console.log(finWage.issuingDate);
            // console.log("month");
            // console.log(month[i]);
            if(finWage.issuingDate == this.rateDate.month[i]){
              this.rateDate.wages_cost[i] += finWage.finalWage;

              //
            }
            //获取最后一个月的发放工资
            if(i==this.rateDate.month.length-1){
              // this.rateStructureDate.personnelCost = personnel_cost[i];
              this.rateStructureDate.wagesCost = this.rateDate.wages_cost[i];
            }
          }
        }
        this.account.totalWages = (parseFloat(this.account.totalWages ) + 0.00).toFixed(2);

        // 计算总计利润
        for(var i=0; i<this.rateDate.month.length; i++){
          this.rateDate.pre_tax[i] = this.rateDate.general_income[i] - this.rateDate.business_tax[i] - this.rateDate.procurement_cost[i] - this.rateDate.others_cost[i] - this.rateDate.wages_cost[i];
          if(i == this.rateDate.month.length-1){
            this.rateStructureDate.preTax = this.rateDate.pre_tax[i];
          }
        }
        

        console.log('preTax');
        console.log(this.account.preTax);
          
        console.log("wages_cost");
        console.log(this.rateDate.wages_cost);
        console.log("general_income");
        console.log(this.rateDate.general_income);
        console.log("procurement_cost");
        console.log(this.rateDate.procurement_cost);
        console.log("others_cost");
        console.log(this.rateDate.others_cost);
        console.log("tax");
        console.log(this.rateDate.tax);
        console.log("business_tax");
        console.log(this.rateDate.business_tax);
        console.log("this.account.totalWages");
        console.log(this.account.totalWages);
        
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
            data: ['账期总收入', '发放工资', '采购支出', '其他支出', '总计税额', '总计利润']
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
            data: ['21年09月', '21年10月', '21年11月', '21年12月', '22年01月', '22年02月', '22年03月', '22年04月', '22年05月']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
                name: '账期总收入',
                type: 'line',
                stack: 'general_income',
                data: this.rateDate.general_income
            },
            {
                name: '发放工资',
                type: 'line',
                stack: 'wages_cost',
                data: this.rateDate.wages_cost
            },
            {
                name: '采购支出',
                type: 'line',
                stack: 'procurement_cost',
                data: this.rateDate.procurement_cost
            },
            {
                name: '其他支出',
                type: 'line',
                stack: 'others_cost',
                data: this.rateDate.others_cost
            },
            {
                name: '总计税额',
                type: 'line',
                stack: 'tax',
                data: this.rateDate.business_tax
            },
            {
                name: '总计利润',
                type: 'line',
                stack: 'pre_tax',
                data: this.rateDate.pre_tax
            },
          ]
        };
        myChart.on('click', this.showRateStructure);
        option && myChart.setOption(option);
        this.showRateStructure({dataIndex:8,name:"2022-05"});
      });
    },


    showRateStructure(params){
      // listRateStuMonth().then(response => {

      console.log(params);

      // })
      var i = params.dataIndex;
      this.month = params.name;

     // 总计利润取绝对值
     //this.rateStructureDate.preTaxx = this.rateStructureDate.preTax>0? this.rateStructureDate.preTax:0-this.rateStructureDate.preTax;
     
      var chartDom = document.getElementById('rateStructure');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
            text: '',
            // subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['账期总收入', '发放工资', '采购支出', '其他支出', '总计税额', '总计利润']
        },
        series: [
          {
            name: '当月利润结构',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
              // {value: this.rateStructureDate.generalIncome, name: '账期总收入'},
              // {value: this.rateStructureDate.wagesCost, name: '发放工资'},
              // {value: this.rateStructureDate.procurementCost, name: '采购支出'},
              // {value: this.rateStructureDate.othersCost, name: '其他支出'},
              // {value: this.rateStructureDate.preTax, name: '总计利润'}
              {value: this.rateDate.general_income[i], name: '账期总收入'},
              {value: this.rateDate.wages_cost[i], name: '发放工资'},
              {value: this.rateDate.procurement_cost[i], name: '采购支出'},
              {value: this.rateDate.others_cost[i], name: '其他支出'},
              {value: this.rateDate.business_tax[i], name: '总计税额'},
              {value: this.rateDate.pre_tax[i], name: '总计利润'}
            ],
            emphasis: {
              itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);




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

