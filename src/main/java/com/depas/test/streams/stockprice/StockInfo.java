package com.depas.test.streams.stockprice;

public class StockInfo {
  public final String ticker;
  public final double price;

  public StockInfo(final String symbol, final double thePrice) {
    ticker = symbol;
    price = thePrice;
  }

  public String toString() {
    return String.format("ticker: %s price: %g", ticker, new Double(price));
  }

  public static StockInfo unknownStockInfo(){
	  return new StockInfo("Unknown", -1);
  }
}
