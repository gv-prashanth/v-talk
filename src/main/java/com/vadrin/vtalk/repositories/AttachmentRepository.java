package com.vadrin.vtalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

}
