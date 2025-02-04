import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import {ReactiveFormsModule} from "@angular/forms";
import {ProductService} from "./services/product.service";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {ClientsService} from "./services/clients.service";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  providers:[ProductService,ClientsService],
  styleUrl: './app.component.css'

})
export class AppComponent {

}
