package com.example.montrack.Service;

import com.example.montrack.DTO.ApiResponse;
import com.example.montrack.Models.Wishlist;
import com.example.montrack.Repositories.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    public ApiResponse<Wishlist> addWishlist(Wishlist wishlist) {
        if (wishlist == null || wishlist.getUser().getUserId() == 0 || wishlist.getName() == null) {
            return new ApiResponse<>(false, "Wishlist data is invalid", null);
        }
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return new ApiResponse<>(true, "Wishlist added successfully", savedWishlist);
    }

    public ApiResponse<Boolean> dateReached(Long wishlistId) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(wishlistId);
        if (wishlist.isEmpty()) {
            return new ApiResponse<>(false, "Wishlist not found", null);
        }
        boolean reached = wishlist.get().getSaving() >= wishlist.get().getBudget();
        return new ApiResponse<>(true, "Date reached status retrieved", reached);
    }

    public ApiResponse<Void> deleteWishlist(Long wishlistId) {
        if (!wishlistRepository.existsById(wishlistId)) {
            return new ApiResponse<>(false, "Wishlist not found", null);
        }
        wishlistRepository.deleteById(wishlistId);
        return new ApiResponse<>(true, "Wishlist deleted successfully", null);
    }
}
