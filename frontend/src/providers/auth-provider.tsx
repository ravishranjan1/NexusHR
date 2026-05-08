import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { AuthResponse } from "@/types/auth";

const STORAGE_KEY = "nexushr-auth";

type AuthContextValue = {
  auth: AuthResponse | null;
  isAuthenticated: boolean;
  saveAuth: (value: AuthResponse) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

type AuthProviderProps = {
  children: ReactNode;
};

export function AuthProvider({ children }: AuthProviderProps) {
  const [auth, setAuth] = useState<AuthResponse | null>(null);

  useEffect(() => {
    const stored = window.localStorage.getItem(STORAGE_KEY);
    if (stored) {
      try {
        setAuth(JSON.parse(stored) as AuthResponse);
      } catch {
        window.localStorage.removeItem(STORAGE_KEY);
      }
    }
  }, []);

  function saveAuth(value: AuthResponse) {
    setAuth(value);
    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(value));
  }

  function logout() {
    setAuth(null);
    window.localStorage.removeItem(STORAGE_KEY);
  }

  return (
    <AuthContext.Provider
      value={{
        auth,
        isAuthenticated: Boolean(auth?.accessToken),
        saveAuth,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider.");
  }
  return context;
}
