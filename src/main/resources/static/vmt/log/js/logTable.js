Vue.component('log-table', {
    props: ['table_data'],
    data: function () {
        return {
            height: 500,
            jsVoid: 'javascript:void(0)'
        }
    },
    template: `
<el-table
        :data="table_data"
        :max-height="height"
        style="width: 100%">
    <el-table-column
            type="selection">
    </el-table-column>
    <el-table-column
            prop="fileName"
            label="文件名"
            border="true"
            sortable>
    </el-table-column>
    <el-table-column
            prop="fileSize"
            label="文件尺寸"
            border="true"
            sortable>
    </el-table-column>
    <el-table-column
            fixed="right"
            label="操作">
        <template slot-scope="scope">
            <el-button-group>
                <el-link target="_blank" :href="getDownloadUrl(scope.row,true)" :underline="false">
                    <el-tooltip content="下载" placement="top">
                        <el-button type="primary" icon="el-icon-download"></el-button>
                    </el-tooltip>
                </el-link>
                <el-link target="_blank" :href="getDownloadUrl(scope.row,false)" :underline="false">
                    <el-tooltip content="下载(压缩包)" placement="top">
                        <el-button type="primary" icon="el-icon-suitcase"></el-button>
                    </el-tooltip>
                </el-link>
                <el-link :href="jsVoid" :underline="false">
                    <el-tooltip content="在线查看" placement="top">
                        <el-button  type="primary" icon="el-icon-document"></el-button>
                    </el-tooltip>
                </el-link>
                <el-link :href="jsVoid" :underline="false">
                    <el-tooltip content="删除" placement="top">
                        <el-button type="primary" icon="el-icon-delete"></el-button>
                    </el-tooltip>
                </el-link>
            </el-button-group>
        </template>
    </el-table-column>
</el-table>
  `,
    methods: {
        getDownloadUrl(row,isRawFile){
            let downloadUrl = ctx + "vmt/log/download/some?fileNameBase64=" + Base64.encode(row.fileName)
                + "&&rawFile=" +isRawFile;
            return downloadUrl;
        }
    },
});