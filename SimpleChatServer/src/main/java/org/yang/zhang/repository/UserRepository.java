package org.yang.zhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.yang.zhang.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User>{
       User findByName(String name);
}