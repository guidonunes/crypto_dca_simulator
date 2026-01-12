"use client";

import { useState } from "react";


export default function SimulationForm() {
  const [asset, setAsset] = useState("BTC");
  const [amount, setAmount] = useState(100);

  return (
    <div className="bg-slate-800 p-6 rounded-lg shadow-lg w-full max-w-md">
      <h2 className="text-2xl font-bold mb-6 text-white text-center">
        Crypto Configuration
      </h2>
      <form className="space-y-4">
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
      </form>
    </div>
  )
}
