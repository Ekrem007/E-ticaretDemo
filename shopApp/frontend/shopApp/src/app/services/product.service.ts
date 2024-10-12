import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ListResponseModel } from '../models/listResponseModel';

import { SingleResponseModel } from '../models/singleResponseModel';
import { Product } from '../models/product';
import { ProductAddDto } from '../models/productAddDto';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  
  apiUrl = "http://localhost:8082/api/products"


  constructor(private http:HttpClient) { }

  getProducts():Observable<ListResponseModel<Product>>{
    return this.http.get<ListResponseModel<Product>>(this.apiUrl + "/getAll")
  }
  getProductsByCategory(categoryId:number):Observable<ListResponseModel<Product>>{
    return this.http.get<ListResponseModel<Product>>(this.apiUrl + "/getByCategory?categoryId=" + categoryId)
  }

  getProductsById(productId:number):Observable<SingleResponseModel<Product>>{
    return this.http.get<SingleResponseModel<Product>>(this.apiUrl + "/getByProductId?productId=" + productId)

  }
  addProduct(product:ProductAddDto):Observable<SingleResponseModel<ProductAddDto>>{
    return this.http.post<SingleResponseModel<ProductAddDto>>(this.apiUrl+"/add",product)
  }
  updateProduct(product:ProductAddDto, productId: number): Observable<SingleResponseModel<ProductAddDto>> {
    return this.http.put<SingleResponseModel<ProductAddDto>>(this.apiUrl + "/update?productId=" + productId, product);
  }
  deleteProduct(productId:number):Observable<SingleResponseModel<number>>{
    return this.http.delete<SingleResponseModel<number>>(this.apiUrl+"/delete?productId=" + productId)
  }
}