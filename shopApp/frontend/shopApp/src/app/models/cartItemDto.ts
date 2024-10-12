import { OrderStatus } from "./orderStatus";

export interface CartItemDto{     
    id:number
    userId: number;
    productId: number;
    quantity: number;
    status: OrderStatus;
    productName: string;
    productPrice:number;
    imageUrl:string;
}