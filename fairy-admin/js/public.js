var basePath;
$(function () {
    // basePath = public.getBasePath();
    basePath = "localhost:8888"
})

var public = {
    getBasePath: function () {
        return location.origin; //取得整个地址栏;
    }
}
