"use client";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";
import { MonthlyData } from "@/services/api";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

interface Props {
  data: MonthlyData[];
}


export default function SimulationChart({ data }: Props) {
  const chartData = {
    labels: data.map((entry) => `Month ${entry.month}`),
    datasets: [
      {
        label: "Portfolio Value",
        data: data.map((entry) => entry.portfolioValue),
        borderColor: "rgba(59, 130, 246, 1)", // Blue-500
        backgroundColor: "rgba(59, 130, 246, 0.5)",
        tension: 0.2,
      },
      {
        label: "Invested Amount",
        data: data.map((entry) => entry.investedAmount),
        borderColor: "rgba(16, 185, 129, 1)", // Green-500
        backgroundColor: "rgba(16, 185, 129, 0.5)",
        borderDash: [5, 5],
      }
    ]
  }

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top" as const, labels: { color: "white" } },
      title: {
        display: true,
        text: "Simulation Over Time",
        color: "white",
      },
    },
    scales: {
      y: { ticks: { color: "#94a3b8" }, grid: { color: "#334155" } },
      x: { ticks: { color: "#94a3b8" }, grid: { display: false } },
    },
  }

  return (
    <div className="bg-slate-800 p-4 rounded-lg shadow-lg mt-6 w-full max-w-4xl border border-slate-700">
      <Line options={options} data={chartData} />
    </div>
  );
}
