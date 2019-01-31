package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class Tax {

	private UUID taxId;
	private int taxRate;
	private BigDecimal taxTotal;
	
	public Tax(UUID taxId) {
		super();
		this.taxId = taxId;
	}

	public Tax(int percentage, BigDecimal taxTotal) {
		super();
		this.taxId = UUID.randomUUID();
		this.taxRate = percentage;
		this.taxTotal = taxTotal;
	}
	
	public Tax(UUID taxId, int percentage, BigDecimal taxTotal) {
		super();
		this.taxId = taxId;
		this.taxRate = percentage;
		this.taxTotal = taxTotal;
	}
	
	public UUID getTaxId() {
		return taxId;
	}

	public void setTaxId(UUID taxId) {
		this.taxId = taxId;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}
	
	public BigDecimal getTaxTotal() {
		return taxTotal;
	}
	
	public void setTaxTotal(BigDecimal taxTotal) {
		this.taxTotal = taxTotal;
	}
		
}
