package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.shinhan.sunInCloud.dto.ImportProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ImportProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long importProductNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="import_no")
	private Imports imports;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="product_no")
	private Product product;
	
	@NotNull
	private Integer requestAmount;
	
	// ó�� default�� 0�� �� ������ null�� �� ������?
	// ���� �⺻������ 0�� ���� ������ ��������
	@ColumnDefault("0")
	private Integer importAmount;
	
	
	public ImportProductDTO toImportProductDTO() {
		return ImportProductDTO.builder()
				.importAmount(importAmount)
				.importNo(imports.getImportNo())
				.importProductNo(importProductNo)
				.productNo(product.getProductNo())
				.requestAmount(requestAmount)
				.build();
		
	}
}









