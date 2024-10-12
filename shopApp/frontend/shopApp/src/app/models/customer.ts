import { Order } from "./order";

export interface Customer {
    id: number;
    name: string;
    email: string;
    phoneNumber: string;
    orders: Order[];
  }