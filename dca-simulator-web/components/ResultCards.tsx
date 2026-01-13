import { SimulationResponse } from "@/services/api";


interface Props {
  data: SimulationResponse;
}

export default function ResultCards({ data }: Props) {
  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  const isProfit = data.profit >= 0;
  const profitColor = isProfit ? "text-green-500" : "text-red-500";


    return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mt-8 w-full max-w-4xl px-4 sm:px-0">
      {/* Card 1: Total Invested */}
      <div className="bg-slate-800 p-6 rounded-lg shadow-lg text-center border border-slate-700">
        <h3 className="text-slate-400 text-sm uppercase tracking-wider font-semibold">
          Total Invested
        </h3>
        <p className="text-3xl font-bold text-white mt-2">
          {formatCurrency(data.investedAmount).format(data.investedAmount)}
        </p>
      </div>

      {/* Card 2: Final Value */}
      <div className="bg-slate-800 p-6 rounded-lg shadow-lg text-center border border-slate-700">
        <h3 className="text-slate-400 text-sm uppercase tracking-wider font-semibold">
          Final Value
        </h3>
        <p className="text-3xl font-bold text-blue-400 mt-2">
          {formatCurrency(data.finalValue).format(data.finalValue)}
        </p>
      </div>

      {/* Card 3: Profit / Loss */}
      <div className="bg-slate-800 p-6 rounded-lg shadow-lg text-center border border-slate-700">
        <h3 className="text-slate-400 text-sm uppercase tracking-wider font-semibold">
          Total Profit
        </h3>
        <p className={`text-3xl font-bold mt-2 ${profitColor}`}>
          {isProfit ? "+" : ""}{formatCurrency(data.profit).format(data.profit)}
        </p>
        <p className={`text-sm mt-1 ${profitColor}`}>
          {isProfit ? "▲" : "▼"} {data.gainPercent.toFixed(2)}%
        </p>
      </div>
    </div>
  );

}
