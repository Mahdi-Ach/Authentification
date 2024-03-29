import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) {}
  getResponseSingup(data:any,url:string): Observable<string> {
    return this.http.post<string>(url,data);
  }
}
