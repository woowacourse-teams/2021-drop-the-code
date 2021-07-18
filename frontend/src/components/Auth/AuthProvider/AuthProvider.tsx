import { ReactNode, useState } from "react";

import { AuthContext } from "../../../hooks/useAuthContext";
import { User } from "../../../types/auth";

export interface Props {
  children: ReactNode;
}

const AuthProvider = ({ children }: Props) => {
  const [user, setUser] = useState<User | null>(null);

  const login = (user: User) => {
    setUser(user);
  };

  const logout = () => {
    setUser(null);
  };

  return <AuthContext.Provider value={{ user: user, login, logout }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
