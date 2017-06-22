package rs.team15.service;

import java.util.Collection;

import rs.team15.model.Bill;
import rs.team15.model.ClientOrder;
import rs.team15.model.OrderItem;

public interface OrderService {
	
	ClientOrder create(ClientOrder order);
	
	OrderItem addNew(OrderItem item);
	
	ClientOrder update(ClientOrder order);
	
	ClientOrder find(Integer id);
	
	Collection<ClientOrder> findByEmployee(String email);
	
	Collection<ClientOrder> findByRestaurant(String restaurant);

	OrderItem findItem(Integer id);
	
	OrderItem updateItem(OrderItem item);
	
	OrderItem delete(OrderItem item);
	
	Bill createBill(Bill bill);
	
}
