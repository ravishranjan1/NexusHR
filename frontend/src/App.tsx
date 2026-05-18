import { Navigate, Route, Routes } from "react-router-dom";
import { ProtectedRoute } from "@/components/protected-route";
import { AuthPage } from "@/pages/auth-page";
import { DashboardPage } from "@/pages/dashboard-page";
import { ManagerDashboardPage } from "@/pages/manager-dashboard-page";

export function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<AuthPage mode="login" />} />
      <Route path="/signup" element={<AuthPage mode="signup" />} />
      <Route
        path="/app"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/app/manager"
        element={
          <ProtectedRoute allowedRoles={["ADMIN", "HR_MANAGER"]}>
            <ManagerDashboardPage />
          </ProtectedRoute>
        }
      />
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}
