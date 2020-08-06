module.exports = {
    pages: {
        index: {
            entry: "src/main.js",
            title: "Sema Cars Management"
        }
    },
    lintOnSave: false,
    outputDir: "target/dist",
    assetsDir: "static",
    publicPath: "/spring-and-vue/",
    devServer: {
        port: 3001,
        open: true
    },
    configureWebpack: {
        devtool: "source-map"
    }
};
