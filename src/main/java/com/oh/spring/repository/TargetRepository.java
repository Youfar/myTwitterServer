package com.oh.spring.repository;
import com.oh.spring.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Serializable>{
    Target findByCreator_UserId(Integer userId);
}
