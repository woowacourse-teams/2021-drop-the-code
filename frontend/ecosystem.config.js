/* eslint-disable no-undef */
module.exports = {
  apps: [
    {
      name: "front-server",
      script: "./dist/server/main.js",
      instances: "max",
      exec_mode: "cluster",
      wait_ready: true,
      listen_timeout: 10000,
      kill_timeout: 10000,
    },
  ],
};
