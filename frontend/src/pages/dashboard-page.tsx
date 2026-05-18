import {
  Activity,
  ArrowUpRight,
  CalendarClock,
  CircleDollarSign,
  ClipboardCheck,
  LogOut,
  ShieldCheck,
  Sparkles,
  TimerReset,
  UserCircle2,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useEmployeeDashboard } from "@/hooks/use-employee-dashboard";
import { usePlatformStatus } from "@/hooks/use-platform-status";
import { useAuth } from "@/providers/auth-provider";

export function DashboardPage() {
  const { auth, logout } = useAuth();
  const { data: dashboard } = useEmployeeDashboard();
  const { data: platformStatus } = usePlatformStatus();
  const navigate = useNavigate();
  const displayName = auth?.email?.split("@")[0]?.replace(/[._-]/g, " ") ?? "Employee";
  const roleLabel = auth?.roles?.join(", ") ?? "No role assigned";
  const canAccessManagerDashboard = auth?.roles.some((role) => role === "ADMIN" || role === "HR_MANAGER") ?? false;

  function handleLogout() {
    logout();
    navigate("/login", { replace: true });
  }

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top_left,_rgba(255,245,224,0.9),_transparent_35%),linear-gradient(180deg,_#fffaf3_0%,_#f1e2cb_100%)] text-stone-900">
      <main className="mx-auto flex min-h-screen max-w-7xl flex-col gap-6 px-6 py-8 md:px-10">
        <section className="overflow-hidden rounded-[2rem] border border-amber-200/70 bg-[linear-gradient(135deg,_rgba(255,255,255,0.96),_rgba(255,244,222,0.9))] shadow-[0_24px_80px_rgba(87,63,29,0.09)]">
          <div className="grid gap-8 p-8 md:grid-cols-[1.5fr_1fr] md:p-10">
            <div className="space-y-5">
              <span className="inline-flex rounded-full border border-amber-300 bg-amber-100 px-3 py-1 text-xs font-semibold uppercase tracking-[0.2em] text-amber-900">
                Employee Dashboard
              </span>
              <div className="space-y-3">
                <h1 className="font-['Georgia'] text-4xl leading-tight md:text-5xl">
                  Welcome back, {displayName}
                </h1>
                <p className="max-w-2xl text-stone-600">
                  Here&apos;s your personal workspace for attendance, leave, payroll, and performance visibility. This
                  Day 12 screen turns the protected route into a usable employee dashboard with core HR metrics.
                </p>
              </div>
              <div className="flex flex-wrap gap-3 text-sm text-stone-700">
                <span className="inline-flex items-center gap-2 rounded-full bg-white/85 px-4 py-2 ring-1 ring-amber-200">
                  <UserCircle2 className="size-4" />
                  {auth?.email ?? "Unknown user"}
                </span>
                <span className="inline-flex items-center gap-2 rounded-full bg-white/85 px-4 py-2 ring-1 ring-amber-200">
                  <ShieldCheck className="size-4" />
                  {roleLabel}
                </span>
              </div>
            </div>

            <div className="flex flex-col justify-between gap-4 rounded-[1.75rem] border border-stone-200/70 bg-white/80 p-6">
              <div className="space-y-2">
                <p className="text-sm font-semibold uppercase tracking-[0.18em] text-stone-500">Quick actions</p>
                <div className="grid gap-3">
                  <div className="rounded-2xl bg-stone-950 px-4 py-4 text-stone-50">
                    <p className="text-sm font-semibold">Attendance ready</p>
                    <p className="mt-1 text-sm text-stone-300">Use this area next to connect check-in and history views.</p>
                  </div>
                  <div className="rounded-2xl bg-amber-50 px-4 py-4 text-stone-800 ring-1 ring-amber-200">
                    <p className="text-sm font-semibold">Leave tracker</p>
                    <p className="mt-1 text-sm text-stone-600">Pending requests and balances can be surfaced here next.</p>
                  </div>
                  {canAccessManagerDashboard ? (
                    <button
                      className="rounded-2xl bg-emerald-50 px-4 py-4 text-left text-stone-800 ring-1 ring-emerald-200 transition hover:bg-emerald-100"
                      onClick={() => navigate("/app/manager")}
                      type="button"
                    >
                      <p className="text-sm font-semibold">Manager workspace</p>
                      <p className="mt-1 text-sm text-stone-600">Open the team overview dashboard for approvals and performance signals.</p>
                    </button>
                  ) : null}
                </div>
              </div>
              <Button variant="secondary" onClick={handleLogout}>
                <LogOut className="size-4" />
                Sign out
              </Button>
            </div>
          </div>
        </section>

        <section className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
          {dashboard?.metrics.map((metric) => (
            <Card key={metric.label} className="border-stone-200/80 bg-white/85 shadow-[0_16px_48px_rgba(92,65,25,0.08)]">
              <CardHeader className="space-y-3">
                <div className="flex items-center justify-between">
                  <p className="text-sm font-semibold uppercase tracking-[0.18em] text-stone-500">{metric.label}</p>
                  <ArrowUpRight className="size-4 text-amber-700" />
                </div>
                <CardTitle className="text-3xl">{metric.value}</CardTitle>
                <CardDescription>{metric.detail}</CardDescription>
              </CardHeader>
              <CardContent className="text-sm font-medium text-emerald-700">{metric.trend}</CardContent>
            </Card>
          ))}
        </section>

        <section className="grid gap-4 xl:grid-cols-[1.5fr_1fr]">
          <Card className="border-stone-200/80 bg-white/88 shadow-[0_18px_60px_rgba(87,63,29,0.08)]">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-amber-100 p-3 text-amber-900">
                  <Sparkles className="size-6" />
                </div>
                <div>
                  <CardTitle>Key metrics overview</CardTitle>
                  <CardDescription>A starter employee-facing snapshot for the most important HR signals.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="grid gap-3 md:grid-cols-3">
              {dashboard?.highlights.map((highlight) => (
                <div key={highlight.title} className="rounded-[1.5rem] bg-stone-50 p-5 ring-1 ring-stone-200/70">
                  <p className="text-xs font-semibold uppercase tracking-[0.18em] text-stone-500">{highlight.title}</p>
                  <p className="mt-3 text-2xl font-bold text-stone-900">{highlight.value}</p>
                  <p className="mt-2 text-sm leading-6 text-stone-600">{highlight.description}</p>
                </div>
              ))}
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88 shadow-[0_18px_60px_rgba(87,63,29,0.08)]">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-emerald-100 p-3 text-emerald-900">
                  <Activity className="size-6" />
                </div>
                <div>
                  <CardTitle>Recent activity</CardTitle>
                  <CardDescription>Useful for giving employees confidence that their actions were recorded.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-4">
              {dashboard?.activities.map((activity) => (
                <div key={`${activity.title}-${activity.time}`} className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                  <div className="flex items-center justify-between gap-3">
                    <p className="font-semibold text-stone-900">{activity.title}</p>
                    <span className="text-xs font-semibold uppercase tracking-[0.15em] text-stone-500">{activity.time}</span>
                  </div>
                  <p className="mt-2 text-sm leading-6 text-stone-600">{activity.description}</p>
                </div>
              ))}
            </CardContent>
          </Card>
        </section>

        <section className="grid gap-4 lg:grid-cols-3">
          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-sky-100 p-3 text-sky-900">
                  <TimerReset className="size-6" />
                </div>
                <div>
                  <CardTitle>Attendance panel</CardTitle>
                  <CardDescription>Starter surface for today&apos;s work rhythm and history.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3 text-sm leading-6 text-stone-600">
              <p><strong>Status:</strong> Checked in</p>
              <p><strong>Today&apos;s start:</strong> 9:04 AM</p>
              <p><strong>Monthly consistency:</strong> 96% presence recorded</p>
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-rose-100 p-3 text-rose-900">
                  <CalendarClock className="size-6" />
                </div>
                <div>
                  <CardTitle>Leave panel</CardTitle>
                  <CardDescription>Quick balance and workflow visibility for employees.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3 text-sm leading-6 text-stone-600">
              <p><strong>Available balance:</strong> 7 days</p>
              <p><strong>Pending approvals:</strong> 1 request</p>
              <p><strong>Next leave:</strong> June 7 to June 8</p>
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-violet-100 p-3 text-violet-900">
                  <CircleDollarSign className="size-6" />
                </div>
                <div>
                  <CardTitle>Payroll and review</CardTitle>
                  <CardDescription>Connects Days 10 and 11 into the employee-facing UI.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3 text-sm leading-6 text-stone-600">
              <p><strong>Latest net pay:</strong> Rs 59,000</p>
              <p><strong>Review score:</strong> 4.33 / 5</p>
              <p><strong>Top signal:</strong> Teamwork rated highest</p>
            </CardContent>
          </Card>
        </section>

        <section className="grid gap-4 lg:grid-cols-[1.2fr_0.8fr]">
          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-stone-900 p-3 text-stone-50">
                  <ClipboardCheck className="size-6" />
                </div>
                <div>
                  <CardTitle>Upcoming employee actions</CardTitle>
                  <CardDescription>Simple, beginner-friendly prompts that guide what the user can do next.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="grid gap-3 md:grid-cols-3">
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Mark attendance</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Confirm today&apos;s checkout to keep attendance data complete.</p>
              </div>
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Track leave status</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Follow pending approval progress without needing manager follow-up.</p>
              </div>
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Review performance notes</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Check strengths and improvement areas after each review cycle.</p>
              </div>
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <CardTitle>Platform readiness</CardTitle>
              <CardDescription>Small technical confidence check alongside the employee UI.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-3">
              {platformStatus?.map((service) => (
                <div key={service.name} className="flex items-start justify-between gap-4 rounded-[1.25rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                  <div>
                    <p className="font-semibold text-stone-900">{service.name}</p>
                    <p className="mt-1 text-sm leading-6 text-stone-600">{service.description}</p>
                  </div>
                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.15em] ${
                      service.status === "Ready"
                        ? "bg-emerald-100 text-emerald-900"
                        : "bg-amber-100 text-amber-900"
                    }`}
                  >
                    {service.status}
                  </span>
                </div>
              ))}
            </CardContent>
          </Card>
        </section>
      </main>
    </div>
  );
}
