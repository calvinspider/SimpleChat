package org.yang.zhang.mapper;

import org.yang.zhang.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMapper extends JpaRepository<Permission,Integer> {

}