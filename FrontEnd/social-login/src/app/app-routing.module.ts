import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './guards/auth.guard';
import { HiUserComponent } from './hi-user/hi-user.component';
import { HiAdminComponent } from './hi-admin/hi-admin.component';
import { HiSuperAdminComponent } from './hi-super-admin/hi-super-admin.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path:"register",component:RegisterComponent},
  {path:"login",component:LoginComponent},
  {path:"HiUser",component:HiUserComponent,canActivate: [authGuard],data:{role:["USER","ADMIN","SUPERADMIN"]}},
  {path:"HiAdmin",component:HiAdminComponent,canActivate: [authGuard],data:{role:["ADMIN","SUPEADMIN"]}},
  {path:"HiSuperAdmin",component:HiSuperAdminComponent,canActivate: [authGuard],data:{role:["SUPERADMIN"]}},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
