package org.yang.zhang.mapper;

import org.yang.zhang.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMapper extends JpaRepository<RolePermission,Integer> {

}