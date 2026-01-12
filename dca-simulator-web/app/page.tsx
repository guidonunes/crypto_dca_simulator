import SimulationForm from "@/components/SimulationForm";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24 bg-slate-900 text-white">
      <h1 className="text-5xl font-extrabold mb-8 text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-emerald-400">
        DCA Simulator
      </h1>

      {/* Render our new form here */}
      <SimulationForm />

    </main>
  );
}
