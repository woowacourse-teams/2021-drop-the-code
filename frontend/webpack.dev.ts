import webpack from "webpack";
import { Configuration as DevServerConfiguration } from "webpack-dev-server";
import merge from "webpack-merge";

import path from "path";

import common from "./webpack.common";

interface Config extends webpack.Configuration {
  devServer?: DevServerConfiguration;
}

const config = merge<Config>(common, {
  mode: "development",
  devtool: "eval-source-map",
  devServer: {
    contentBase: path.join(__dirname, "dist"),
    historyApiFallback: true,
    compress: true,
    hot: true,
    open: true,
    port: 3000,
  },
});

export default config;
