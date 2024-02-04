import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-hi-user',
  templateUrl: './hi-user.component.html',
  styleUrl: './hi-user.component.css'
})
export class HiUserComponent implements OnInit{
  constructor(private tokenservice:TokenService,private http:HttpClient,private router:Router,private loginservice:LoginService){}
  ngOnInit(): void {
    this.tokenservice.handle_autorefresh()
  }
  adminlink(){
    this.http.get("http://localhost:8087/api/admin/hello").subscribe({
      next : value =>{console.log(value);this.router.navigate(['/HiAdmin'])},
      error : err =>{console.log(err)}
    })
  }
  superadminlink(){
    this.http.get("http://localhost:8087/api/superadmin/hi").subscribe({
      next : value =>{console.log(value);this.router.navigate(['/HiSuperAdmin'])},
      error : err =>{console.log(err)}
    })
  }
}
