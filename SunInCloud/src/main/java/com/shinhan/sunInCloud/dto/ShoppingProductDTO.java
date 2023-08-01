package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingProductDTO {
	private String exportNo;

	private String address;

	private String ordererName;

	private String productName;

	private String productNo;

	private int amount;

	private int sellingPrice;

	private Timestamp orderDate;

	private String orderStatus;

	private String invoiceNo;

}
