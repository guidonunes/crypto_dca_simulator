export interface SimulationRequest {
  assetName: string;
  amount: number;
  strategy: string;
}

export interface MonthlyData {
  month: number;
  investedAmount: number;
  portfolioValue: number;
}

export interface SimulationResponse {
  id: number;
  assetName: string;
  investedAmount: number;
  finalValue: number;
  profit: number;
  gainPercent: number;
  chartData: MonthlyData[];
}

export async function runSimulation(data: SimulationRequest): Promise<SimulationResponse> {
  const response = await fetch("http://localhost:8080/api/simulations", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Failed to run simulation");
  }

  return response.json(
  )
}
