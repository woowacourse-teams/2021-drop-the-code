import { Suspense, useEffect } from "react";
import { useQuery } from "react-query";
import { useLocation } from "react-router-dom";

import { oauthLogin } from "../../apis/auth";
import Loading from "../../components/Loading/Loading";
import useAuthContext from "../../hooks/useAuthContext";
import { toURLSearchParams } from "../../utils/formatter";

// Auth
const OAuthReceiver = () => {
  const location = useLocation();
  const { login } = useAuthContext();

  const query = new URLSearchParams(location.search);
  const [providerName, code] = [query.get("providerName"), query.get("code")];

  const { data } = useQuery("oauthLogin", async () => {
    if (!providerName || !code) return;

    const response = await oauthLogin(toURLSearchParams({ providerName, code }));

    if (!response.isSuccess) {
      // 스낵바에 전달
      // response.error.message;
      return;
    }

    return response.data;
  });

  useEffect(() => {
    if (!data) return;

    login(data);

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
