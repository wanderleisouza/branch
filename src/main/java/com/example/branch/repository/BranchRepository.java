package com.example.branch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.branch.domain.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, String> { }
