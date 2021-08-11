/* eslint-disable no-undef */
const isDevelopment = process.env.NODE_ENV === "development";

function isWebTarget(caller) {
  return Boolean(caller && caller.target === "web");
}

function isWebpack(caller) {
  return Boolean(caller && caller.name === "babel-loader");
}

module.exports = (api) => {
  const web = api.caller(isWebTarget);
  const webpack = api.caller(isWebpack);

  return {
    presets: [
      [
        "@babel/preset-env",
        {
          useBuiltIns: web ? "usage" : undefined,
          corejs: web ? "3" : false,
          targets: !web ? { node: "current" } : undefined,
          modules: webpack ? false : "commonjs",
        },
      ],
      [
        "@babel/preset-react",
        {
          runtime: "automatic",
        },
      ],
      "@babel/preset-typescript",
    ].filter(Boolean),
    plugins: [
      "@babel/plugin-syntax-dynamic-import",
      "babel-plugin-styled-components",
      "@loadable/babel-plugin",
      isDevelopment && "react-refresh/babel",
    ].filter(Boolean),
  };
};
