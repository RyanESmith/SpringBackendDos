package com.ccAssets.dependentmicroservice.sample.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ccAssets.dependentmicroservice.sample.DependentBean;
import com.ccAssets.dependentmicroservice.sample.DependentEntity;
import com.ccAssets.dependentmicroservice.sample.DependentEntityRepository;

@RestController
@RequestMapping("/dependentEntity")
@CrossOrigin(origins = "http://localhost:3000")
public class DependentController {
	
	@Autowired
	private DependentEntityRepository repo;
	
	@GetMapping
	public Collection<DependentEntity> getAll(){
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DependentEntity> getDependentEntity(@PathVariable Long id){
		Optional<DependentEntity> result = repo.findById(id);
		return result.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<DependentEntity>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public ResponseEntity<DependentEntity> createDependentEntity(@Valid @RequestBody DependentEntity entity){
		DependentEntity result = repo.save(entity);
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DependentEntity> updateDependentEntity(@Valid @RequestBody DependentEntity entity ){
		DependentEntity result = repo.save(entity);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DependentEntity> deleteDependentEntity(@PathVariable Long id){
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{dependentId}/independentEntity/{independentId}")
	public DependentBean getDependent(@PathVariable Long dependentId, @PathVariable Long independentId) {
		Optional<DependentEntity> result =  repo.findById(dependentId);
		DependentEntity dependentEntity = result.orElse(new DependentEntity());
		
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("id", independentId);
		
		ResponseEntity<DependentBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/independentEntity/"+independentId, DependentBean.class, uriVariables);
		
		DependentBean response = responseEntity.getBody();
		
		return new DependentBean(response.getIndependentProperty1(), response.getIndependentProperty2(), response.getIndependentProperty3(), 
				dependentEntity.getProperty1(), dependentEntity.getProperty2(), dependentEntity.getProperty3());
	}
}
