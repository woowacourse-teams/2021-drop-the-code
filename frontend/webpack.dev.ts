import dotenv from "dotenv";
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
  plugins: [new webpack.EnvironmentPlugin(["GITHUB_OAUTH_CLIENT_ID", "GITHUB_OAUTH_REDIRECT"])],
});

export default config;
