import { OrderItem } from "./orderItem";


export interface Order {
  id: number;
  totalAmount: number;
  customerId: number;
  orderItems: OrderItem[];  
}
