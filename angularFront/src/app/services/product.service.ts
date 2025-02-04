import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {product} from "../model/product.model";
import {KeycloakService} from "keycloak-angular";
import {OAuthService} from "angular-oauth2-oidc";


@Injectable({
  providedIn: 'root'

})
export class ProductService {

  constructor(private http:HttpClient) { }

  getProducts(page:number=0,size:number=100):Observable<Array<product>>{



    return   this.http.get<Array<product>>(`http://localhost:8888/PRODUCT-SERVICE/api/products`);

  }
  deleteProduct(id:number){
   return  this.http.delete<product>('http://localhost:8888/PRODUCT-SERVICE/api/products/'+id);
  }

  addProduct(p: product):Observable<product> {
    return  this.http.post<product>(`http://localhost:8888/PRODUCT-SERVICE/api/products`,p);
  }
  updateProduct(id:number ,p: product):Observable<product> {
    return  this.http.put<product>(`http://localhost:8888/PRODUCT-SERVICE/api/products/${id}`,p);
  }

  getProductById(id: number):Observable<product> {
    return  this.http.get<product>(`http://localhost:8888/PRODUCT-SERVICE/api/products/${id}`);

  }
}
