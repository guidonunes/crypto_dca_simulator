import SimulationForm from "@/components/SimulationForm";
import Image from "next/image";


export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-4 sm:p-8 md:p-16 lg:p-24 bg-[#1e1e1e] text-white">
            <div className="mb-4">
        <Image
          src="/header-icon.png"
          alt="DCA Logo"
          width={80}
          height={80}
          className="drop-shadow-lg align-middle mx-auto"
        />
      </div>
        <h1 className="text-2xl font-bold mb-6 text-white text-center">
          Simulate Strategy
      </h1>
        <p className="text-slate-400 text-sm text-center mb-6">
          Data range: 01/01/2020 to 01/01/2026
        </p>
      <SimulationForm />
    </main>
  );
}
