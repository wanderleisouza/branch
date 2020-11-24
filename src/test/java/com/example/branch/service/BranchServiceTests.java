package com.example.branch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.branch.domain.Branch;
import com.example.branch.exception.BranchNotFoundException;
import com.example.branch.repository.BranchRepository;

@SpringBootTest
public class BranchServiceTests {
	
    @Autowired
    private BranchService branchService;
 
    @MockBean
    private BranchRepository branchRepository;
    
    @Test
    public void whenFindAllShouldReturnAllItems() {
    	
        Branch b1 = new Branch("-103", "VILA LEOPOLDINA", -46.728521, -23.530247, 0);
        Branch b2= new Branch("-1281", "VILA LEOPOLDINA", -46.727677, -23.534168, 0);
        Iterable<Branch> iterable = Arrays.asList(b1, b2);
        
        Mockito.reset(branchRepository);
        Mockito.when(branchRepository.findAll()).thenReturn(iterable);
        
    	Iterable<Branch> all = branchService.findAll();
    	long actual = StreamSupport.stream(all.spliterator(), false).count();
    	long expected = StreamSupport.stream(iterable.spliterator(), false).count();
    	assertEquals(expected, actual);
    	
    }
    
    @Test
    public void whenInvalidIDthenBranchShouldBeNotFound() {
        Mockito.when(branchRepository.findById("ABC")).thenThrow(NullPointerException.class);
        Mockito.reset(branchRepository);
    	assertThrows(BranchNotFoundException.class, () -> branchService.findById("ABC"));
    }
    
    @Test
    public void whenFindByLatLongShouldReturnNearstItems() {
    	
        Branch b1 = new Branch("A103", "VILA LEOPOLDINA", -46.728521, -23.530247, 0);
        Branch b2 = new Branch("A1281", "VILA LEOPOLDINA", -46.727677, -23.534168, 0);
        Branch b3 = new Branch("A46", "TUCURUVI", -46.617545, -23.472328, 0);
        Iterable<Branch> iterable = Arrays.asList(b1, b2, b3);
        
        Mockito.reset(branchRepository);
        Mockito.when(branchRepository.findAll()).thenReturn(iterable);
      
    	Iterable<Branch> all = branchService.findNearestByLonLat(-46.739716, -23.525636, 20000);
    	long actual = StreamSupport.stream(all.spliterator(), false).count();
    	assertEquals(3, actual);
        
    	all = branchService.findNearestByLonLat(-46.739716, -23.525636, 2000);
    	actual = StreamSupport.stream(all.spliterator(), false).count();
    	assertEquals(2, actual);
    	
    	all = branchService.findNearestByLonLat(-46.739716, -23.525636, 20);
    	actual = StreamSupport.stream(all.spliterator(), false).count();
    	assertEquals(0, actual);
    	
    }
    
}
