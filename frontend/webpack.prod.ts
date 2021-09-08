import DotenvWebpackPlugin from "dotenv-webpack";
import merge from "webpack-merge";

import path from "path";

import common from "./webpack.common";

const config = merge(common, {
  entry: "./src",
  output: {
    path: path.resolve(__dirname, "dist/client"),
    filename: "[name].js",
    publicPath: "/",
    clean: true,
  },
  devtool: false,
  plugins: [
    new DotenvWebpackPlugin({
      path: ".env.production",
    }),
  ],
});

export default config;
