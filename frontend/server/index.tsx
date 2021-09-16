import { Suspense } from "react";
import { StaticRouter } from "react-router-dom";

import express from "express";
import { pipeToNodeWritable, renderToString } from "react-dom/server";
import { ServerStyleSheet } from "styled-components";

import path from "path";

import Loading from "components/Loading/Loading";

import App from "../src/App";

import Html from "./Html";

const app = express();

let keepAlive = true;

app.use(function (req, res, next) {
  if (!keepAlive) {
    res.set("Connection", "close");
  }

  next();
});

app.use(express.static(path.join(__dirname, "../client")));
app.use(express.static(path.join(__dirname, "/")));

app.get("*", (req, res) => {
  const sheet = new ServerStyleSheet();

  renderToString(
    sheet.collectStyles(
      <StaticRouter location={req.url} context={{}}>
        <App />
      </StaticRouter>
    )
  );

  const { startWriting } = pipeToNodeWritable(
    <StaticRouter location={req.url} context={{}}>
      <Html styles={sheet.getStyleElement()}>
        <Suspense fallback={<Loading />}>
          <App />
        </Suspense>
      </Html>
    </StaticRouter>,
    res,
    {
      onReadyToStream: () => {
        res.setHeader("Content-type", "text/html");
        res.write("<!DOCTYPE html>");

        startWriting();
      },
    }
  );
});

const server = app.listen(process.env.PORT, () => {
  process.send?.("ready");
  console.log(`Server started ${process.env.PORT}`);
});

process.on("SIGINT", function () {
  keepAlive = false;

  server.close(() => {
    console.log("Server closed");
    process.exit(0);
  });
});
