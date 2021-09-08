import { PropsWithChildren, ReactElement } from "react";
import { Route, Switch } from "react-router-dom";

import og from "assets/og.jpg";

import META_ROUTE from "./constants/metaRoute";

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
      </head>
      <body>
        <div id="root">{children}</div>
        <script async src="/main.js"></script>
      </body>
    </html>
  );
};

export default Html;
