package com.ccAssets.dependentmicroservice.sample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentEntityRepository extends JpaRepository<DependentEntity, Long>{

}
