import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../service/login.service';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  loginForm!: FormGroup;
  socialUser!: SocialUser;
  isLoggedin?: boolean;
  response !: string;
  constructor(
    private formBuilder: FormBuilder,
    private socialAuthService: SocialAuthService,
    private serviceLogin: LoginService,
    private cookieService:CookieService,
    private router:Router,
    private route: ActivatedRoute
  ) {
    
  }
  ngOnInit() {
    let code = this.route.snapshot.queryParamMap.get('code');
    localStorage.removeItem('access-token');
    localStorage.removeItem("refresh-token")
    this.initializeForm();
    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      this.isLoggedin = user != null;
      console.log(this.socialUser);
      this.serviceLogin.getResponseLoginGoogle(this.socialUser,"http://localhost:8087/api/user/authenticate-google")
      .subscribe({
        next :(value:any)=>{
          this.handleLoginSuccess(value)},
          error : err=>{console.log(err)}
      })
    });
    if(code!=null){
      this.getTokenGithub(code)
    }
  }
  handleLoginSuccess(value: any): void {
    const access_token = value['access-token'];
    const refresh_token = value['refresh-token'];
  
    localStorage.setItem("access-token", access_token);
    localStorage.setItem("refresh-token", refresh_token);
  
    this.router.navigate(['/HiUser']);
  }
  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
      // Define your form controls here
      password: ['', Validators.required],
      email: ['', Validators.required],
      // Add more form controls as needed
    });
  }
  onSubmit(): void {
    if (this.loginForm.valid) {
      // Form is valid, perform submission logic here
      console.log('Form submitted:', this.loginForm.controls['email'].value);
      this.serviceLogin.getResponseLogin({email:this.loginForm.controls['email'].value,password:this.loginForm.controls['password'].value},"http://localhost:8087/api/user/authenticate").subscribe({
        next : (value:any) =>{
          this.handleLoginSuccess(value);
        },
        error : err =>{
          this.response = err.error;
        }
      })
    } else {
      // Form is invalid, display error messages or handle accordingly
      console.log('Form is invalid');
    }
  }
  loginWithGoogle(): void {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }
  logOut(): void {
    this.socialAuthService.signOut();
  }
  signInWithGitHub() {
    const clientId = '550790b5794a0235277e';
    const redirectUri = 'http://localhost:4200/login';
    window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}`;
  }
  getTokenGithub(code:string){
    this.serviceLogin.getResponseLoginGithub("http://localhost:8087/api/user/callback?code="+code).subscribe({
        next : (value:any) =>{
          console.log(value)
          this.handleLoginSuccess(value);
        },
        error : (err:any) =>{
          this.response = err.error;
        }
      })
  }
}
