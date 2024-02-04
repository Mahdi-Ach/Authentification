import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginService } from '../service/login.service';

export const loggingInterceptor: HttpInterceptorFn = (req, next) => {
  if(!req.url.includes("/login")){
    console.log("a")
    let newrequest = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${localStorage.getItem("access-token")}`)
    })
    return next(newrequest)
  }
  console.log("i m in intercept")
  return next(req);
};
