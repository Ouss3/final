import { Component } from '@angular/core';
import {CommonModule, NgForOf} from "@angular/common";
import {RouterLink, RouterOutlet} from "@angular/router";
import {SecurityService} from "../services/security.service";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js";


@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    RouterOutlet,CommonModule
  ],
 providers:[SecurityService],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  actions:Array<any> =[
    {title:"Fournisseurs", "route":"/admin/clients" ,icon:"people"},
    {title:"Products", "route":"/admin/products" ,icon:"shop"},
    // {title:"Sales", "route":"/admin/sales" ,icon:"card-list"}
  ];
  currentAction:any;



  constructor(public securityService:SecurityService,public keycloak:KeycloakService) {
  }

  setCurent(action: any) {
    this.currentAction = action;




  }

  async login() {
    await this.keycloak.login({
      redirectUri: window.location.origin
    });
  }

  logout() {
     this.keycloak.logout(window.location.origin).then(r => console.log(r));
  }
}
