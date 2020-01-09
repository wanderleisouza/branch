package com.example.branch.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.branch.domain.Branch;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BranchRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private BranchRepository branchRepository;
    
    @Test
    public void whenFindByIdThenReturnBranch() {
    	
        // given
        Branch branch = new Branch("1", "Headquarter", -43.121d, -23.2333d, 0);
        entityManager.persist(branch);
        entityManager.flush();
     
        // when
        Optional<Branch> found = branchRepository.findById(branch.getId());
     
        // then
        assertThat(found.get().getName()).isEqualTo(branch.getName());
        
    }
	
}
