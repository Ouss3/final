import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import {client} from "../model/client.model";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

    constructor(private http:HttpClient) { }

    getProducts(page:number=0,size:number=100):Observable<Array<client>>{



        return   this.http.get<Array<client>>(`http://localhost:8888/CLIENT-SERVICE/api/clients`);

    }
    deleteProduct(id:number){
        return  this.http.delete<client>('http://localhost:8888/CLIENT-SERVICE/api/clients/'+id);
    }

    addProduct(p: client):Observable<client> {
        return  this.http.post<client>(`http://localhost:8888/CLIENT-SERVICE/api/clients`,p);
    }
    updateProduct(id:number ,p: client):Observable<client> {
        return  this.http.put<client>(`http://localhost:8888/CLIENT-SERVICE/api/clients/${id}`,p);
    }

    getProductById(id: number):Observable<client> {
        return  this.http.get<client>(`http://localhost:8888/CLIENT-SERVICE/api/clients/${id}`);

    }
}
