Vue.component('log-preview', {
    props: ['logName', 'logContents'],
    data: function () {
        return {
            jsVoid: 'javascript:void(0)',
        }
    },
    template: `
<div>
<!--<el-input-->
<!--  type="textarea"-->
<!--  :rows="20"-->
<!--  :show-word-limit="true"-->
<!--  :value="logContent"-->
<!--  :autosize="true"-->
<!--  resize="none"-->
<!--  outline="none"-->
<!--  :readonly="true"-->
<!--  >-->
<!--</el-input>-->
<el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>{{logName}}</span>
    <el-button style="float: right; padding: 3px 0" type="text" @click="exportContent">导出内容</el-button>
  </div>
<!--  <div v-for="logContent in logContents" :key="o" class="text item">-->
<!--    -->
<!--  </div>-->
  <pre style="white-space: pre-wrap; font-size: 14px" v-for="logContent of logContents" v-html="logContent">
<!--    <span v-for="logContent of logContents" v-html="logContent"></span>-->
  </pre>
</el-card>
</div> `,
    methods: {
        exportContent() {
            //encodeURIComponent解决中文乱码
            let uri = 'data:text/plain;charset=utf-8,\ufeff' + encodeURIComponent(this.logContents.join('').replace(/<br\/>/g, ''));
            //通过创建a标签实现
            let link = document.createElement('a');
            link.href = uri;
            //对下载的文件命名
            link.download = this.logName;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
});
