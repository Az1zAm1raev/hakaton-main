package com.example.hakaton.repository.security;

import com.example.hakaton.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrganizationsRepository extends JpaRepository<Organization, Long> {
}
