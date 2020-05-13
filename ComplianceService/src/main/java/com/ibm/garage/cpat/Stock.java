package com.ibm.garage.cpat;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Stock {
    private final UUID userId;
    private final String stockSymbol;
    private final String exchangeId;
    private final String tradeType;
    private final Date dateCreated;
    private final Date dateSubmitted;
    private final int quantity;
    private final Double stockPrice;
    private final Double totalCost;
    private final int institutionId;
    private final int countryId;
    private final boolean complianceServices;
    private final boolean technicalValidation;
    private final boolean schemaValidation;
    private final boolean businessValidation;
    private final boolean tradeEnrichment;

	public Stock(String stockSymbol, String exchangeId, String tradeType, int quantity, Double stockPrice,
			int institutionId, int countryId) {
        this.userId = UUID.randomUUID();
		this.stockSymbol = stockSymbol;
        this.exchangeId = exchangeId;
        this.tradeType = tradeType;
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        this.dateCreated = new Date();
        this.dateSubmitted = new Date();
		this.quantity = quantity;
		this.stockPrice = stockPrice;
        this.totalCost = stockPrice * quantity;
        this.institutionId = institutionId;
        this.countryId = countryId;
        this.complianceServices = false;
        this.technicalValidation = false;
        this.schemaValidation = false;
        this.businessValidation = false;
        this.tradeEnrichment = false;
    }

	public UUID getUserId() {
		return userId;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public int getQuantity() {
		return quantity;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public int getCountryId() {
		return countryId;
	}

	public boolean isComplianceServices() {
		return complianceServices;
	}

	public boolean isTechnicalValidation() {
		return technicalValidation;
	}

	public boolean isSchemaValidation() {
		return schemaValidation;
	}

	public boolean isBusinessValidation() {
		return businessValidation;
	}

	public boolean isTradeEnrichment() {
		return tradeEnrichment;
	}
    
}