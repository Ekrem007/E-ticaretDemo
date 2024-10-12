import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SingleResponseModel } from '../models/singleResponseModel';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }

  private apiUrl = 'http://localhost:8082/api/users';

   
  login(username: string, password: string): Observable<SingleResponseModel<string>> {
    return this.http.post<SingleResponseModel<string>>(`${this.apiUrl}/login?username=${username}&password=${password}`, {});

  }
  
  
}
