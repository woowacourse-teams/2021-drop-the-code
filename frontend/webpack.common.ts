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
  plugins: [
    new ForkTsCheckerWebpackPlugin(),
    new webpack.EnvironmentPlugin([
      "GITHUB_OAUTH_CLIENT_ID",
      "GITHUB_OAUTH_REDIRECT_URL",
      "CLIENT_BASE_URL",
      "SERVER_BASE_URL",
    ]),
  ],
};

export default config;
