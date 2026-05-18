import { FormEvent, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { login, signup } from "@/lib/api";
import { useAuth } from "@/providers/auth-provider";
import { AuthResponse, LoginPayload, SignupPayload } from "@/types/auth";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";

type AuthMode = "login" | "signup";

type AuthFormProps = {
  mode: AuthMode;
};

export function AuthForm({ mode }: AuthFormProps) {
  const navigate = useNavigate();
  const { saveAuth } = useAuth();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const isLogin = mode === "login";

  const pageCopy = useMemo(
    () =>
      isLogin
        ? {
            title: "Welcome back",
            description: "Sign in with your HR account to access the NexusHR workspace.",
            cta: "Sign in",
            switchText: "New here?",
            switchLink: "/signup",
            switchLabel: "Create an account",
          }
        : {
            title: "Create your account",
            description: "Start with an employee account that can use the secured NexusHR flows.",
            cta: "Create account",
            switchText: "Already have an account?",
            switchLink: "/login",
            switchLabel: "Sign in",
          },
    [isLogin],
  );

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsSubmitting(true);
    setError(null);

    try {
      const response = isLogin
        ? await login({
            email: form.email,
            password: form.password,
          } satisfies LoginPayload)
        : await signup({
            firstName: form.firstName,
            lastName: form.lastName,
            email: form.email,
            password: form.password,
          } satisfies SignupPayload);

      saveAuth(response satisfies AuthResponse);
      navigate(getHomeRoute(response.roles), { replace: true });
    } catch (submissionError) {
      setError(submissionError instanceof Error ? submissionError.message : "Authentication failed.");
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <Card className="w-full max-w-xl border-stone-200/80 bg-white/85 shadow-[0_28px_80px_rgba(90,68,40,0.12)]">
      <CardHeader className="space-y-3">
        <CardTitle className="text-3xl">{pageCopy.title}</CardTitle>
        <CardDescription>{pageCopy.description}</CardDescription>
      </CardHeader>
      <CardContent>
        <form className="space-y-5" onSubmit={handleSubmit}>
          {!isLogin ? (
            <div className="grid gap-4 md:grid-cols-2">
              <Field
                label="First name"
                value={form.firstName}
                onChange={(value) => setForm((current) => ({ ...current, firstName: value }))}
              />
              <Field
                label="Last name"
                value={form.lastName}
                onChange={(value) => setForm((current) => ({ ...current, lastName: value }))}
              />
            </div>
          ) : null}

          <Field
            label="Email"
            type="email"
            value={form.email}
            onChange={(value) => setForm((current) => ({ ...current, email: value }))}
          />

          <Field
            label="Password"
            type="password"
            value={form.password}
            onChange={(value) => setForm((current) => ({ ...current, password: value }))}
          />

          {error ? (
            <div className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-rose-700">
              {error}
            </div>
          ) : null}

          <div className="space-y-3">
            <Button className="w-full" disabled={isSubmitting} type="submit">
              {isSubmitting ? "Please wait..." : pageCopy.cta}
            </Button>
            <p className="text-center text-sm text-stone-500">
              {pageCopy.switchText}{" "}
              <a className="font-semibold text-stone-900 underline-offset-4 hover:underline" href={pageCopy.switchLink}>
                {pageCopy.switchLabel}
              </a>
            </p>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}

function getHomeRoute(roles: string[]) {
  return roles.some((role) => role === "ADMIN" || role === "HR_MANAGER") ? "/app/manager" : "/app";
}

type FieldProps = {
  label: string;
  value: string;
  onChange: (value: string) => void;
  type?: string;
};

function Field({ label, value, onChange, type = "text" }: FieldProps) {
  return (
    <label className="flex flex-col gap-2 text-sm font-medium text-stone-700">
      {label}
      <input
        className="rounded-2xl border border-stone-300 bg-stone-50 px-4 py-3 text-base text-stone-900 outline-none transition focus:border-stone-500 focus:bg-white"
        onChange={(event) => onChange(event.target.value)}
        required
        type={type}
        value={value}
      />
    </label>
  );
}
