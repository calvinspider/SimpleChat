package org.yang.zhang.mapper;

import org.yang.zhang.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper extends JpaRepository<UserRole,Integer> {

}