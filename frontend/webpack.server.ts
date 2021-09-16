import CopyPlugin from "copy-webpack-plugin";
import DotenvWebpackPlugin from "dotenv-webpack";
import merge from "webpack-merge";

import path from "path";

import common from "./webpack.common";

const config = merge(common, {
  entry: "./server",
  target: "node",
  output: {
    path: path.resolve(__dirname, "dist/server"),
    filename: "[name].js",
    publicPath: "/",
    clean: true,
  },
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "public",
          to: "../client",
          filter: (resourcePath) => {
            return !resourcePath.includes("index.html");
          },
        },
      ],
    }),
    new DotenvWebpackPlugin({
      path: ".env.production",
    }),
  ],
});

export default config;
