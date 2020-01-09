package com.example.branch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.branch.domain.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, String> { 
	
    @Query("SELECT b FROM Branch b WHERE b.lon=:lonA and b.lon=:lonB and b.lat=:latA and b.lat=:latB") 
    public List<Branch> fetchBranches(@Param("lonA") double lonA, @Param("lonB") double lonB, 
    								  @Param("latA") double latA, @Param("latB") double latB );    

}
