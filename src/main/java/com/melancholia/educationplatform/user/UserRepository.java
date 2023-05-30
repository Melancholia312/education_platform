package com.melancholia.educationplatform.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Modifying
    @Query(value = "insert into users_privileges (user_id, privilege_id) VALUES (:userId, :permissionId)", nativeQuery = true)
    @Transactional
    void addPermission(@Param("userId") long userId, @Param("permissionId") long permissionId);
}
