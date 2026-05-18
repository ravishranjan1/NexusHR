import { useQuery } from "@tanstack/react-query";

export type DashboardMetric = {
  label: string;
  value: string;
  detail: string;
  trend: string;
};

export type DashboardHighlight = {
  title: string;
  value: string;
  description: string;
};

export type DashboardActivity = {
  title: string;
  time: string;
  description: string;
};

type EmployeeDashboardData = {
  metrics: DashboardMetric[];
  highlights: DashboardHighlight[];
  activities: DashboardActivity[];
};

const starterDashboardData: EmployeeDashboardData = {
  metrics: [
    {
      label: "Attendance streak",
      value: "12 days",
      detail: "Consistent check-ins this month",
      trend: "+2 from last week",
    },
    {
      label: "Leave balance",
      value: "7 days",
      detail: "Casual + earned leave available",
      trend: "1 request pending",
    },
    {
      label: "Latest payslip",
      value: "Rs 59,000",
      detail: "Net salary for May 2026",
      trend: "Generated successfully",
    },
    {
      label: "Performance score",
      value: "4.33 / 5",
      detail: "Most recent completed review",
      trend: "Strong teamwork rating",
    },
  ],
  highlights: [
    {
      title: "This week",
      value: "4 / 5 days present",
      description: "One flexible-day entry is still waiting for final checkout sync.",
    },
    {
      title: "Next payroll milestone",
      value: "May 31, 2026",
      description: "Payroll cutoff closes at the end of the month before payslip generation.",
    },
    {
      title: "Review window",
      value: "Q2 2026 active",
      description: "Goal achievement, teamwork, and punctuality are the current tracked categories.",
    },
  ],
  activities: [
    {
      title: "Attendance updated",
      time: "Today, 9:04 AM",
      description: "Check-in captured for the current workday.",
    },
    {
      title: "Leave request submitted",
      time: "Yesterday, 6:20 PM",
      description: "Personal leave request for June 7 to June 8 is awaiting manager approval.",
    },
    {
      title: "Payslip generated",
      time: "May 1, 2026",
      description: "April payroll was processed and made available in the payroll module.",
    },
  ],
};

export function useEmployeeDashboard() {
  return useQuery({
    queryKey: ["employee-dashboard"],
    queryFn: async () => starterDashboardData,
    initialData: starterDashboardData,
  });
}
