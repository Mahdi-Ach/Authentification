import { Injectable } from '@angular/core';
import { Observable, catchError, tap } from 'rxjs';
import { LoginService } from './login.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor(private loginService: LoginService, private router: Router) {}

  handle_autorefresh(){
    this.loginService.exist_token().subscribe({
      next: (value:any) =>{},
      error: (err:any) =>{
        if(err.status === 403){
          this.loginService.refresh_token(localStorage.getItem("refresh-token")).subscribe({
            next: (value:any) =>{console.log(value);},
            error: (err:any) =>{
              console.log(err)
              this.router.navigate(['/login']);
            }
          })
        }
      }
    })
  }
}
