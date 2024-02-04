import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-hi-admin',
  templateUrl: './hi-admin.component.html',
  styleUrl: './hi-admin.component.css'
})
export class HiAdminComponent {
  constructor(private tokenservice:TokenService,private router:Router,private loginservice:LoginService){}
  ngOnInit(): void {
    this.tokenservice.handle_autorefresh()
  }
}
