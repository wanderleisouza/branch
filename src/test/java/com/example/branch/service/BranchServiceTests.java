package com.example.branch.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.branch.exception.BranchNotFoundException;
import com.example.branch.repository.BranchRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BranchServiceTests {
	
    @Autowired
    private BranchService branchService;
 
    @MockBean
    private BranchRepository branchRepository;
    
    @Before
    public void setUp() {
        Mockito.when(branchRepository.findById("ABC")).thenThrow(NullPointerException.class);
    }
    
    @Test
    public void whenInvalidIDthenBranchShouldBeNotFound() {
    	assertThrows(BranchNotFoundException.class, () -> branchService.findById("ABC"));
     }
   
}
