import SimulationForm from "@/components/SimulationForm";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24 bg-slate-900 text-white">
      <h1 className="text-4xl font-bold mb-4">🚀 DCA Simulator</h1>
      <SimulationForm />
    </main>
  );
}
