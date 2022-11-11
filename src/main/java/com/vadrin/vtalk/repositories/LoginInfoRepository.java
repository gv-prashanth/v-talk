package com.vadrin.vtalk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Integer> {

  public List<LoginInfo> findAllByOrderByIdDesc();
  public LoginInfo findFirstBySenderOrderByIdDesc(String sender);
}
