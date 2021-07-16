import ReactRefreshWebpackPlugin from "@pmmmwh/react-refresh-webpack-plugin";
import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import HtmlWebpackPlugin from "html-webpack-plugin";
import webpack from "webpack";

import path from "path";

const config: webpack.Configuration = {
  entry: "./src/index.tsx",
  output: {
    path: path.resolve(__dirname, "dist"),
    filename: "[name].[chunkhash].js",
    publicPath: "/",
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
        },
      },
      {
        test: /\.(png|jpg|jpeg|gif)$/i,
        type: "asset/resource",
      },
      {
        test: /\.svg?$/,
        use: [
          {
            loader: "@svgr/webpack",
            options: {
              prettier: false,
            },
          },
        ],
        issuer: {
          and: [/\.(ts|tsx|js|jsx|md|mdx)$/],
        },
      },
    ],
  },
  resolve: { extensions: [".js", ".ts", ".tsx"] },
  plugins: [
    new HtmlWebpackPlugin({ template: "public/index.html", favicon: "public/favicon.ico" }),
    new ForkTsCheckerWebpackPlugin(),
    new ReactRefreshWebpackPlugin(),
    new webpack.HotModuleReplacementPlugin(),
  ],
};

export default config;
