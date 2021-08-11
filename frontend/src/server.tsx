/* eslint-disable @typescript-eslint/no-var-requires */
import { StaticRouter } from "react-router-dom";

import { ChunkExtractor } from "@loadable/server";
import express from "express";
import { renderToString } from "react-dom/server";
import { ServerStyleSheet } from "styled-components";

import path from "path";

const app = express();

app.use(express.static(path.resolve(__dirname)));

app.get("/favicon.ico", (req, res) => res.sendStatus(204));

const nodeStats = path.resolve(__dirname, "async-node/loadable-stats.json");

const webStats = path.resolve(__dirname, "web/loadable-stats.json");

app.get("*", (req, res) => {
  const sheet = new ServerStyleSheet();

  const nodeExtractor = new ChunkExtractor({ statsFile: nodeStats });
  const { default: App } = nodeExtractor.requireEntrypoint();

  const webExtractor = new ChunkExtractor({ statsFile: webStats });
  const jsx = webExtractor.collectChunks(
    sheet.collectStyles(
      <StaticRouter location={req.url} context={{}}>
        <App />
      </StaticRouter>
    )
  );

  const html = renderToString(jsx);
  const styleTags = sheet.getStyleTags();
  res.set("content-type", "text/html");

  res.send(`
    <!DOCTYPE html>
      <html lang="kr">
        <head>
          <meta charset="UTF-8" />
          <meta http-equiv="X-UA-Compatible" content="IE=edge" />
          <meta name="viewport" content="width=device-width, initial-scale=1.0" />
          <title>코드봐줘</title>
          ${styleTags}
        </head>
        <body>
          <div id="root">${html}</div>
          ${webExtractor.getScriptTags()}
        </body>
      </html>
  `);
});

app.listen(80);
