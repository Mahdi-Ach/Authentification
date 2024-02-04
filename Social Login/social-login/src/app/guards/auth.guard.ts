import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { jwtDecode } from 'jwt-decode';
interface JwtPayloads {
  iss?: string;
  sub?: string;
  aud?: string[] | string;
  exp?: number;
  nbf?: number;
  iat?: number;
  jti?: string;
  role?: string[];
}
export const authGuard: CanActivateFn = (route, state) => {
  const router:Router = inject(Router);
  const access_token = localStorage.getItem("access-token");
  let decode = null;
  if(access_token){
    decode = jwtDecode<JwtPayloads>(access_token);
  }
  if(decode?.sub && decode.role!=null && decode.role.length > 0 && decode.role.some(i => route.data['role'].includes(i))){
    return true;
  }
  router.navigate(["/login"]);
  return false;

};
