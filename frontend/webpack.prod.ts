import dotenv from "dotenv";
import merge from "webpack-merge";

import common from "./webpack.common";

dotenv.config({ path: ".env.production" });

const config = merge(common, {
  devtool: false,
});

export default config;
