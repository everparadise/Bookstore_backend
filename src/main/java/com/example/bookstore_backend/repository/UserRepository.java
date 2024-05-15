package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> getUserByUid(Long uid);
    @Query("SELECT new com.example.bookstore_backend.dto.UserDto(user.uid, user.username, user.remainMoney, user.slogan, user.avatar)  FROM User user WHERE user.password = :password AND user.username = :username")
    public Optional<UserDto> userInfoValidation(String username, String password);
    public Optional<User> getUserByUsername(String username);

    @Modifying
    @Query("UPDATE User user SET user.remainMoney = :remainMoney, " +
            "user.avatar = :avatar, user.username = :username, " +
            "user.slogan = :slogan WHERE user.uid = :uid")
    public Integer PrivateSave(Long uid, String slogan, String username, String avatar,Double remainMoney);

    @Modifying
    @Query("UPDATE User user SET user.remainMoney = user.remainMoney - :spend WHERE user.uid = :uid AND user.remainMoney > :spend")
    public Integer decreaseMoney(Long uid, double spend);
}
