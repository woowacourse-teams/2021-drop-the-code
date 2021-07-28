import { Role } from "./review";

export interface User {
  id: number;
  name: string;
  email: string;
  imageUrl: string;
  role: Role;
  accessToken: string;
  refreshToken: string;
}
