package com.example.branch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.branch.domain.Branch;
import com.example.branch.service.BranchService;

@RestController
public class BranchController {
	
	@Autowired
	BranchService branchService;
	
	@GetMapping("/{id}")
	public Branch findById(@PathVariable final String id) {
		return branchService.findById(id);
	}
	
	@GetMapping("/")
	public Iterable<Branch> findAll() {
		return branchService.findAll();
	}
	
	@PostMapping("/")
	@PutMapping("/")
	public Branch save(@RequestBody final Branch branch) {
		return branchService.save(branch);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable final String id) {
		branchService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
