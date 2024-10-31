package com.example.main.repository;

import com.example.main.Constants.Role;
import com.example.main.dao.AuthDao;
import com.example.main.dto.UserStateDto;
import com.example.main.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> getUserByUid(Long uid);
//    @Query("SELECT new com.example.bookstore_backend.dto.UserDto(user.uid, user.username, user.remainMoney, user.slogan, user.avatar)  FROM User user WHERE user.password = :password AND user.username = :username")
//    public Optional<User> getUserByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.remainMoney = :remainMoney, " +
            "user.avatar = :avatar, user.username = :username, " +
            "user.slogan = :slogan WHERE user.uid = :uid")
    public Integer PrivateSave(Long uid, String slogan, String username, String avatar,Integer remainMoney);

    @Modifying
    @Query("UPDATE User user SET user.remainMoney = user.remainMoney - :spend WHERE user.uid = :uid AND user.remainMoney > :spend")
    public Integer decreaseMoney(Long uid, Integer spend);

    @Query("SELECT new com.example.main.dao.AuthDao(user.username, user.password, user.uid, user.role) FROM User user WHERE user.username = :username")
    public Optional<AuthDao> getAuthUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.role = :role WHERE user.uid = :uid")
    public Integer updateUserStatusByUid(Long uid, Role role);

    @Query("SELECT new com.example.main.dto.UserStateDto(user.uid, user.username, user.avatar, user.role) FROM User user WHERE user.username = :username")
    public Optional<UserStateDto> getUserStateByUsername(String username);
}
