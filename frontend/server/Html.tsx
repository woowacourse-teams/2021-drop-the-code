import { PropsWithChildren, ReactElement } from "react";

const Html = ({ children, styles, title }: PropsWithChildren<{ styles: ReactElement[]; title?: string }>) => {
  return (
    <html lang="ko">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="shortcut icon" href="favicon.ico" />
        {styles}
        <title>코드봐줘</title>
      </head>
      <body>
        <div id="root">{children}</div>
        <script async src="/main.js"></script>
      </body>
    </html>
  );
};

export default Html;
