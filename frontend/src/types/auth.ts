export type AuthResponse = {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  email: string;
  roles: string[];
};

export type LoginPayload = {
  email: string;
  password: string;
};

export type SignupPayload = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
};
