package com.depas.test.streams.stockprice;

import java.util.List;
import java.util.function.Function;

public class StockPrice {
  public static void getStockGreaterThan(List<String> symbols, int stockPrice) {
	// this is not functional
    List<StockInfo> stocks = StockFetcher.fetchStockPrices(symbols);

    System.out.println(
      stocks.stream()
            .filter(stock -> stock.price > stockPrice)
            .findFirst());
  }

  public static StockInfo getStockGreaterThanFunctional(List<String> symbols,final int stockPrice) {
	  // this is functional style
      return StockFetcher.fetchStockPricesLazy(symbols)
                  .filter(stock -> stock.price > stockPrice)
                  .findFirst()
                  .map(Function.identity())
                  .orElse(StockInfo.unknownStockInfo());
  }

  public static void main(String[] args) {
    System.out.println(Tickers.symbols);

    System.out.println("Eager evaluation, not efficient");
    Timeit.code(() -> getStockGreaterThan(Tickers.symbols,200));

    System.out.println("Lazy evaluation, more efficient");
    Timeit.code(() -> System.out.println(getStockGreaterThanFunctional(Tickers.symbols,200)));
  }
}