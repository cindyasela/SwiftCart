package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.CartItemsDTO;
import com.tek.swiftcarts.dto.CartsDTO;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartsServiceImpl {
    private final UsersRepository usersRepository;
    private final StatusRepository statusRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository itemsRepository;
    private final ProductRepository productRepository;



    public CartsDTO createNewCart(CartsDTO dto){

        Carts carts=new Carts();

        carts.setName(dto.getName());

     Users users=usersRepository.findById(dto.getCreatedBy().getId()).orElseThrow(() ->
             new AppException(
             ErrorCodes.RESOURCE_NOT_FOUND,"Users Not Found"));

     carts.setCreatedBy(users);

     carts.setCreatedAt(LocalDateTime.now());

      Optional<Status>activeStatus=statusRepository.findByName("ACTIVE");

        if (activeStatus.isEmpty()){throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Status is Empty");
        }

        carts.setStatus(activeStatus.get());

        carts.setId(dto.getId());

        carts.setUpdatedAt(LocalDateTime.now());


        if (dto.getCartItems() != null && !dto.getCartItems().isEmpty()) {

            List<CartItems> cartItems = dto.getCartItems()
                    .stream()
                    .map(itemDTO -> {

                        CartItems cartItem = new CartItems();
                        cartItem.setProductName(
                                itemDTO.getProductName());
                        cartItem.setProductSku(
                                itemDTO.getProductSku());

                        cartItem.setQuantity(
                                itemDTO.getQuantity());

                        cartItem.setUnitPrice(
                                itemDTO.getUnitPrice());

                        BigDecimal itemTotal =
                                calculateItemTotal(
                                        itemDTO.getUnitPrice(),
                                        itemDTO.getQuantity(),
                                        itemDTO.getDiscountAmount(),
                                        itemDTO.getTaxAmount()
                                );


                        cartItem.setTotalPrice(
                                itemTotal
                        );

                        cartItem.setDiscountAmount(
                                itemDTO.getDiscountAmount());

                        cartItem.setTaxAmount(
                                itemDTO.getTaxAmount());

                        cartItem.setStatus(
                                CartItemStatus.ACTIVE
                        );

                        cartItem.setCart(carts);

                        return cartItem;
                    })
                    .toList();


            carts.setCartItems(cartItems);

            Carts savedCart = cartRepository.save(carts);

            return mapToDTO(savedCart);

        }

        return dto;
    }


    private BigDecimal calculateItemTotal(BigDecimal unitPrice, int quantity, BigDecimal discountAmount, BigDecimal taxAmount) {

        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity)
                );


        if(discountAmount == null){

            discountAmount = BigDecimal.ZERO;
        }

        if(taxAmount == null){

            taxAmount = BigDecimal.ZERO;

        }

        return subtotal.subtract(discountAmount).add(taxAmount);

    }



    public CartsDTO addItemCart(String cartId , CartItemsDTO dto) {

        Carts carts = cartRepository.findById(cartId).
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "CART Not Available"));

        carts.setUpdatedAt(LocalDateTime.now());

        Users users = usersRepository.findById(dto.getUpdatedBy().getId()).
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "User Not Found"));

        carts.setUpdatedBy(users);

        carts.setName(dto.getProductName());

        Status updatedStatus = statusRepository.findByName("UPDATED").
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "User Not Found"));

        carts.setStatus(updatedStatus);

        Products products=productRepository.findById(dto.getProduct().getId()).orElseThrow(() -> new AppException
                (ErrorCodes.RESOURCE_NOT_FOUND,"Product Not found"));


        Optional<CartItems>existingItems=itemsRepository.findByCartIdAndProductId(cartId,products.getId());

        if (existingItems.isPresent()){

             CartItems items=existingItems.get();

             items.setQuantity(items.getQuantity()+ dto.getQuantity());

            items.setTotalPrice(
                    items.getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf(items.getQuantity()))
            );

        } else {

            CartItems cartItem = new CartItems();

            cartItem.setProductName(dto.getProductName());

            cartItem.setProductSku(dto.getProductSku());

            cartItem.setQuantity(dto.getQuantity());

            cartItem.setUnitPrice(dto.getUnitPrice());

            BigDecimal itemTotal =
                    calculateItemTotal(
                            dto.getUnitPrice(),
                            dto.getQuantity(),
                            dto.getDiscountAmount(),
                            dto.getTaxAmount()
                    );


            cartItem.setTotalPrice(itemTotal
            );

            cartItem.setDiscountAmount(dto.getDiscountAmount());

            cartItem.setTaxAmount(dto.getTaxAmount());

            cartItem.setStatus(CartItemStatus.ACTIVE
            );

            carts.getCartItems().add(cartItem);

        }

        Carts savedCart = cartRepository.save(carts);

        return mapToDTO(savedCart);
    }



    private CartsDTO mapToDTO(Carts savedCart) {

        CartsDTO dto = new CartsDTO();

        dto.setId(savedCart.getId());

        dto.setName(savedCart.getName());

        dto.setCreatedAt(savedCart.getCreatedAt());

        dto.setUpdatedAt(savedCart.getUpdatedAt());

        return dto;


    }

    public CartsDTO removeItemCart(String cartId,CartItemsDTO dto) {


        Carts carts = cartRepository.findById(cartId).
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "CART Not Available"));

        carts.setUpdatedAt(LocalDateTime.now());

        Users users = usersRepository.findById(dto.getUpdatedBy().getId()).
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "User Not Found"));

        carts.setUpdatedBy(users);

        carts.setName(dto.getProductName());

            Status removedStatus = statusRepository.findByName("REMOVED").
                orElseThrow(() -> new AppException
                        (ErrorCodes.RESOURCE_NOT_FOUND, "User Not Found"));

        carts.setStatus(removedStatus);

        Products products = productRepository.findById(dto.getProduct().getId()).orElseThrow(() -> new AppException
                (ErrorCodes.RESOURCE_NOT_FOUND, "Product Not found"));


        Optional<CartItems> existingItem = itemsRepository.findByCartIdAndProductId(cartId, dto.getProduct().getId());


        if (existingItem.isPresent()) {

            CartItems items = existingItem.get();

            items.setQuantity(items.getQuantity() - dto.getQuantity());

            items.setTotalPrice(
                    items.getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf(items.getQuantity()))
            );

        }

        Carts savedCart = cartRepository.save(carts);

        return mapToDTO(savedCart);

    }



}


