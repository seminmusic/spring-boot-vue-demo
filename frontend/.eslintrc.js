module.exports = {
    root: true,
    env: {
        node: true
    },
    extends: [
        "plugin:vue/essential",
        "eslint:recommended",
        // "@vue/prettier",
        "@vue/standard"
    ],
    parserOptions: {
        parser: "babel-eslint"
    },
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
        "vue/max-attributes-per-line": "off",
        quotes: ["error", "double"],
        indent: ["error", 4],
        semi: ["error", "always"]
    }
}
