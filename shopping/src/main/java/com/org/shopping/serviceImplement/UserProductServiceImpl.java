package com.org.shopping.serviceImplement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.BuyResponseDto;
import com.org.shopping.dto.UserProductDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;
import com.org.shopping.entity.User;
import com.org.shopping.entity.UserProduct;
import com.org.shopping.repository.ProductRepository;
import com.org.shopping.repository.UserProductRepository;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.service.UserProductService;

@Service
public class UserProductServiceImpl implements UserProductService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserProductRepository userProductRepository;

	/**
	 * @author Rakesh
	 * @param saving a purchased data into the database
	 * @return the Success message with corresponding status code
	 */
	@Override
	public BuyResponseDto buyProduct(long userId, long productId, int quantity) {
		UserProduct userProduct = new UserProduct();
		Optional<User> user = userRepository.findById(userId);
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent() && user.isPresent()) {
			userProduct.setPrice(product.get().getPrice() * quantity);
			userProduct.setPurchaseddate(LocalDate.now().toString());
			userProduct.setProduct(product.get());
			userProduct.setQuantity(quantity);
			userProduct.setUser(user.get());
			userProductRepository.save(userProduct);
			Product products = new Product();
			products.setDescription(product.get().getDescription());
			products.setId(product.get().getId());
			products.setPrice(product.get().getPrice());
			products.setRating((product.get().getRating()));
			products.setPriority(product.get().getPriority());
			products.setQuantity(product.get().getQuantity() - quantity);
			products.setProductCode(product.get().getProductCode());
			products.setProductName(product.get().getProductName());
			productRepository.save(products);
			return new BuyResponseDto(userProduct.getUserProductId(), AppConstant.SUCCESSSFULLY_BUY +
					"Receipt No: "+ userProduct.getUserProductId() +" "+ "Total Price: " +userProduct.getPrice(),
					AppConstant.USER_PURCHASED);
		} else {
			return new BuyResponseDto(0L, AppConstant.SUCCESSSFULLY_NOT_BUY, AppConstant.NOT_PURCHASED);
		}
	}

	/**
	 * @author Rakesh
	 * @param retriving product details from database based on user purchased
	 * @return list of product details
	 */
	@Override
	public List<UserProductDto> getUserProducts(Long userid) {
		Optional<User> findById = userRepository.findById(userid);
		List<UserProductDto> userProductList = new ArrayList<>();
		if (findById.isPresent()) {
			List<UserProduct> userProduct = findById.get().getUserProduct();

			userProduct.forEach(user -> {
				UserProductDto userProductDto = new UserProductDto();
				userProductDto.setPrice(user.getPrice());
				userProductDto.setProductName(user.getProduct().getProductName());
				userProductDto.setProductId(user.getProduct().getId());
				userProductDto.setPurchaseDate(user.getPurchaseddate());
				userProductDto.setQuantity(user.getQuantity());
				userProductDto.setReceiptId(user.getUserProductId());
				userProductList.add(userProductDto);
			});
		}
		return userProductList;
	}

	@Override
	public UserResponse ratingProduct(Long id, double rating) { 
		Optional<UserProduct> userProduct = userProductRepository.findById(id);
		Optional<Product> product = productRepository.findById(userProduct.get().getProduct().getId());
		if(userProduct.isPresent() && product.isPresent()) {
			UserProduct userProducts = new UserProduct();
			userProducts.setUserProductId(id);
			userProducts.setUser(userProduct.get().getUser());
			userProducts.setProduct(userProduct.get().getProduct());
			userProducts.setRating(rating);
			userProducts.setPrice(userProduct.get().getPrice());
			userProducts.setQuantity(userProduct.get().getQuantity());
			userProducts.setPurchaseddate(userProduct.get().getPurchaseddate());
			userProductRepository.save(userProducts);
			Product products = new Product();
			products.setDescription(product.get().getDescription());
			products.setId(product.get().getId());
			products.setPrice(product.get().getPrice());
			products.setRating(calculateRating(product.get().getId()));
			products.setPriority(product.get().getPriority());
			products.setQuantity(product.get().getQuantity());
			products.setProductCode(product.get().getProductCode());
			products.setProductName(product.get().getProductName());
			productRepository.save(products);
			return new UserResponse("Thanks for the rating!", AppConstant.USER_REVIEW);
		} else {
			return new UserResponse("No PurchaseId Found with: "+id, AppConstant.USER_REVIEW);
		}				
	} 
		
	public double calculateRating(Long productId) {
		List<UserProduct> ratinglist = userProductRepository.findAll();
		List<UserProduct> pid = ratinglist.stream()
                                          .filter(product -> product.getProduct().getId().equals(productId)).collect(Collectors.toList());
		double average = pid.stream().mapToDouble(rate -> rate.getRating()).average().orElse(0.0);
		average = Math.round(average * 10) / 10.0;
		return average;		
	}
}
		