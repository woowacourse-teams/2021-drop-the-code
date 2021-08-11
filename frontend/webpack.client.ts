import LoadablePlugin from "@loadable/webpack-plugin";
import ReactRefreshWebpackPlugin from "@pmmmwh/react-refresh-webpack-plugin";
import dotenv from "dotenv";
import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import webpack, { WebpackPluginInstance } from "webpack";
import { Configuration as DevServerConfiguration } from "webpack-dev-server";
import nodeExternals from "webpack-node-externals";

import path from "path";

const NODE_ENV = process.env.NODE_ENV;
const isProduction = NODE_ENV === "production";
const isDevelopment = NODE_ENV === "development";

if (isProduction) {
  dotenv.config({ path: ".env.production" });
} else {
  dotenv.config({ path: ".env.development" });
}

interface Config extends webpack.Configuration {
  devServer?: DevServerConfiguration;
}

const config = (target: string): Config => ({
  name: target,
  entry: `./src/main-${target}.tsx`,
  devtool: isProduction ? false : "eval-source-map",
  target,
  output: {
    path: path.resolve(__dirname, "dist", target),
    filename: "[name].[chunkhash].js",
    publicPath: `/${target}/`,
    clean: true,
    libraryTarget: target === "async-node" ? "commonjs2" : undefined,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
          options: {
            caller: { target },
          },
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
  externals: target === "async-node" ? ["@loadable/component", nodeExternals() as WebpackPluginInstance] : undefined,
  resolve: { extensions: [".js", ".ts", ".tsx"], modules: [path.join(__dirname, "src"), "node_modules"] },
  plugins: (
    [
      new LoadablePlugin() as unknown as WebpackPluginInstance,
      new ForkTsCheckerWebpackPlugin(),
      new webpack.EnvironmentPlugin([
        "GITHUB_OAUTH_CLIENT_ID",
        "GITHUB_OAUTH_REDIRECT_URL",
        "CLIENT_BASE_URL",
        "SERVER_BASE_URL",
      ]),
      isDevelopment && new webpack.HotModuleReplacementPlugin(),
      isDevelopment && new ReactRefreshWebpackPlugin(),
    ] as WebpackPluginInstance[]
  ).filter(Boolean),
});

export default [config("web"), config("async-node")];
