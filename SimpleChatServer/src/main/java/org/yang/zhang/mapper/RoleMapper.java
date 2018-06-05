package org.yang.zhang.mapper;

import org.yang.zhang.module.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends JpaRepository<Role,Integer> {

}