import SimulationForm from "@/components/SimulationForm";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-4 sm:p-8 md:p-16 lg:p-24 bg-slate-900 text-white">
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold mb-6 sm:mb-8 text-transparent bg-clip-text bg-linear-to-r from-blue-400 to-emerald-400 text-center px-4">
        DCA Simulator
      </h1>

      {/* Render our new form here */}
      <SimulationForm />

    </main>
  );
}
