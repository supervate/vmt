function listAllFiles() {
    // 可选地，上面的请求可以这样做
    axios.get(ctx + "vmt/log/list")
        .then(function (response) {
            // vm.table_data.push(response);
            vm.table_data = response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}
// setInterval(function(){
// 	listAllFiles();
// },3000);