import {
  ArrowLeft,
  BadgeCheck,
  BellRing,
  BriefcaseBusiness,
  ClipboardList,
  LogOut,
  UsersRound,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useManagerDashboard } from "@/hooks/use-manager-dashboard";
import { useAuth } from "@/providers/auth-provider";

const toneClasses = {
  emerald: "bg-emerald-100 text-emerald-900",
  amber: "bg-amber-100 text-amber-900",
  sky: "bg-sky-100 text-sky-900",
  violet: "bg-violet-100 text-violet-900",
} as const;

export function ManagerDashboardPage() {
  const { auth, logout } = useAuth();
  const { data } = useManagerDashboard();
  const navigate = useNavigate();
  const displayName = auth?.email?.split("@")[0]?.replace(/[._-]/g, " ") ?? "Manager";

  function handleLogout() {
    logout();
    navigate("/login", { replace: true });
  }

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top_right,_rgba(216,244,233,0.85),_transparent_32%),linear-gradient(180deg,_#f7fbfa_0%,_#deede6_100%)] text-stone-900">
      <main className="mx-auto flex min-h-screen max-w-7xl flex-col gap-6 px-6 py-8 md:px-10">
        <section className="overflow-hidden rounded-[2rem] border border-emerald-200/70 bg-[linear-gradient(140deg,_rgba(255,255,255,0.96),_rgba(230,245,238,0.96))] shadow-[0_24px_80px_rgba(40,86,64,0.1)]">
          <div className="grid gap-8 p-8 md:grid-cols-[1.6fr_1fr] md:p-10">
            <div className="space-y-5">
              <span className="inline-flex rounded-full border border-emerald-300 bg-emerald-100 px-3 py-1 text-xs font-semibold uppercase tracking-[0.2em] text-emerald-900">
                Manager Dashboard
              </span>
              <div className="space-y-3">
                <h1 className="font-['Georgia'] text-4xl leading-tight md:text-5xl">
                  Team overview for {displayName}
                </h1>
                <p className="max-w-3xl text-stone-600">
                  This Day 13 workspace brings attendance, leave, payroll, and performance signals together for
                  managers. It is designed to help you see team health quickly and act on approvals without hunting
                  across separate screens.
                </p>
              </div>
              <div className="flex flex-wrap gap-3 text-sm text-stone-700">
                <span className="inline-flex items-center gap-2 rounded-full bg-white/90 px-4 py-2 ring-1 ring-emerald-200">
                  <BriefcaseBusiness className="size-4" />
                  {auth?.roles.join(", ") ?? "No role assigned"}
                </span>
                <span className="inline-flex items-center gap-2 rounded-full bg-white/90 px-4 py-2 ring-1 ring-emerald-200">
                  <UsersRound className="size-4" />
                  Team operations view enabled
                </span>
              </div>
            </div>

            <div className="flex flex-col justify-between gap-4 rounded-[1.75rem] border border-stone-200/70 bg-white/85 p-6">
              <div className="space-y-3">
                <p className="text-sm font-semibold uppercase tracking-[0.18em] text-stone-500">Manager actions</p>
                <button
                  className="flex w-full items-start gap-3 rounded-2xl bg-emerald-50 px-4 py-4 text-left ring-1 ring-emerald-200 transition hover:bg-emerald-100"
                  onClick={() => navigate("/app")}
                  type="button"
                >
                  <ArrowLeft className="mt-0.5 size-4 text-emerald-900" />
                  <div>
                    <p className="text-sm font-semibold text-stone-900">Back to employee dashboard</p>
                    <p className="mt-1 text-sm text-stone-600">Switch back to the personal employee workspace view.</p>
                  </div>
                </button>
              </div>
              <Button variant="secondary" onClick={handleLogout}>
                <LogOut className="size-4" />
                Sign out
              </Button>
            </div>
          </div>
        </section>

        <section className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
          {data?.metrics.map((metric) => (
            <Card key={metric.label} className="border-stone-200/80 bg-white/88 shadow-[0_16px_48px_rgba(40,86,64,0.08)]">
              <CardHeader className="space-y-3">
                <span className={`inline-flex w-fit rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.15em] ${toneClasses[metric.tone]}`}>
                  {metric.label}
                </span>
                <CardTitle className="text-3xl">{metric.value}</CardTitle>
                <CardDescription>{metric.detail}</CardDescription>
              </CardHeader>
            </Card>
          ))}
        </section>

        <section className="grid gap-4 xl:grid-cols-[1.3fr_0.7fr]">
          <Card className="border-stone-200/80 bg-white/88 shadow-[0_18px_60px_rgba(40,86,64,0.08)]">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-sky-100 p-3 text-sky-900">
                  <UsersRound className="size-6" />
                </div>
                <div>
                  <CardTitle>Team snapshot</CardTitle>
                  <CardDescription>A clear overview of the people, status, and recent performance signals you manage.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3">
              {data?.teamMembers.map((member) => (
                <div key={member.name} className="grid gap-3 rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70 md:grid-cols-[1.2fr_1fr_1fr_1fr]">
                  <div>
                    <p className="font-semibold text-stone-900">{member.name}</p>
                    <p className="mt-1 text-sm text-stone-600">{member.role}</p>
                  </div>
                  <div className="text-sm text-stone-600">
                    <p className="font-medium text-stone-900">Attendance</p>
                    <p className="mt-1">{member.attendance}</p>
                  </div>
                  <div className="text-sm text-stone-600">
                    <p className="font-medium text-stone-900">Leave</p>
                    <p className="mt-1">{member.leaveStatus}</p>
                  </div>
                  <div className="text-sm text-stone-600">
                    <p className="font-medium text-stone-900">Performance</p>
                    <p className="mt-1">{member.performance}</p>
                  </div>
                </div>
              ))}
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88 shadow-[0_18px_60px_rgba(40,86,64,0.08)]">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-amber-100 p-3 text-amber-900">
                  <BellRing className="size-6" />
                </div>
                <div>
                  <CardTitle>Approval queue</CardTitle>
                  <CardDescription>Items that need manager attention soon.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3">
              {data?.approvals.map((item) => (
                <div key={`${item.type}-${item.employee}`} className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                  <div className="flex items-center justify-between gap-3">
                    <p className="font-semibold text-stone-900">{item.type}</p>
                    <span className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.15em] ${
                      item.priority === "High" ? "bg-rose-100 text-rose-900" : "bg-amber-100 text-amber-900"
                    }`}>
                      {item.priority}
                    </span>
                  </div>
                  <p className="mt-2 text-sm font-medium text-stone-800">{item.employee}</p>
                  <p className="mt-1 text-sm leading-6 text-stone-600">{item.summary}</p>
                </div>
              ))}
            </CardContent>
          </Card>
        </section>

        <section className="grid gap-4 lg:grid-cols-[0.9fr_1.1fr]">
          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-violet-100 p-3 text-violet-900">
                  <BadgeCheck className="size-6" />
                </div>
                <div>
                  <CardTitle>Manager insights</CardTitle>
                  <CardDescription>Starter summaries that prepare the UI for richer analytics later.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="space-y-3">
              {data?.insights.map((insight) => (
                <div key={insight} className="rounded-[1.5rem] bg-stone-50 p-4 text-sm leading-6 text-stone-600 ring-1 ring-stone-200/70">
                  {insight}
                </div>
              ))}
            </CardContent>
          </Card>

          <Card className="border-stone-200/80 bg-white/88">
            <CardHeader>
              <div className="flex items-center gap-3">
                <div className="rounded-2xl bg-emerald-100 p-3 text-emerald-900">
                  <ClipboardList className="size-6" />
                </div>
                <div>
                  <CardTitle>Team overview actions</CardTitle>
                  <CardDescription>Simple prompts that show what the manager dashboard is meant to help with.</CardDescription>
                </div>
              </div>
            </CardHeader>
            <CardContent className="grid gap-3 md:grid-cols-2">
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Review leave approvals</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Clear requests quickly so employee schedules stay predictable.</p>
              </div>
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Watch attendance exceptions</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Catch missing checkouts or irregular patterns before payroll closes.</p>
              </div>
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Track performance readiness</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">See which reviews still need comments, follow-ups, or completion.</p>
              </div>
              <div className="rounded-[1.5rem] bg-stone-50 p-4 ring-1 ring-stone-200/70">
                <p className="font-semibold text-stone-900">Balance workload signals</p>
                <p className="mt-2 text-sm leading-6 text-stone-600">Use team visibility to spot risk before morale or delivery slips.</p>
              </div>
            </CardContent>
          </Card>
        </section>
      </main>
    </div>
  );
}
