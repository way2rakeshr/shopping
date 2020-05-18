package com.org.shopping.serviceImplement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.ProductDto;
import com.org.shopping.dto.ProductListDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;
import com.org.shopping.entity.User;
import com.org.shopping.exception.ProductException;
import com.org.shopping.exception.UserException;
import com.org.shopping.repository.ProductRepository;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	/**
	 * @author Hasina
	 * @param retriving product details from database based on user type
	 * @return list of product details
	 */
	public List<ProductListDto> findAllProducts(Long userId) {
		List<Product> findAll = productRepository.findAll();
		List<ProductListDto> list=new ArrayList<ProductListDto>();
		List<ProductListDto> collect = findAll.stream().filter(product->product.getPrice()>10).map(pro->{  
			ProductListDto prod=new ProductListDto();
			BeanUtils.copyProperties(pro, prod);
			list.add(prod);
			return prod;
			
		}).collect(Collectors.toList());
		return collect;

		
	
//		productList.forEach(product -> {
//			ProductListDto productDto = new ProductListDto();
//			BeanUtils.copyProperties(product, productDto);
//			productdtoList.add(productDto);
//		});

//		Optional<User> findByUserId = userRepository.findById(userId);
//
//		if (findByUserId.isPresent()) {
//
//			List<Product> priorityList = null;
//			if (!findByUserId.get().getUserType().equals(AppConstant.PRIME_USER)) {
//				priorityList = productList.stream().filter(product -> product.getPriority().equals(AppConstant.HIGH))
//						.collect(Collectors.toList());
//			} else {
//				priorityList = productList;
//			}
//
//			priorityList.forEach(product -> {
//				ProductListDto productDto = new ProductListDto();
//				BeanUtils.copyProperties(product, productDto);
//				productdtoList.add(productDto);
//			});
//		} else {
//			throw new UserException(AppConstant.USER_NOT_EXISTS);
//		}
//		return productdtoList;
	}

	/**
	 * @author rakesh
	 * @param set product review into the database based on customer purchased
	 * @return the Success message with corresponding status code
	 */

	@Override
	public UserResponse ratingProduct(Long productId, int rating) {
		Optional<Product> product = productRepository.findById(productId);
		Product products = new Product();
		if (product.isPresent()) {
			products.setDescription(product.get().getDescription());
			products.setId(product.get().getId());
			products.setPrice(product.get().getPrice());
			products.setRating((product.get().getRating() + rating) / 2);
			products.setPriority(product.get().getPriority());
			products.setQuantity(product.get().getQuantity());
			products.setProductCode(product.get().getProductCode());
			products.setProductName(product.get().getProductName());
			productRepository.save(products);
			return new UserResponse(AppConstant.REVIEW, AppConstant.USER_REVIEW);
		} else {
			return new UserResponse(AppConstant.NOT_REVIEW, AppConstant.USER_REVIEW);
		}
	}

	@Override
	public List<ProductListDto> searchProduct(String productName, Long userId) {
		List<ProductListDto> productdtoList = new ArrayList<>();

		List<Product> productList = productRepository.findAll();

		Optional<User> findByUserId = userRepository.findById(userId);

		if (productName == null || productName.isEmpty()) {
			throw new ProductException(AppConstant.PRODUCT_NAME);
		}
		if (findByUserId.isPresent()) {
			List<Product> priorityList = null;
			if (!findByUserId.get().getUserType().equals(AppConstant.PRIME_USER)) {
				priorityList = productList.stream().filter(product -> product.getPriority().equals(AppConstant.HIGH))
						.filter(product -> product.getProductName().contains(productName)).collect(Collectors.toList());
			} else {
				priorityList = productList.stream().filter(product -> product.getProductName().contains(productName))
						.collect(Collectors.toList());
			}

			priorityList.sort(Comparator.comparing(Product::getRating).reversed());

			if (priorityList.isEmpty()) {
				throw new ProductException(AppConstant.PRODUCT_NOT_FOUND);
			}
			priorityList.forEach(product -> {
				ProductListDto productDto = new ProductListDto();
				BeanUtils.copyProperties(product, productDto);
				productdtoList.add(productDto);
			});
		} else {
			throw new UserException(AppConstant.USER_NOT_EXISTS);
		}
		return productdtoList;
	}

	@Override
	public UserResponse addProduct(ProductDto productDto) {

		if (productDto.getProductCode().isEmpty() || productDto.getProductName().isEmpty()
				|| productDto.getDescription().isEmpty() || productDto.getPriority().isEmpty()
				|| productDto.getPrice() == 0.0 || productDto.getQuantity() == 0) {
			throw new UserException(AppConstant.DETAILS_REQUIRED);
		}

		Optional<Product> productName = productRepository.findByProductName(productDto.getProductName());

		if (productName.isPresent()) {
			throw new ProductException(AppConstant.PRODUCT_EXISTS);
		}
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		productRepository.save(product);
		return new UserResponse(productDto.getProductName() + " " + AppConstant.PRODUCT_ADDED,
				AppConstant.PRODUCT_SAVED);
	}
}
