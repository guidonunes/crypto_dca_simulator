package br.com.dcasimulator.service;


import br.com.dcasimulator.dto.MonthlyData;
import br.com.dcasimulator.dto.SimulationResponse;
import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.exception.ResourceNotFoundException;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.model.SimulationRequest;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.PriceRepository;
import br.com.dcasimulator.repository.SimulationResultRepository;
import br.com.dcasimulator.strategy.DcaStrategy;
import br.com.dcasimulator.strategy.LumpSumStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulationService {
    private final SimulationResultRepository repository;
    private final DcaStrategy dcaStrategy;
    private final LumpSumStrategy lumpSumStrategy;
    private final PriceRepository priceRepository;

    public SimulationService(SimulationResultRepository repository,
                             DcaStrategy dcaStrategy,
                             LumpSumStrategy lumpSumStrategy,
                             PriceRepository priceRepository) {
        this.repository = repository;
        this.dcaStrategy = dcaStrategy;
        this.lumpSumStrategy = lumpSumStrategy;
        this.priceRepository = priceRepository;
    }

    public SimulationResponse runSimulation(SimulationRequest request) {
        List<Price> allPrices = priceRepository.findByAssetSymbol(request.assetName());
        if (allPrices.isEmpty()) {
            throw new ResourceNotFoundException("Asset not found: " + request.assetName());
        }

        if(request.startDate() != null) {
            LocalDate firstDbDate = allPrices.get(allPrices.size() - 1).getDate();

            if(firstDbDate.isAfter(request.startDate())) {
                throw new IllegalArgumentException(
                        "Invalid Start Date! You asked for " + request.startDate() +
                                ", but our history for " + request.assetName() +
                                " only starts on " + firstDbDate
                );
            }
        }

        SimulationResult result;
        List<MonthlyData> chartData;

        if ("DCA".equalsIgnoreCase(request.strategy())) {
            result = runDca(allPrices, request.amount(), request.assetName());
            chartData = generateDcaChartData(allPrices, request.amount());
        } else {
            result = runLumpSum(allPrices, request.amount(), request.assetName());
            chartData = generateLumpSumChartData(allPrices, request.amount());
        }

        return new SimulationResponse(
                result.getAssetName(),
                BigDecimal.valueOf(result.getInvestedAmount()), // Converting Double -> BigDecimal
                BigDecimal.valueOf(result.getFinalValue()),
                BigDecimal.valueOf(result.getProfit()),
                BigDecimal.valueOf(result.getGainPercent()),
                chartData
        );

    }

    public SimulationResult runDca(List<Price> prices, BigDecimal amount, String assetName) {
        SimulationResult result = dcaStrategy.calculate(prices, amount);
        result.setAssetName(assetName);
        return repository.save(result);
    }

    public SimulationResult runLumpSum(List<Price> prices, BigDecimal amount, String assetName) {
        SimulationResult result = lumpSumStrategy.calculate(prices, amount);
        result.setAssetName(assetName);
        return repository.save(result);
    }

    public List<SimulationResult> getAllResults() {
        return repository.findAll();
    }

    public void deleteResult(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllResults() {
        repository.deleteAll();
    }

    private List<MonthlyData> generateDcaChartData(List<Price> prices, BigDecimal investmentAmount) {
        List<MonthlyData> chartData = new ArrayList<>();
        
        if (prices == null || prices.isEmpty()) {
            return chartData;
        }

        // Sort prices by date
        List<Price> validPrices = prices.stream()
                .filter(p -> p.getPrice() != null && p.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Price::getDate))
                .collect(Collectors.toList());

        if (validPrices.isEmpty()) {
            return chartData;
        }

        BigDecimal totalCryptoAccumulated = BigDecimal.ZERO;
        BigDecimal totalCashInvested = BigDecimal.ZERO;
        LocalDate startDate = validPrices.get(0).getDate();
        LocalDate nextBuyDate = startDate;

        // Track state for each month
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate lastDate = validPrices.get(validPrices.size() - 1).getDate();
        LocalDate lastMonth = lastDate.withDayOfMonth(1);
        int monthNumber = 1;
        BigDecimal lastMonthEndPrice = null;

        // Process all prices in order
        for (Price price : validPrices) {
            LocalDate currentDate = price.getDate();
            LocalDate priceMonth = currentDate.withDayOfMonth(1);

            // Process investments (same logic as DCA strategy)
            if (!currentDate.isBefore(nextBuyDate)) {
                BigDecimal cryptoBought = investmentAmount.divide(
                        price.getPrice(),
                        8,
                        RoundingMode.HALF_UP
                );
                totalCryptoAccumulated = totalCryptoAccumulated.add(cryptoBought);
                totalCashInvested = totalCashInvested.add(investmentAmount);
                nextBuyDate = nextBuyDate.plusDays(30);
            }

            // When we move to a new month, record the previous month's data
            if (!priceMonth.equals(currentMonth) && lastMonthEndPrice != null) {
                if (totalCryptoAccumulated.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal portfolioValue = totalCryptoAccumulated.multiply(lastMonthEndPrice);
                    chartData.add(new MonthlyData(
                            monthNumber,
                            totalCashInvested,
                            portfolioValue
                    ));
                    monthNumber++;
                }
                currentMonth = priceMonth;
            }

            // Update the last price seen for current month
            if (priceMonth.equals(currentMonth)) {
                lastMonthEndPrice = price.getPrice();
            }
        }

        // Add the last month's data
        if (lastMonthEndPrice != null && totalCryptoAccumulated.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal portfolioValue = totalCryptoAccumulated.multiply(lastMonthEndPrice);
            chartData.add(new MonthlyData(
                    monthNumber,
                    totalCashInvested,
                    portfolioValue
            ));
        }

        return chartData;
    }

    private List<MonthlyData> generateLumpSumChartData(List<Price> prices, BigDecimal amount) {
        List<MonthlyData> chartData = new ArrayList<>();
        
        if (prices == null || prices.isEmpty()) {
            return chartData;
        }

        // Sort prices by date
        List<Price> validPrices = prices.stream()
                .filter(p -> p.getPrice() != null && p.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Price::getDate))
                .collect(Collectors.toList());

        if (validPrices.isEmpty()) {
            return chartData;
        }

        BigDecimal initialPrice = validPrices.get(0).getPrice();
        BigDecimal cryptoAccumulated = amount.divide(initialPrice, 8, RoundingMode.HALF_UP);
        int monthNumber = 1;

        // Group prices by month and calculate monthly snapshots
        LocalDate startDate = validPrices.get(0).getDate();
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate lastMonth = validPrices.get(validPrices.size() - 1).getDate().withDayOfMonth(1);

        while (!currentMonth.isAfter(lastMonth)) {
            // Find the last price in this month
            BigDecimal monthEndPrice = null;
            for (Price price : validPrices) {
                LocalDate priceMonth = price.getDate().withDayOfMonth(1);
                if (priceMonth.equals(currentMonth)) {
                    monthEndPrice = price.getPrice();
                } else if (priceMonth.isAfter(currentMonth)) {
                    break;
                }
            }

            // Calculate portfolio value at end of month
            if (monthEndPrice != null) {
                BigDecimal portfolioValue = cryptoAccumulated.multiply(monthEndPrice);
                chartData.add(new MonthlyData(
                        monthNumber,
                        amount, // Invested amount stays constant for lump sum
                        portfolioValue
                ));
                monthNumber++;
            }

            currentMonth = currentMonth.plusMonths(1);
        }

        return chartData;
    }
}
