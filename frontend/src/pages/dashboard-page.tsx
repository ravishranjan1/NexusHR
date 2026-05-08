import { LogOut, Shield, UserCircle2, Workflow } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useAuth } from "@/providers/auth-provider";

export function DashboardPage() {
  const { auth, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/login", { replace: true });
  }

  return (
    <div className="min-h-screen bg-[linear-gradient(180deg,_#fffaf3_0%,_#f3e7d6_100%)] text-stone-900">
      <main className="mx-auto flex min-h-screen max-w-7xl flex-col gap-6 px-6 py-8 md:px-10">
        <section className="flex flex-col gap-4 rounded-[2rem] border border-stone-200/80 bg-white/80 p-8 shadow-[0_24px_80px_rgba(87,63,29,0.09)] md:flex-row md:items-center md:justify-between">
          <div className="space-y-3">
            <span className="inline-flex rounded-full border border-emerald-300 bg-emerald-100 px-3 py-1 text-xs font-semibold uppercase tracking-[0.2em] text-emerald-900">
              Protected Route Active
            </span>
            <h1 className="font-['Georgia'] text-4xl md:text-5xl">NexusHR authenticated workspace</h1>
            <p className="max-w-2xl text-stone-600">
              This temporary landing view confirms the Day 9 frontend auth flow works and that only signed-in users
              can reach protected screens.
            </p>
          </div>
          <Button variant="secondary" onClick={handleLogout}>
            <LogOut className="size-4" />
            Sign out
          </Button>
        </section>

        <section className="grid gap-4 md:grid-cols-3">
          <Card className="border-stone-200/80 bg-white/85">
            <CardHeader>
              <UserCircle2 className="size-8 text-stone-800" />
              <CardTitle>Signed in user</CardTitle>
              <CardDescription>Your auth-service identity is stored locally for route protection.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-2 text-sm text-stone-600">
              <p><strong>Email:</strong> {auth?.email ?? "Unknown"}</p>
              <p><strong>Roles:</strong> {auth?.roles.join(", ") ?? "None"}</p>
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/85">
            <CardHeader>
              <Shield className="size-8 text-stone-800" />
              <CardTitle>Route guard</CardTitle>
              <CardDescription>`/app` is now protected until a frontend session exists.</CardDescription>
            </CardHeader>
            <CardContent className="text-sm text-stone-600">
              This sets up the navigation pattern we’ll use for employee, manager, and admin views in upcoming days.
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/85">
            <CardHeader>
              <Workflow className="size-8 text-stone-800" />
              <CardTitle>Next frontend work</CardTitle>
              <CardDescription>The foundation is ready for dashboard pages and API-connected widgets.</CardDescription>
            </CardHeader>
            <CardContent className="text-sm text-stone-600">
              Day 10 and later can now focus on product flows instead of frontend auth scaffolding.
            </CardContent>
          </Card>
        </section>
      </main>
    </div>
  );
}
