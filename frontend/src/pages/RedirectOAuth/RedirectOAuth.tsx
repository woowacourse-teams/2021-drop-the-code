import { Suspense, useEffect } from "react";
import { useQuery } from "react-query";
import { useLocation } from "react-router-dom";

import { oauthLogin } from "../../apis/auth";
import Loading from "../../components/Loading/Loading";

// Auth
const OAuthReceiver = () => {
  const location = useLocation();

  const query = new URLSearchParams(location.search);
  const [code, providerName] = [query.get("code"), query.get("providerName")];

  const { data } = useQuery("oauthLogin", async () => {
    if (!code || !providerName) return;

    const response = await oauthLogin(code, providerName);

    if (!response.isSuccess) {
      // 스낵바에 전달
      // response.error.message;
      return;
    }

    return response.data;
  });

  useEffect(() => {
    if (!data) return;

    //setData
  }, []);

  return <></>;
};

const RedirectOAuth = () => (
  <Suspense fallback={<Loading />}>
    <OAuthReceiver />
  </Suspense>
);

export default RedirectOAuth;
