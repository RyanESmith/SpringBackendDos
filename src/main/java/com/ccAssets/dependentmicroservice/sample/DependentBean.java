package com.ccAssets.dependentmicroservice.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DependentBean {
	
	private String independentProperty1;
	private String independentProperty2;
	private String independentProperty3;
	private String dependentProperty1;
	private String dependentProperty2;
	private String dependentProperty3;

}
