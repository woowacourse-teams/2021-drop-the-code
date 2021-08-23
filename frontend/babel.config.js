/*global process, module*/

const NODE_ENV = process.env.NODE_ENV;

if (!NODE_ENV) throw Error("NODE_ENV environment variable is not defined");

const isDevelopment = NODE_ENV === "development";
const isProduction = NODE_ENV === "production";

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
