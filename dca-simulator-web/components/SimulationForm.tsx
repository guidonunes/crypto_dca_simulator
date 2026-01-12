"use client";

import { useState } from "react";


export default function SimulationForm() {
  const [asset, setAsset] = useState("BTC");
  const [amount, setAmount] = useState(100);
  const [strategy, setStrategy] = useState("DCA");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Submitting:", { asset, amount, strategy });
    alert(`Running Simulation for ${asset} with $${amount}`);
  };

  return (
    <div className="bg-slate-800 p-6 rounded-lg shadow-lg w-full max-w-md">
      <h2 className="text-2xl font-bold mb-6 text-white text-center">
        Crypto Configuration
      </h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* ASSET SELECTION */}
        <div>
          <label className="block text-slate-300 mb-2"> Asset: </label>
            <select
              value={asset}
              onChange={(e) => setAsset(e.target.value)}
              className="w-full p-2 rounded bg-slate-700 text-white border border-slate-600 focus:outline-none focus:border-blue-500 ">
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
            className="w-full p-2 rounded bg-slate-700 text-white border border-slate-600 focus:outline-none focus:border-blue-500"
          />
        </div>
        {/* STRATEGY SELECTION */}
        <div>
          <label>Strategy</label>
          <select
            value={strategy}
            onChange={(e)=> setStrategy(e.target.value)}
            className="w-full p-2 rounded bg-slate-700 text-white border border-slate-600 focus:outline-none focus:border-blue-500"
          >
            <option value="DCA">Dollar-Cost Averaging (DCA)</option>
            <option value="LumpSum">Lump Sum (All at once)</option>
          </select>
        </div>
        {/* SUBMIT BUTTON */}
        <button
          type="submit"
          className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded transition duration-200"
        >
          Simulate
        </button>
      </form>
    </div>
  )
}
