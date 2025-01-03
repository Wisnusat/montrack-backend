package com.example.montrack.Controller;

import com.example.montrack.DTO.ApiResponse;
import com.example.montrack.Models.Wishlist;
import com.example.montrack.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<ApiResponse<Wishlist>> addWishlist(@RequestBody Wishlist wishlist) {
        ApiResponse<Wishlist> response = wishlistService.addWishlist(wishlist);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/status/{wishlistId}")
    public ResponseEntity<ApiResponse<Boolean>> dateReached(@PathVariable Long wishlistId) {
        ApiResponse<Boolean> response = wishlistService.dateReached(wishlistId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<ApiResponse<Void>> deleteWishlist(@PathVariable Long wishlistId) {
        ApiResponse<Void> response = wishlistService.deleteWishlist(wishlistId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
}
