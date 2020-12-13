package com.journalyourtrade.apigateway.repository;

import com.journalyourtrade.apigateway.model.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
