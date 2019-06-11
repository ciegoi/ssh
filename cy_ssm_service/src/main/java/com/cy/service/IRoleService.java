package com.cy.service;

import com.cy.domain.Permission;
import com.cy.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRoleService {
    List<Role> findAll() throws Exception;

    void save(Role role) throws Exception;

    List<Permission> findOtherPermission(String roleId) throws Exception;

    Role findById(String roleId) throws Exception;

    void addPermissionToRole(String roleId, String[] permissionIds) throws Exception;
}
