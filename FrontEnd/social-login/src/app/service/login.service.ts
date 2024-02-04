import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtPayload } from 'jwt-decode';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  isAuthentificated : boolean = false;
  role:string[]=[];
  username:any="";
  access_token:any="";
  constructor(private http:HttpClient) { }
  getResponseLogin(data:any,url:string): Observable<string> {
    return this.http.post<string>(url,data);
  }
  loadProfil(data:JwtPayload,access_token:string){
    this.isAuthentificated = true;
    this.username = data.sub;
    this.access_token = access_token;
    console.log(data);
  }
  getResponseLoginGoogle(data:any,url:string): Observable<string> {
    return this.http.post<string>(url,data);
  }
  getResponseLoginGithub(url:string): Observable<string> {
    return this.http.get<string>(url);
  }
  exist_token(){
    return this.http.get("http://localhost:8087/api/user/check_token")
  }
  refresh_token(refresh_token:any){
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    const body = new URLSearchParams();
    body.set('refresh_token', refresh_token);
    console.log({"refresh_token":refresh_token})
    return this.http.post("http://localhost:8087/api/user/refresh-token", body.toString(), { headers });
    }
}
