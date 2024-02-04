import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-hi-super-admin',
  templateUrl: './hi-super-admin.component.html',
  styleUrl: './hi-super-admin.component.css'
})
export class HiSuperAdminComponent {
  constructor(private tokenservice:TokenService,private router:Router,private loginservice:LoginService){}
  ngOnInit(): void {
    this.tokenservice.handle_autorefresh()
  }
}
