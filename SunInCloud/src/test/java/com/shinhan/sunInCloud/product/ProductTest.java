package com.shinhan.sunInCloud.product;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ProductDTO;
import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.service.DetailProductGroupService;
import com.shinhan.sunInCloud.service.ProductService;
import com.shinhan.sunInCloud.service.SellerService;

@SpringBootTest
public class ProductTest {
	@Autowired
	private ProductService productService;
	@Autowired
	private DetailProductGroupService detailProductGroupService;
	@Autowired
	private SellerService sellerService;

	@Test
	void register() {
		ArrayList<Product> products = new ArrayList<>();
		Seller etudehome = sellerService.findByBusinessNo("135-81-05033");
		Seller outsfree = sellerService.findByBusinessNo("106-86-68127");

		DetailProductGroup makeup = detailProductGroupService.findByGroupName("메이크업");
		DetailProductGroup nail = detailProductGroupService.findByGroupName("네일");
		DetailProductGroup cleansing = detailProductGroupService.findByGroupName("클렌징");
		DetailProductGroup skin = detailProductGroupService.findByGroupName("스킨");
		DetailProductGroup cream = detailProductGroupService.findByGroupName("크림");
		String[] productNo = { "8806382685093", "8806382650527", "8806338760904", "8806165973164", "8806382609914",
				"8806179490671", "8806199416347", "8806165967330", "8806199479342", "8809587374717", "8806173592876",
				"8806173571437", "8806146938816", "8806173571116", "8809516803042", "8809516792124", "8806173546565",
				"8806173577088", "8806173584710", "8806173560080", "8806173530748" };
		String[] productName = { "디어달링틴트 리얼레드", "쁘띠달링네일즈 딸기시럽", "립앤아이리무버", "미니애니쿠션", "반짝눈물라이너 태양빛눈물", "룩엣마이아이즈 사랑은모래성",
				"생크림블러셔 자몽딸기", "룩엣마이아이즈 샤이닝베이지", "캐시미어핏아이즈 롱블랙", "룩엣마이아이즈 피칸", "그린씨드크림", "미니멈 토너", "미네랄블러셔 복숭아",
				"비자 트러블 스킨", "올리브 리얼 미스트", "용암수 앰플", "탄산 미네랄 미스트", "탄산 톤업 크림", "한란 크림", "화산송이 마스크", "화산송이 클레이 마스크" };
		Integer[] safetyStock = { 182, 137, 168, 118, 106, 194, 196, 118, 175, 126, 196, 184, 160, 144, 163, 167, 161,
				107, 150, 143, 163 };
		DetailProductGroup[] group = { makeup, nail, cleansing, makeup, makeup, makeup, makeup, makeup, makeup, makeup,
				cream, skin, makeup, skin, skin, cream, skin, cream, cream, cleansing, cleansing };
		Integer[] consumerPrice = { 4260, 4000, 11000, 15000, 8000, 4000, 8500, 4000, 5500, 4000, 27000, 7000, 7000,
				18000, 25000, 23000, 12000, 25000, 27000, 18000, 18000 };
		Integer[] importPrice = { 1278, 1200, 3300, 4500, 2400, 1200, 2550, 1200, 1650, 1200, 13500, 3500, 3500, 9000,
				12500, 11500, 6000, 12500, 13500, 9000, 9000 };

		for (int i = 0; i < productNo.length; i++) {
			Product product = Product.builder().consumerPrice(consumerPrice[i]).currentStock(0)
					.detailProductGroup(group[i]).importPrice(importPrice[i]).isActive(true).productName(productName[i])
					.productNo(productNo[i]).safetyStock(safetyStock[i])
					.enoughStock((int)(safetyStock[i] * 1.5))
					.seller(i < 10 ? etudehome : outsfree).build();

			products.add(product);
		}

		List<Product> savedProducts = productService.registerAll(products);

		Assertions.assertThat(savedProducts.size()).isEqualTo(products.size());
	}
	
	@Test
	void findAllinPage() {
		int pageSize = 5;
		int pageNumber = 1;
		Seller seller = sellerService.findByBusinessNo("135-81-05033");
		List<ProductDTO> products = productService.findProductBySellerNo(seller.getSellerNo(), pageNumber, pageSize);
		for (ProductDTO product : products) {
			System.out.println(product.getProductName());
		}
		Assertions.assertThat(products.size()).isEqualTo(5);
	}
}
