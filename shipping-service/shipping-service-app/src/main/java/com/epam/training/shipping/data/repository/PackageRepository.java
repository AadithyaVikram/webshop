package com.epam.training.shipping.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.training.shipping.data.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
}
