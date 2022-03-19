<template>
  <div class="app-container home">
    <el-row :gutter="20">
        <el-col :span="24">
            <h1>常用功能</h1>
          </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="24">
        <el-button icon="el-icon-edit">
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
        </el-button>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      
      <el-col :span="24">
        <h1>服务指标</h1>
      </el-col>
      <el-col :sm="24"  :span="24" style="padding-left: 50px">
        <el-row>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>利润总额</span>
              </div>
              <div  class="text item">
                12557.35
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>现金</span>
              </div>
              <div  class="text item">
                9,955,200.11
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>银行存款</span>
              </div>
              <div  class="text item">
                1,800,000.00
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>存货</span>
              </div>
              <div  class="text item">
                20,000.00
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>负债总额</span>
              </div>
              <div  class="text item">
                2,554.88
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>贷款总额</span>
              </div>
              <div  class="text item">
                7,757.35
              </div>
            </el-card>
          </el-col>
          <el-col :span="3">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>应收账款</span>
              </div>
              <div  class="text item">
                100.35
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" >
        <el-row>
          <el-col :span="24">
          <h1>利润变化趋势（单位：元）</h1>
          <div id="run"></div>
          
          </el-col>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12">
        <el-row>
          <el-col :span="24">
          <h1>1月利润结构分析（单位：元）</h1>
          
          </el-col>
        </el-row>
        <!-- <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
          
          </el-collapse>
        </el-card> -->
      </el-col>
    </el-row>
  </div>
</template>

<script>



export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.1",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
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

