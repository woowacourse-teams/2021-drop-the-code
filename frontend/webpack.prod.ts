import DotenvWebpackPlugin from "dotenv-webpack";
import { Module } from "webpack";
import { BundleAnalyzerPlugin } from "webpack-bundle-analyzer";
import { WebpackManifestPlugin } from "webpack-manifest-plugin";
import merge from "webpack-merge";

import path from "path";

import common from "./webpack.common";

const config = merge(common, {
  entry: "./src",
  output: {
    path: path.resolve(__dirname, "dist/client"),
    filename: "[chunkhash].js",
    publicPath: "/",
    clean: true,
  },
  devtool: false,
  optimization: {
    splitChunks: {
      cacheGroups: {
        core: {
          priority: 10,
          chunks: "initial",
          name: "core",
          filename: "[chunkhash].js",
          test: /[\\/]node_modules[\\/](react|react-dom)[\\/]/,
        },
        common: {
          priority: 9,
          chunks: "initial",
          test(module: Module) {
            return module.size() > 8000;
          },
          name: "common",
          filename: "[chunkhash].js",
        },
      },
    },
  },
  plugins: [
    new DotenvWebpackPlugin({
      path: ".env.production",
    }),
    new BundleAnalyzerPlugin({ analyzerMode: "disabled", generateStatsFile: true }),
    new WebpackManifestPlugin({}),
  ],
});

export default config;
