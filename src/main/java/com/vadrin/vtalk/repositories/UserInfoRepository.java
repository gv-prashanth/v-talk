package com.vadrin.vtalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

}
