package com.example.cardatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

	@Query("select c from Car c where c.brand = ?1")
	List<Car> findByBrand(String brand);

	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(String brand);

	List<Car> findByBrandOrderByYearAsc(String brand);

	List<Car> findByBrandAndModel(String brand, String model);

	List<Car> findByColor(String color);

	List<Car> findByYear(Integer year);

}
