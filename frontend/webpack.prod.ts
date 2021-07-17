import dotenv from "dotenv";
import webpack from "webpack";
import merge from "webpack-merge";

import common from "./webpack.common";

dotenv.config({ path: ".env.production" });

const config = merge(common, {
  mode: "production",
  devtool: false,
  plugins: [new webpack.EnvironmentPlugin(["GITHUB_OAUTH_CLIENT_ID", "CLIENT_BASE_URL", "SERVER_BASE_URL"])],
});

export default config;
