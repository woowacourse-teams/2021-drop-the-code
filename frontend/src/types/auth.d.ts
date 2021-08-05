import { Role } from "./review";

export interface User {
  id: number;
  name: string;
  email: string;
  imageUrl: string;
  githubUrl: string;
  role: Role;
  accessToken: string;
  refreshToken: string;
}
