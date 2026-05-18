import { useQuery } from "@tanstack/react-query";

export type ManagerMetric = {
  label: string;
  value: string;
  detail: string;
  tone: "emerald" | "amber" | "sky" | "violet";
};

export type TeamMemberSnapshot = {
  name: string;
  role: string;
  attendance: string;
  leaveStatus: string;
  performance: string;
};

export type ApprovalQueueItem = {
  type: string;
  employee: string;
  summary: string;
  priority: "High" | "Medium";
};

type ManagerDashboardData = {
  metrics: ManagerMetric[];
  teamMembers: TeamMemberSnapshot[];
  approvals: ApprovalQueueItem[];
  insights: string[];
};

const starterManagerDashboardData: ManagerDashboardData = {
  metrics: [
    {
      label: "Team size",
      value: "18",
      detail: "Active direct and cross-functional reports",
      tone: "sky",
    },
    {
      label: "Present today",
      value: "16",
      detail: "Two employees are on approved leave",
      tone: "emerald",
    },
    {
      label: "Pending approvals",
      value: "4",
      detail: "Leave and performance actions waiting",
      tone: "amber",
    },
    {
      label: "Average review score",
      value: "4.21 / 5",
      detail: "Based on the latest completed review cycle",
      tone: "violet",
    },
  ],
  teamMembers: [
    {
      name: "Amit Sharma",
      role: "Java Developer",
      attendance: "Present",
      leaveStatus: "No active leave",
      performance: "4.33 / 5",
    },
    {
      name: "Sneha Verma",
      role: "QA Engineer",
      attendance: "Present",
      leaveStatus: "Pending leave request",
      performance: "4.10 / 5",
    },
    {
      name: "Rahul Nair",
      role: "Frontend Developer",
      attendance: "On leave",
      leaveStatus: "Approved until May 20",
      performance: "4.45 / 5",
    },
    {
      name: "Priya Singh",
      role: "HR Analyst",
      attendance: "Present",
      leaveStatus: "No active leave",
      performance: "3.95 / 5",
    },
  ],
  approvals: [
    {
      type: "Leave request",
      employee: "Sneha Verma",
      summary: "Needs review for May 22 to May 23 personal leave.",
      priority: "High",
    },
    {
      type: "Performance review",
      employee: "Amit Sharma",
      summary: "Q2 2026 review notes are ready for final manager comments.",
      priority: "Medium",
    },
    {
      type: "Attendance exception",
      employee: "Karan Patel",
      summary: "Missing checkout on Friday needs validation.",
      priority: "High",
    },
  ],
  insights: [
    "Team attendance is stable this week with a 92% same-day presence rate.",
    "Performance trends are strongest in teamwork, while punctuality needs follow-up for two employees.",
    "The current approval queue is small enough to clear today, which will help keep leave and review workflows moving.",
  ],
};

export function useManagerDashboard() {
  return useQuery({
    queryKey: ["manager-dashboard"],
    queryFn: async () => starterManagerDashboardData,
    initialData: starterManagerDashboardData,
  });
}
