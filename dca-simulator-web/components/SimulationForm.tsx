"use client";

import { useState } from "react";
import { runSimulation, SimulationResponse } from "@/services/api";
import ResultCards from './ResultCards';
import SimulationChart from "./SimulationChart";
import toast from "react-hot-toast";




export default function SimulationForm() {
  const [asset, setAsset] = useState("BTC");
  const [amount, setAmount] = useState(100);
  const [strategy, setStrategy] = useState("DCA");

  const [result, setResult] = useState<SimulationResponse | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setResult(null);

    const simulationPromise = runSimulation({
        assetName: asset,
        amount,
        strategy
    });

    toast.promise(simulationPromise, {
        loading: 'Crunching the numbers... 🧮',
        success: (data) => {
            setResult(data); // Save the data
            return `Success! ${asset} simulation complete.`;
        },
        error: (err) => {
            console.error(err);
            // This pulls the message from api.ts ("Asset not found", etc.)
            return `Error: ${err.message}`;
        },
    });

    try {
      const result = await runSimulation({
        assetName: asset,
        amount,
        strategy,
      });



      setResult(result);
      console.log("✅ API Success:", result);
      console.log("📊 Chart Data:", result.chartData);

    } catch (error) {
      console.error("❌ API Error:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full flex flex-col items-center px-4 sm:px-0">
      <div className="bg-[#3c3c3c] p-4 sm:p-6 rounded-lg shadow-lg shadow-amber-800 w-full max-w-4xl">
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* ASSET SELECTION */}
          <div>
            <label className="block text-slate-300 mb-2"> Asset: </label>
              <select
                value={asset}
                onChange={(e) => setAsset(e.target.value)}
                className="w-full p-2 rounded bg-[#4d4d4d] text-white border border-slate-600 focus:outline-none focus:border-blue-500 ">
              <option value="BTC">Bitcoin (BTC)</option>
              <option value="ETH">Ethereum (ETH)</option>
            </select>
          </div>
          {/* AMOUNT INPUT */}
          <div>
            <label>Monthly Investment (R$)</label>
            <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(Number(e.target.value))}
              className="w-full p-2 rounded bg-[#4d4d4d] text-white border border-slate-600 focus:outline-none focus:border-blue-500"
            />
          </div>
          {/* STRATEGY SELECTION */}
          <div>
            <label>Strategy</label>
            <select
              value={strategy}
              onChange={(e)=> setStrategy(e.target.value)}
              className="w-full p-2 rounded bg-[#4d4d4d] text-white border border-slate-600 focus:outline-none focus:border-blue-500"
            >
              <option value="DCA">Dollar-Cost Averaging (DCA)</option>
              <option value="LumpSum">Lump Sum (All at once)</option>
            </select>
          </div>
          {/* SUBMIT BUTTON */}
          <button
            type="submit"
            className="w-full bg-yellow-600 hover:bg-yellow-700 text-slate-900 font-bold py-2 px-4  mt-3 rounded transition duration-200"
          >
            {loading ? "Simulating..." : "Run Simulation"}
          </button>
        </form>
      </div>
      {result && <ResultCards data={result} />}

      {result && result.chartData && result.chartData.length > 0 && (
        <SimulationChart data={result.chartData} />
      )}
    </div>
  )
}
