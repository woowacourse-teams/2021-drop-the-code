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

app.use(express.static(path.join(__dirname, "../client")));
app.use("/favicon.ico", express.static(path.join(__dirname, "../../public/favicon.ico")));

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

app.listen(3000, () => console.log("Server started http://localhost:3000"));
