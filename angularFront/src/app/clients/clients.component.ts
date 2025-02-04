import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";

import {ClientsService} from "../services/clients.service";
import {client} from "../model/client.model";
import {FilterPipe} from "../filter.pipe";
import {CommonModule, NgForOf} from "@angular/common";
import {ProductService} from "../services/product.service";
import {SecurityService} from "../services/security.service";
import {HttpClientModule} from "@angular/common/http";
import {NgxPaginationModule} from "ngx-pagination";

@Component({
  selector: 'app-clients',
  standalone: true,
    imports: [CommonModule,RouterLink, FilterPipe, NgForOf, ReactiveFormsModule, FormsModule,  HttpClientModule,NgxPaginationModule],
    providers:[ProductService,SecurityService,ClientsService],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.css'
})
export class ClientsComponent implements OnInit{
  public productForm!:FormGroup;
  public clients:Array<client> =[];
  public searchText:string= '';
  public isAdd:boolean= true;
  public currentProduct!: number;



  constructor(public productService:ClientsService,private formBuilder:FormBuilder) {

  }

  ngOnInit(): void {
    this.getProducts();
    this.clearForm();




  }
  clearForm(){
    this.productForm=this.formBuilder.group({
      nom:this.formBuilder.control(null, this.isAdd? Validators.required :null ),
        prenom:this.formBuilder.control(null, this.isAdd?Validators.required:null),
        email:this.formBuilder.control(null, this.isAdd?Validators.required:null),
        password:this.formBuilder.control(null, this.isAdd?Validators.required:null),

    });
  }
  getProductById(id:number){
    this.productService.getProductById(id).subscribe({
      next:data => {
        this.productForm=this.formBuilder.group({
          name:this.formBuilder.control(data.nom, this.isAdd? Validators.required :null ),
          price:this.formBuilder.control(data.prenom, this.isAdd?Validators.required:null),
          quantity:this.formBuilder.control(data.email, this.isAdd?Validators.required:null),
          description:this.formBuilder.control(data.password, this.isAdd?Validators.required:null),

        });

      },
      error: error => {
        console.error('There was an error!', error);
      } } );
  }
  getProducts(){



    this.productService.getProducts().subscribe({
      next:data => {
        this.clients = data;

      },
      error: error => {
        console.error('There was an error!', error);
      } } );
  }


  deleteProduct(id:number){
    if(confirm("Are you sure to delete "))

      this.productService.deleteProduct(id).subscribe({
        next:deletProduct => {
          this.clients = this.clients.filter(product => product.idClient != id);
        },
        error: error => {
          console.error('There was an error!', error);
        } } );
  }

  saveProduct() {
    let p:client = this.productForm.value;
    this.productService.addProduct(p).subscribe({
      next: data => {
        this.getProducts();
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });

  }


  modifyProduct(id:number) {
    let p:client= this.productForm.value;
    this.productService.updateProduct(id,p).subscribe({
      next: data => {
        this.getProducts();
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

}
