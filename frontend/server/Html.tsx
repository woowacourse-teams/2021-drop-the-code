import { PropsWithChildren, ReactElement } from "react";
import { Route, Switch } from "react-router-dom";

import fs from "fs";
import path from "path";

import og from "assets/og.jpg";

import META_ROUTE from "./constants/metaRoute";

const manifest = JSON.parse(fs.readFileSync(path.join(__dirname, "../client/manifest.json"), "utf8"));

const Html = ({ children, styles, title }: PropsWithChildren<{ styles: ReactElement[]; title?: string }>) => {
  return (
    <html lang="ko">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
        <link
          rel="preload"
          href={process.env.CDN_URL + "/IBMPlexSansKR-Regular.woff2"}
          as="font"
          type="font/woff2"
          crossOrigin={process.env.CDN_URL}
        />
        {styles}
        <title>코드봐줘</title>
        <meta property="og:description" content="내 코드를 한 단계 성장시켜줄 리뷰어를 만나보세요." />
        <meta property="og:image" content={og} />
        <meta property="og:site_name" content="코드봐줘" />
        <meta property="og:locale" content="ko-KR" />
        <Switch>
          {META_ROUTE.map(({ path, meta }) => (
            <Route key={path} path={path} exact>
              {meta}
            </Route>
          ))}
        </Switch>
        <script defer src={manifest["main.js"]}></script>
        <script defer src={manifest["core.js"]}></script>
        <script defer src={manifest["common.js"]}></script>
      </head>
      <body>
        <div id="root">{children}</div>
      </body>
    </html>
  );
};

export default Html;
