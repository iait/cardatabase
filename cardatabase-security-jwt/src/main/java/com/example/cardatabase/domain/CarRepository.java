package com.example.cardatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

	@Query("select c from Car c where c.brand = ?1")
	List<Car> findByBrand(@Param("brand") String brand);

	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(@Param("brand") String brand);

	List<Car> findByBrandOrderByYearAsc(@Param("brand") String brand);

	List<Car> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);

	List<Car> findByColor(@Param("color") String color);

	List<Car> findByYear(@Param("year") Integer year);

}
