import { useEffect } from "react";
import { useLocation } from "react-router-dom";

// Auth
const OAuthReceiver = () => {
  const location = useLocation();

  const query = new URLSearchParams(location.search);
  const [code, providerName] = [query.get("code"), query.get("providerName")];

  useEffect(() => {
    // api
    // setAuth
  }, []);

  console.log(code, providerName);
  return (
    <div>
      code is {code} {providerName}
    </div>
  );
};

export default OAuthReceiver;
