package com.example.branch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.branch.domain.Branch;
import com.example.branch.exception.BranchNotFoundException;
import com.example.branch.repository.BranchRepository;

@Service
public class BranchService {
	
	Logger logger = LoggerFactory.getLogger(BranchService.class);
	
	@Autowired
	BranchRepository branchRepository;
	
    public synchronized Branch save(Branch branch) {
    	logger.info("saving branch {}", branch);
        return branchRepository.save(branch);
    }
    
    public Branch findById(String id) {
        return branchRepository.findById(id).orElseThrow(BranchNotFoundException::new);
    }
    
    public void deleteById(String id) {
    	logger.info("deleting branch id {}", id);
        branchRepository.deleteById(id);
    }
    
    public Iterable<Branch> findAll() {
        return branchRepository.findAll();
    }
    
    public Iterable<Branch> findNearestByLongLat() {
        var allBranches = branchRepository.findAll();
        //do lat lng mojo filter here
        return allBranches;
    }
    
}
