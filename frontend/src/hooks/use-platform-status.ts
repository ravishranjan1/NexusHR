import { useQuery } from "@tanstack/react-query";

type ServiceStatus = {
  name: string;
  description: string;
  status: "Ready" | "Pending";
};

const starterStatus: ServiceStatus[] = [
  {
    name: "Auth Service",
    description: "JWT issuing service and Redis-backed session caching.",
    status: "Ready",
  },
  {
    name: "Employee Service",
    description: "Employee CRUD, attendance, leave workflows, and JWT validation.",
    status: "Ready",
  },
  {
    name: "Frontend App",
    description: "React workspace bootstrapped for auth pages and dashboards.",
    status: "Pending",
  },
];

export function usePlatformStatus() {
  return useQuery({
    queryKey: ["platform-status"],
    queryFn: async () => starterStatus,
    initialData: starterStatus,
  });
}
