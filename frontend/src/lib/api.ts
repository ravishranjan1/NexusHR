import { AuthResponse, LoginPayload, SignupPayload } from "@/types/auth";

const AUTH_BASE_URL = "http://localhost:8081/api/auth";

async function request<T>(url: string, options: RequestInit) {
  const response = await fetch(url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers ?? {}),
    },
  });

  if (!response.ok) {
    const errorData = (await response.json().catch(() => null)) as { message?: string } | null;
    throw new Error(errorData?.message ?? "Something went wrong. Please try again.");
  }

  return (await response.json()) as T;
}

export function login(payload: LoginPayload) {
  return request<AuthResponse>(`${AUTH_BASE_URL}/login`, {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function signup(payload: SignupPayload) {
  return request<AuthResponse>(`${AUTH_BASE_URL}/signup`, {
    method: "POST",
    body: JSON.stringify(payload),
  });
}
