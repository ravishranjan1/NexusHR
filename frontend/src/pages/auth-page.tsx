import { ShieldCheck } from "lucide-react";
import { AuthForm } from "@/components/auth-form";

type AuthMode = "login" | "signup";

type AuthPageProps = {
  mode: AuthMode;
};

export function AuthPage({ mode }: AuthPageProps) {
  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top_right,_rgba(245,158,11,0.22),_transparent_26%),linear-gradient(160deg,_#f8f4ee_0%,_#efe5d4_45%,_#dfd4c0_100%)]">
      <main className="mx-auto grid min-h-screen max-w-7xl items-center gap-10 px-6 py-10 md:grid-cols-[1fr_1fr] md:px-10">
        <section className="space-y-6">
          <span className="inline-flex items-center gap-2 rounded-full border border-stone-300 bg-white/70 px-4 py-2 text-xs font-semibold uppercase tracking-[0.22em] text-stone-700">
            <ShieldCheck className="size-4" />
            Day 9 Authentication Pages
          </span>
          <div className="space-y-4">
            <h1 className="max-w-xl font-['Georgia'] text-4xl leading-tight text-stone-900 md:text-6xl">
              Secure access for HR teams starts here.
            </h1>
            <p className="max-w-xl text-base leading-7 text-stone-600 md:text-lg">
              The frontend now connects to the backend auth flow with clean forms, token persistence,
              and protected route handling so the Week 2 dashboard work can build on a real login journey.
            </p>
          </div>
          <div className="grid max-w-xl gap-3 text-sm text-stone-600">
            <div className="rounded-2xl border border-white/70 bg-white/70 px-4 py-3">
              Uses `auth-service` signup and login APIs.
            </div>
            <div className="rounded-2xl border border-white/70 bg-white/70 px-4 py-3">
              Stores access and refresh tokens in local browser storage for now.
            </div>
            <div className="rounded-2xl border border-white/70 bg-white/70 px-4 py-3">
              Blocks `/app` until a valid frontend session exists.
            </div>
          </div>
        </section>

        <section className="flex justify-center">
          <AuthForm mode={mode} />
        </section>
      </main>
    </div>
  );
}
