package com.shinhan.sunInCloud.detailProductGroup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.service.DetailProductGroupService;
import com.shinhan.sunInCloud.service.ProductGroupService;

@SpringBootTest
public class DetailProductGroupTest {
	@Autowired
	private DetailProductGroupService detailProductGroupService;
	@Autowired
	private ProductGroupService productGroupService;
	
	@Test
	void registerDetailProductGroup() {
		ProductGroup productGroup = productGroupService.findByGroupName("화장품");
		String[] detailGroupNames = {"메이크업", "네일", "클렌징", "스킨", "크림"};
		for (String detailGroupName : detailGroupNames) {
			DetailProductGroup detailProductGroup = DetailProductGroup
					.builder()
					.groupName(detailGroupName)
					.productGroup(productGroup)
					.build();

			DetailProductGroup savedDetailProductGroup = detailProductGroupService.register(detailProductGroup);
			Assertions.assertThat(savedDetailProductGroup.getDetailProductGroupNo()).isNotNull();
			Assertions.assertThat(savedDetailProductGroup.getGroupName()).isEqualTo(detailProductGroup.getGroupName());
		}
	}
	
}
