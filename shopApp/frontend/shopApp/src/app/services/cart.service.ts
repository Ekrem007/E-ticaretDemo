import { CartItemDto } from './../models/cartItemDto';
import { Product } from './../models/product';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cart } from '../models/cart';
import { SingleResponseModel } from '../models/singleResponseModel';
import { ResponseModel } from '../models/responseModel';
import { CartItem } from '../models/cartItem';
import { ListResponseModel } from '../models/listResponseModel';



@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8082/api/carts';

  constructor(private http: HttpClient) {}

  getTotalQuantity(userId: number): Observable<SingleResponseModel<number>> {
    return this.http.get<SingleResponseModel<number>>(`${this.apiUrl}/totalQuantity?userId=${userId}`);
  }

  getTotalPrice(userId: number): Observable<SingleResponseModel<number>> { 
    return this.http.get<SingleResponseModel<number>>(`${this.apiUrl}/totalPrice?userId=${userId}`);
  }
  
  addToCart(cartItem: CartItem): Observable<ResponseModel> {
    return this.http.post<ResponseModel>(`${this.apiUrl}/add`, cartItem);
  }
  getCart():Observable<ListResponseModel<CartItemDto>>{
    return this.http.get<ListResponseModel<CartItemDto>>(this.apiUrl+"/getWaitingCarts")
  }
  deleteCartItem(cartDtoId: number): Observable<ResponseModel> {
    return this.http.delete<ResponseModel>(`${this.apiUrl}/delete?cartDtoId=${cartDtoId}`);
}
makeOrder(): Observable<SingleResponseModel<string>> {
  return this.http.post<SingleResponseModel<string>>(this.apiUrl + "/order", {});
}
getOrders():Observable<ListResponseModel<CartItemDto>>{
  return this.http.get<ListResponseModel<CartItemDto>>(this.apiUrl+"/getAll")
}
sendOrder(cartId: number): Observable<SingleResponseModel<string>> {
  return this.http.put<SingleResponseModel<string>>(`${this.apiUrl}/${cartId}/complete`, {});
}

}
