import { ReactNode } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "@/providers/auth-provider";

type ProtectedRouteProps = {
  allowedRoles?: string[];
  children: ReactNode;
};

export function ProtectedRoute({ children, allowedRoles }: ProtectedRouteProps) {
  const { auth, isAuthenticated } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace state={{ from: location.pathname }} />;
  }

  if (allowedRoles?.length) {
    const userRoles = auth?.roles ?? [];
    const isAllowed = allowedRoles.some((role) => userRoles.includes(role));
    if (!isAllowed) {
      return <Navigate to="/app" replace />;
    }
  }

  return <>{children}</>;
}
