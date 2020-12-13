package com.journalyourtrade.apigateway.repository;

import com.journalyourtrade.apigateway.model.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
}
