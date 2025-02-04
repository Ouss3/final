import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {mergeMap, Observable} from "rxjs";
import {KeycloakService} from "keycloak-angular";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private keycloak:KeycloakService) { }

  // intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  //   // Récupération du token d'authentification (à remplacer par votre code)
  //   const token = 'mon-token-d-authentification';
  //
  //   // Ajout du token dans les entêtes de la requête
  //   const authReq = request.clone({
  //     setHeaders: {
  //       Authorization: Bearer ${token}
  //     }
  //   });
  //
  //   // Envoi de la requête avec les nouvelles entêtes
  //   return next.handle(authReq);
  // }

  intercept(req:HttpRequest<any>, next: HttpHandler) {
    const { enableBearerInterceptor, excludedUrls } = this.keycloak;
    if (!enableBearerInterceptor) {
      return next.handle(req);
    }
    // const shallPass = excludedUrls.findIndex((item => this.isUrlExcluded(req, item))) > -1;
    // if (shallPass) {
    //   return next.handle(req);
    // }
    return this.keycloak.addTokenToHeader(req.headers).pipe(mergeMap((headersWithBearer => {
      const kcReq = req.clone({ headers: headersWithBearer });
      return next.handle(kcReq);
    })));
  }
}
