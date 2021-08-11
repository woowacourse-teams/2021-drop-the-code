import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import webpack, { WebpackPluginInstance } from "webpack";
import nodeExternals from "webpack-node-externals";

import path from "path";

const config: webpack.Configuration = {
  target: "node",
  entry: "./src/server.tsx",
  devtool: false,
  output: {
    path: path.resolve(__dirname, "dist"),
    filename: "server.js",
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

  resolve: { extensions: [".js", ".ts", ".tsx"], modules: [path.join(__dirname, "src"), "node_modules"] },
  plugins: [new ForkTsCheckerWebpackPlugin()],
  externals: [nodeExternals() as WebpackPluginInstance],
};

export default config;
