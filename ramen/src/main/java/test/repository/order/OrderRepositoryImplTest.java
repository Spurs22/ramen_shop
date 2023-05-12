package test.repository.order;

import com.DTO.Order;
import com.repository.order.OrderRepositoryImpl;

public class OrderRepositoryImplTest {

	// 카트 생성 테스트
	   public static void main(String[] args) {
		      Order order = new Order();
		      order.setMemberId(1);
		      order.setDeliveryId(11);
		      order.setStatusName("동근");
		      order.setProductId(111);
		      
		      order.setOrderId(1);
		      order.setStatusId(0);
		      order.setQuantity(2);
		      order.setPrice(2000);
		      order.setFinalPrice(2000);
		      
		      
		      OrderRepositoryImpl or = new OrderRepositoryImpl();
		      // or.createOrder(order);
		      
		      
		   }

}
