import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import webpack from "webpack";

import path from "path";

const config: webpack.Configuration = {
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
};

export default config;
