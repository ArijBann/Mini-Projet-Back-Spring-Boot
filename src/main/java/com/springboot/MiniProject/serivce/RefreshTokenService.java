package com.springboot.MiniProject.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.MiniProject.entity.RefreshToken;
import com.springboot.MiniProject.repository.RefreshTokenRepository;
import com.springboot.MiniProject.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String userEmail) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(userEmail).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))//10
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
    public void deleteRefreshToken (int userId ){
        refreshTokenRepository.deleteByUserId(userId);
    }
    public Optional<RefreshToken> getLoggedInUser(String refreshToken){
        return refreshTokenRepository.findByToken(refreshToken);
    }
}
