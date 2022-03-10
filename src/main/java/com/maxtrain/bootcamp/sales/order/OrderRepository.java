package com.maxtrain.bootcamp.sales.order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Integer> {
	Iterable<Orders> findByStatus(String status);
}
