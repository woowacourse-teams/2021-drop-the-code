/*global process, module*/

const isDevelopment = process.env.NODE_ENV === "development";
const isProduction = process.env.NODE_ENV === "production";

const presets = [
  isProduction && [
    "minify",
    {
      builtIns: false,
    },
  ],
  [
    "@babel/preset-env",
    {
      useBuiltIns: "usage",
      corejs: "3",
    },
  ],
  [
    "@babel/preset-react",
    {
      runtime: "automatic",
    },
  ],
  "@babel/preset-typescript",
].filter(Boolean);

const plugins = ["babel-plugin-styled-components", isDevelopment && "react-refresh/babel"].filter(Boolean);

module.exports = { presets, plugins };
