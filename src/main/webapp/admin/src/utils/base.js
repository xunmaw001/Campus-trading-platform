const base = {
    get() {
        return {
            url : "http://localhost:8080/xiaoyuanjiaoyipingtai/",
            name: "xiaoyuanjiaoyipingtai",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/xiaoyuanjiaoyipingtai/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "校园交易平台"
        } 
    }
}
export default base
