import { OrderStatus } from "./orderStatus";

export interface CartItem {
    userId: number;
    productId: number;
    quantity: number;
    status: OrderStatus;
  }
  