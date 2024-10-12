import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ListResponseModel } from '../models/listResponseModel';
import { Category } from '../models/category';
import { SingleResponseModel } from '../models/singleResponseModel';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) { }

  apiUrl="http://localhost:8082/api/category"

  getCategories():Observable<ListResponseModel<Category>>{
    return this.http.get<ListResponseModel<Category>>(this.apiUrl + "/getAll")
  }
  getCategoryById(categoryId:number):Observable<SingleResponseModel<Category>>{
    return this.http.get<SingleResponseModel<Category>>(this.apiUrl + "/getCategoryById?categoryId=" + categoryId)
  }
  addCategory(category:Category):Observable<SingleResponseModel<Category>>{
    return this.http.post<SingleResponseModel<Category>>(this.apiUrl + "/add", category)
  }
  updateCategory(category:Category, categoryId: number): Observable<SingleResponseModel<Category>> {
    return this.http.put<SingleResponseModel<Category>>(this.apiUrl + "/update?categoryId=" + categoryId, category);
  }
  deleteCategory(categoryId:number):Observable<SingleResponseModel<number>>{
    return this.http.delete<SingleResponseModel<number>>(this.apiUrl + "/delete?categoryId=" + categoryId)
  }
  

  }

