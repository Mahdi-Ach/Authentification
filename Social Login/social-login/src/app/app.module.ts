import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FacebookLoginProvider, GoogleLoginProvider, GoogleSigninButtonModule, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
//import { loggingInterceptor } from './Interceptors/logging.interceptor';
import { HiUserComponent } from './hi-user/hi-user.component';
import { loggingInterceptor } from './Interceptors/logging.interceptor';
import { HiAdminComponent } from './hi-admin/hi-admin.component';
import { HiSuperAdminComponent } from './hi-super-admin/hi-super-admin.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HiUserComponent,
    HiAdminComponent,
    HiSuperAdminComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SocialLoginModule,
    GoogleSigninButtonModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    provideHttpClient(withInterceptors([loggingInterceptor])),
    CookieService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '177506121026-cpq7ns9rqs67jt8qe9ce2c5coppnfdt9.apps.googleusercontent.com'
            )
          },
          /*{
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('177506121026-cpq7ns9rqs67jt8qe9ce2c5coppnfdt9.apps.googleusercontent.com')
          }*/
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
