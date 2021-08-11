import { useEffect } from "react";
import { useQuery } from "react-query";
import { useHistory, useLocation } from "react-router-dom";

import { oauthLogin } from "apis/auth";
import Loading from "components/Loading/Loading";
import useAuthContext from "hooks/useAuthContext";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";
import { PATH } from "utils/constants/path";
import { toURLSearchParams } from "utils/formatter";

const RedirectOAuth = () => {
  const { login } = useAuthContext();
  const toast = useToastContext();

  const history = useHistory();
  const location = useLocation();

  const query = new URLSearchParams(location.search);
  const [providerName, code] = [query.get("providerName"), query.get("code")];

  const { data } = useQuery([QUERY_KEY.OAUTH_LOGIN, providerName, code], async () => {
    if (!providerName || !code) return;

    const response = await oauthLogin(toURLSearchParams({ providerName, code }));

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  useEffect(() => {
    if (!data) return;

    login(data);
    history.push(PATH.MAIN);
  }, [data]);

  return <Loading />;
};

export default RedirectOAuth;
