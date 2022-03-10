package com.maxtrain.bootcamp.sales.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.sales.customer.Customer;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Orders>> GetOrders() {
		var orders = orderRepo.findAll();
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Orders> getOrders(@PathVariable int id){
		var order = orderRepo.findById(id);
		if(order.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orders>(order.get(), HttpStatus.OK);
	}
	
	//GET ALL ORDERS WHERE STATUS IS "REVIEW"
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Orders>>getOrdersInReview(){
		var orders = orderRepo.findByStatus("REVIEW");
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Orders> postCustomer(@RequestBody Orders order){
		if(order == null || order.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		order.setStatus("NEW");
		var ord = orderRepo.save(order);
		return new ResponseEntity<Orders>(ord, HttpStatus.CREATED);	
	}
	// SEts status to approved if total is under 50 or review if it is over 50
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewOrder(@PathVariable int id, @RequestBody Orders order) {
		var statusValue= (order.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		order.setStatus(statusValue);
		return putOrder(id, order);
	}
	// APPROVING AN ORDER
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveOrder(@PathVariable int id, @RequestBody Orders order) {
		order.setStatus("APPROVED");
		return putOrder(id, order);
	}
	//REJECTING AN ORDER
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity REJECTOrder(@PathVariable int id, @RequestBody Orders order) {
		order.setStatus("REJECTED");
		return putOrder(id, order);
	}

	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putOrder(@PathVariable int id, @RequestBody Orders order) {
		if(order == null || order.getId()== 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var ord = orderRepo.findById(order.getId());
		if(ord.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		orderRepo.save(order);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteOrder(@PathVariable int id) {
		var order = orderRepo.findById(id);
		if(order.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		orderRepo.delete(order.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	

}
