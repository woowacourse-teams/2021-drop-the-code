import ReactRefreshWebpackPlugin from "@pmmmwh/react-refresh-webpack-plugin";
import dotenv from "dotenv";
import HtmlWebpackPlugin from "html-webpack-plugin";
import webpack from "webpack";
import { Configuration as DevServerConfiguration } from "webpack-dev-server";
import merge from "webpack-merge";

import path from "path";

import common from "./webpack.common";

dotenv.config({ path: ".env.development" });

interface Config extends webpack.Configuration {
  devServer?: DevServerConfiguration;
}

const config = merge<Config>(common, {
  target: "web",
  devtool: "eval-source-map",
  devServer: {
    contentBase: path.join(__dirname, "dist"),
    historyApiFallback: true,
    hot: true,
    open: true,
    port: 3000,
  },
  plugins: [
    new HtmlWebpackPlugin({ template: "public/index.html", favicon: "public/favicon.ico" }),
    new ReactRefreshWebpackPlugin(),
  ],
});

export default config;