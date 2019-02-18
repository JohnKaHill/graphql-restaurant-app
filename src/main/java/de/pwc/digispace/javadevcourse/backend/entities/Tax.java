package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class Tax {

	private UUID taxId;
	private int taxRate;
	private BigDecimal taxTotal;

	public Tax(UUID taxId, int taxRate, BigDecimal taxTotal) {
		this.taxId = taxId;
		this.taxRate = taxRate;
		this.taxTotal = taxTotal;
	}

	public UUID getTaxId() {
		return taxId;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public BigDecimal getTaxTotal() {
		return taxTotal;
	}
		
}
