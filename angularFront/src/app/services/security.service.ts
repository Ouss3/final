import { Injectable } from '@angular/core';
import {KeycloakEventType, KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js";

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  public profile? : KeycloakProfile;
  constructor (public kcService: KeycloakService) {
    this.init();
  }
  init(){

    this.kcService.keycloakEvents$.subscribe({
      next: (e) => {

        if (e.type == KeycloakEventType.OnAuthSuccess) {
          console.log("OnAuthSuccess")
          this.kcService.loadUserProfile().then(profile=>{
            this.profile=profile;
          });
        }
      },
      error : err => {
        console.log(err);
      }
    });
  }
  public hasRoleIn(roles:string[]):boolean{
    let userRoles = this.kcService.getUserRoles();
    for(let role of roles){
      if (userRoles.includes(role)) return true;
    } return false;
  }

  public async getTocken() {
    let token = this.kcService.getToken();
    return await token;
  }
}
