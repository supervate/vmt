Vue.component('log-table', {
    props: ['tableData', 'height'],
    data: function () {
        return {
            jsVoid: 'javascript:void(0)',
            // save the row that be selected,to handle our business.
            multipleSelection: []
        }
    },
    template: `
<div>
<el-button-group style=" margin-bottom: 10px;margin-top: 10px;">
  <el-link target="_blank" :href="getMultiDownloadUrl(false)" :underline="false">
      <el-tooltip content="download selected" placement="bottom">
            <el-button type="primary" icon="el-icon-download" size="small">some</el-button>
      </el-tooltip>
  </el-link>
  <el-link target="_blank" :href="getMultiDownloadUrl(true)" :underline="false">
      <el-tooltip content="download all" placement="bottom">
        <el-button type="primary" icon="el-icon-download" size="small">all</el-button>
      </el-tooltip>
  </el-link>
  <slot></slot>
</el-button-group>
<el-table
        ref="logTable"
        :data="tableData"
        :max-height="height"
        @selection-change="handleSelectionChange"
        @row-click="clickSelectRow"
        style="width: 100%;margin-bottom: 10px;">
    <el-table-column
            type="selection">
    </el-table-column>
    <el-table-column
            prop="fileName"
            label="name"
            border="true"
            sortable>
    </el-table-column>
    <el-table-column
            prop="fileSize"
            label="size"
            border="true"
            sortable>
      <template slot-scope="scope">
      {{ formatFileSize(scope.row.fileSize) }}
      </template>
    </el-table-column>
    <el-table-column
            fixed="right"
            label="operation">
        <template slot-scope="scope">
            <el-button-group>
                <el-link target="_blank" :href="getDownloadUrl(scope.row,true)" :underline="false">
                    <el-tooltip content="download" placement="top">
                        <el-button type="primary" icon="el-icon-download" size="small"></el-button>
                    </el-tooltip>
                </el-link>
                <el-link target="_blank" :href="getDownloadUrl(scope.row,false)" :underline="false">
                    <el-tooltip content="download(compress package)" placement="top">
                        <el-button type="primary" icon="el-icon-suitcase" size="small"></el-button>
                    </el-tooltip>
                </el-link>
                <el-link target="_blank" :href="getPreviewUrl(scope.row)" :underline="false">
                    <el-tooltip content="preview" placement="top">
                        <el-button  type="primary" icon="el-icon-document" size="small"></el-button>
                    </el-tooltip>
                </el-link>
            </el-button-group>
        </template>
    </el-table-column>
</el-table>
</div>
  `,
    methods: {
        getDownloadUrl(row, isRawFile) {
            return ctx + "vmt/log/download/some?fileNameBase64=" + Base64.encode(row.fileName)
                + "&&rawFile=" + isRawFile;
        },
        getMultiDownloadUrl(downloadAll) {
            if (downloadAll) {
                return ctx + 'vmt/log/download/all';
            } else {
                let fileNames = '';
                for (let rowIndex in this.multipleSelection) {
                    if (rowIndex === this.multipleSelection.length - 1) {
                        fileNames += this.multipleSelection[rowIndex].fileName;
                    } else {
                        fileNames += this.multipleSelection[rowIndex].fileName + ',';
                    }
                }
                return ctx + 'vmt/log/download/some?fileNameBase64=' + Base64.encode(fileNames);
            }
        },
        getPreviewUrl(row) {
            return ctx + "vmt/log/previewPage/" + Base64.encode(row.fileName);
        },
        handleSelectionChange(val) {
            // reset the selected array when the row select changed.
            this.multipleSelection = val;
        },
        clickSelectRow(row, col, event) {
            let clickEleClassName = event.toElement.className;
            // select the row just when click the area's element class is cell or el-table_xxx.
            if (clickEleClassName.indexOf('cell') !== -1 || clickEleClassName.indexOf('el-table_') !== -1) {
                row.flag = !row.flag;
                this.$refs.logTable.toggleRowSelection(row, row.flag);
            }
        },
        formatFileSize(fileSize) {
            return fileSize > 1024 * 1024 ? this.toDecimal(fileSize / (1024 * 1024)) + ' mb' : this.toDecimal(fileSize / 1024) + ' kb';
        },
        //保留两位小数
        //功能：将浮点数四舍五入，取小数点后2位
        toDecimal(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return;
            }
            f = Math.round(x * 100) / 100;
            return f;
        }
    }
});
