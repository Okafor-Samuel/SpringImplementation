package com.example.sercuritydemo.Repository;

import com.example.sercuritydemo.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    @Query(value = "SELECT * FROM token WHERE users_id = ?1 AND (is_expired = 'false' or is_revoked = 'false')", nativeQuery = true)
    List<VerificationToken> findAllValidTokenByUser(Long id);
}
