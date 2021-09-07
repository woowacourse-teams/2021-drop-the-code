import ReactRefreshWebpackPlugin from "@pmmmwh/react-refresh-webpack-plugin";
import dotenv from "dotenv";
import HtmlWebpackPlugin from "html-webpack-plugin";
import webpack from "webpack";
import { Configuration as DevServerConfiguration } from "webpack-dev-server";
import merge from "webpack-merge";

import common from "./webpack.common";

dotenv.config({ path: ".env.development" });

interface Config extends webpack.Configuration {
  devServer?: DevServerConfiguration;
}

const config = merge<Config>(common, {
  entry: "./src",
  target: "web",
  devtool: "eval-source-map",
  devServer: {
    historyApiFallback: true,
    hot: true,
    open: true,
    port: 3000,
  },
  output: {
    publicPath: "/",
  },
  plugins: [
    new HtmlWebpackPlugin({ template: "public/index.html", favicon: "public/favicon.ico" }),
    new ReactRefreshWebpackPlugin(),
  ],
});

export default config;
