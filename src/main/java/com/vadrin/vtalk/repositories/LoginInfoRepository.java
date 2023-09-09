package com.vadrin.vtalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Integer> {
  
}
