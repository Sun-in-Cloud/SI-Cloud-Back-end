package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderListDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.dto.OrderProductListDTO;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.OrderProduct;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.OrderProductRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ProductService productService;
	private final SellerService sellerService;

	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	/**
	 * 발주가 필요한 상품의 목록을 조회하는 메서드
	 * 1. ProductService에서 발주가 필요한 상품을 조회하고
	 * 2. DTO로 Convert
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return OrderProductListDTO
	 * 작성자: 손준범
	 */
	public OrderProductListDTO findNeededOrderProducts(Long sellerNo, int pageNum, int countPerPage) {
		Page<Product> neededToOrderProducts = productService.findNeededToOrderBySellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
		List<OrderProductDTO> products = new ArrayList<>();
		for (Product product : neededToOrderProducts) {
			products.add(OrderProductDTO.builder()
					.productNo(product.getProductNo())
					.productName(product.getProductName())
					.safetyStock(product.getSafetyStock())
					.currentStock(product.getCurrentStock())
					.enoughStock(product.getEnoughStock())
					.amount(product.getEnoughStock() - product.getCurrentStock())
					.build());
		}
		Long neededToOrderCount = productService.countNeededToOrder(sellerNo);
		Long totalPage = calculatePageCount(neededToOrderCount, countPerPage);
		return OrderProductListDTO.builder().totalPage(totalPage).products(products).build();
	}

	/**
	 * 발주 등록 메서드
	 * 1. Seller를 찾고
	 * 2. Seller를 포함하여 발주를 저장하고
	 * 3. 발주가 필요한 상품들 조회하고
	 * 4. 저장된 발주를 기반으로 발주 상품들 저장하기
	 * @param sellerNo
	 * @return 저장이 필요한 물품의 개수와 저장된 물품의 개수가 동일하다면 true, 아니면 false
	 */
	@Transactional
	public boolean register(Long sellerNo) {
		Seller seller = sellerService.findById(sellerNo);
		Order order = orderRepository.save(Order.builder().seller(seller).build());
		if (order.getOrderNo() == null) {
			return false;
		}
		List<Product> neededToOrderProducts = productService.findNeededToOrderBySellerNo(sellerNo);
		if (neededToOrderProducts.size() == 0) {
			return false;
		}
		List<OrderProduct> orderProducts = new ArrayList<>();
		for (Product neededToOrderProduct : neededToOrderProducts) {
			orderProducts.add(OrderProduct.builder()
					.order(order)
					.product(neededToOrderProduct)
					.amount(neededToOrderProduct.getEnoughStock() - neededToOrderProduct.getCurrentStock())
					.build());
		}
		List<OrderProduct> savedOrderProducts = orderProductRepository.saveAll(orderProducts);
		return savedOrderProducts.size() == orderProducts.size() ? true : false;
	}

	/**
	 * 발주 내역 조회
	 * 1. sellerNo를 기반으로 입력으로 주어진 page에 해당하는 발주 내역 조회
	 * 2. DTO로 변경후 리스트 반환
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return 주문내역 리스트
	 * 작성자: 손준범
	 */
	public OrderListDTO findOrders(Long sellerNo, int pageNum, int countPerPage) {
		Page<Order> orders = orderRepository.findBySeller_SellerNoOrderByOrderDateDesc(sellerNo, PageRequest.of(pageNum, countPerPage));
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for (Order order : orders) {
			orderDTOs.add(OrderDTO.builder()
					.orderDate(TimestampUtil.convertTimestampToString(order.getOrderDate()))
					.orderNo(order.getOrderNo())
					.importNo(order.getImports() == null ? null : order.getImports().getImportNo())
					.isImported(order.getImports() == null ? false : order.getImports().getImportDate() == null ? false : true)
					.build());
		}
		Long count = orderRepository.countBySeller_SellerNo(sellerNo);
		Long totalPage = calculatePageCount(count, countPerPage);
		return OrderListDTO.builder().totalPage(totalPage).orders(orderDTOs).build();
	}

	/**
	 * 발주 상세조회
	 * 1. 발주 번호로 발주 상품 조회
	 * 2. DTO로 Convert 후 return
	 * @param orderNo
	 * @return 해당 발주에 포함된 모든 상품 리스트
	 * 작성자: 손준범
	 */
	public List<OrderProductDTO> findByOrderNo(Long orderNo) {
		List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder_OrderNo(orderNo);
		List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
		for (OrderProduct orderProduct : orderProducts) {
			orderProductDTOs.add(OrderProductDTO.builder()
					.productNo(orderProduct.getProduct().getProductNo())
					.productName(orderProduct.getProduct().getProductName())
					.amount(orderProduct.getAmount())
					.build());
		}
		return orderProductDTOs;
	}
	
	private Long calculatePageCount(Long count, int countPerPage) {
		Long totalPage = count / countPerPage;
		if (count % countPerPage > 0) {
			++totalPage;
		}
		return totalPage;
	}
	
	//orderNo 발주번호를 가지고 있는 주문에 importNo입고번호를 저장함
	public void saveImportNoForSeller(Long orderNo, Imports imports) {
		Optional<Order> orders = orderRepository.findById(orderNo);
		
			Order order = orders.get();
			order.setImports(imports);
			orderRepository.save(order);
		
	}

	/**
	 * 입고 처리되지 않은 발주만 조회
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * return 입고처리 되지 않은 발주 list
	 */
	public OrderListDTO findNotImportedOrdersBySeller(Long sellerNo, int pageNum, int countPerPage) {
		Page<Order> orders = orderRepository.findBySeller_SellerNoAndImportsIsNull(sellerNo, PageRequest.of(pageNum, countPerPage));
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for (Order order : orders) {
			orderDTOs.add(OrderDTO.builder()
					.orderNo(order.getOrderNo())
					.orderDate(TimestampUtil.convertTimestampToString(order.getOrderDate()))
					.build());
		}
		Long count = orderRepository.countBySeller_SellerNoAndImportsIsNull(sellerNo);
		Long totalPage = calculatePageCount(count, countPerPage);
		return OrderListDTO.builder().totalPage(totalPage).orders(orderDTOs).build();
	}
}
