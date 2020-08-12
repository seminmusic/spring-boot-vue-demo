module.exports = {
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
    },
    chainWebpack: (config) => {
        // Bez ovoga lazy load chunkova radi tako da nakon inicijalnog loada aplikacije u pozadini browser loada te chunkove,
        // a kad se posjeti ruta chunk se loada iz cache-a (ne ide na server).
        // Sa ovim lazy load chunka će se pokrenuti tek u trenutku kad se posjeti ruta.
        // (ako ima "pages" onda naziv plugina bude nešto kao "<naziv-plugina>-page", mogu se vidjeti nazivi plugina sa: vue inspect --plugins)
        // config.plugins.delete("prefetch");
        // config.plugins.delete("prefetch-index");
    },
    transpileDependencies: [
        "vuetify"
    ]
};
