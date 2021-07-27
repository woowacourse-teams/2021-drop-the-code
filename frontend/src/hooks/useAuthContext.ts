import { createContext, useContext } from "react";

import { User } from "../types/auth";

interface AuthContextProps {
  login: (user: User) => void;
  logout: () => void;
  user: User | null;
}

export const AuthContext = createContext<AuthContextProps | null>(null);

const useAuthContext = () => {
  const context = useContext(AuthContext);

  if (!context) throw Error("AuthContext가 존재하지 않습니다");

  return context;
};

export default useAuthContext;
