package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.entity.ShoppingProduct;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ExportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportsService {
	private final ExportsRepository exportsRepository;
	private final ExportProductRepository exportProductRepository;
	private final ShoppingService shoppingService;
	private final SellerService sellerService;

	/**
	 * 주문건 수집한 후 주문 목록 리턴하는 메서드
	 * 
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	@Transactional
	public List<ExportsDTO> register(Long sellerNo, int pageNum, int countPerPage) {
		List<Exports> exports = new ArrayList<>();
		List<Shopping> shoppings = shoppingService.findNotCollected(sellerNo);
		Seller seller = sellerService.findById(sellerNo);

		// 수집된 주문이 있는 경우에만 출고 목록 만듦
		if (shoppings.size() > 0) {
			// 출고 목록 만들기
			for (Shopping shopping : shoppings) {
				exports.add(shopping.toExports(seller));
			}

			// 출고 상품 만들기
			for (Exports exp : exports) {
				List<ExportProduct> exportProducts = new ArrayList<>();
				Exports savedExports = exportsRepository.save(exp);
				List<ShoppingProduct> shoppingProducts = shoppingService
						.findShoppingProduct(savedExports.getExportNo());

				for (ShoppingProduct shoppingProduct : shoppingProducts) {
					exportProducts.add(shoppingProduct.toExportProduct(savedExports));
				}

				List<ExportProduct> savedExportProducts = exportProductRepository.saveAll(exportProducts);
				
				if(savedExports == null || savedExportProducts == null) findExports(sellerNo, 0, countPerPage);
			}
		}

		// 주문 수집하면 무조건 첫 페이지로 이동
		return findExports(sellerNo, 0, countPerPage);
	}

	/**
	 * 출고 목록 조회 메서드
	 * 
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public List<ExportsDTO> findExports(Long sellerNo, int pageNum, int countPerPage) {
		List<ExportsDTO> exportsDTOs = new ArrayList<>();
		Page<Exports> exports = exportsRepository.findAllBySeller_SellerNoOrderByOrderDateDesc(sellerNo,
				PageRequest.of(pageNum, countPerPage));

		for (Exports exp : exports) {
			exportsDTOs.add(exp.toExportsDTO(exportProductRepository));
		}
		return exportsDTOs;
	}

	/**
	 * 출고 목록 상세 메서드
	 * 
	 * @param exportNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public List<ExportProductDTO> exportDetail(String exportNo, int pageNum, int countPerPage) {
		List<ExportProductDTO> exportProductDTOs = new ArrayList<>();
		Page<ExportProduct> exportProducts = exportProductRepository
				.findByExports_ExportNoOrderByProduct_ProductName(exportNo, PageRequest.of(pageNum, countPerPage));

		for (ExportProduct exportProduct : exportProducts) {
			exportProductDTOs.add(exportProduct.toExportProductDTO());
		}

		return exportProductDTOs;
	}

}
