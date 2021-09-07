import { createContext, useContext } from "react";

import { User } from "types/auth";

interface AuthContextProps {
  isAuthenticated: boolean;
  user: User | null;
  authCheck: () => void;
  login: (user: User) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextProps | null>(null);

const useAuthContext = () => {
  const context = useContext(AuthContext);

  if (!context) throw Error("AuthContext가 존재하지 않습니다");

  return context;
};

export default useAuthContext;
