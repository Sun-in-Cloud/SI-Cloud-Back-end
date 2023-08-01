package com.shinhan.sunInCloud.productGroup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.service.ProductGroupService;

@SpringBootTest
public class ProductGroupTest {

	@Autowired
	ProductGroupService productGroupService;
	
	@Test
	void registerProductGroup() {
		ProductGroup productGroup = ProductGroup.builder()
				.groupName("화장품")
				.build();
		ProductGroup savedProductGroup = productGroupService.register(productGroup);
		
		Assertions.assertThat(savedProductGroup.getProductGroupNo()).isNotNull();
		Assertions.assertThat(savedProductGroup.getGroupName()).isEqualTo(productGroup.getGroupName());
	}
	
	@Test
	void findProductGroupByName() {
		ProductGroup productGroup = productGroupService.findByGroupName("화장품");
		Assertions.assertThat(productGroup.getProductGroupNo()).isEqualTo(1L);
		Assertions.assertThat(productGroup.getGroupName()).isEqualTo("화장품");
	}
	
	@Test
	void cannotFindUnregisteredProductByName() {
		ProductGroup productGroup = productGroupService.findByGroupName("마우스");
		Assertions.assertThat(productGroup).isNull();
	}
}
