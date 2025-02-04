import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";
import {ProductService} from "../services/product.service";
import {product} from "../model/product.model";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {filter, Subject} from "rxjs";
import {Ng2SearchPipeModule} from "ng2-search-filter";
import {FilterPipe} from "../filter.pipe";
import {DataTablesModule} from "angular-datatables";
import DataTables from 'datatables.net';
import {NgxPaginationModule} from "ngx-pagination";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {SecurityService} from "../services/security.service";







@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, RouterLink, HttpClientModule, ReactiveFormsModule,FormsModule,FilterPipe,NgxPaginationModule ],
  templateUrl: './products.component.html',
  providers:[ProductService,SecurityService],
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {
  public productForm!:FormGroup;
  public products:Array<product> =[];
  public searchText:string= '';
  public isAdd:boolean= true;
  public currentProduct!: number;



  constructor(public productService:ProductService,private formBuilder:FormBuilder,public keycloakService :KeycloakService,public securityService:SecurityService) {

   }

  ngOnInit(): void {
    this.getProducts();
    this.clearForm();




  }
  clearForm(){
    this.productForm=this.formBuilder.group({
      name:this.formBuilder.control(null, this.isAdd? Validators.required :null ),
      price:this.formBuilder.control(null, this.isAdd?Validators.required:null),
      quantity:this.formBuilder.control(null, this.isAdd?Validators.required:null),
      description:this.formBuilder.control(null, this.isAdd?Validators.required:null),
      desponibility:this.formBuilder.control(true),
    });
  }
  getProductById(id:number){
    this.productService.getProductById(id).subscribe({
      next:data => {
        this.productForm=this.formBuilder.group({
          name:this.formBuilder.control(data.name, this.isAdd? Validators.required :null ),
          price:this.formBuilder.control(data.price, this.isAdd?Validators.required:null),
          quantity:this.formBuilder.control(data.quantity, this.isAdd?Validators.required:null),
          description:this.formBuilder.control(data.description, this.isAdd?Validators.required:null),
          desponibility:this.formBuilder.control(true),
        });

      },
      error: error => {
        console.error('There was an error!', error);
      } } );
  }
  getProducts(){



    this.productService.getProducts().subscribe({
      next:data => {
        this.products = data;

      },
      error: error => {
        console.error('There was an error!', error);
      } } );
  }


  deleteProduct(id:number){
     if(confirm("Are you sure to delete "))

    this.productService.deleteProduct(id).subscribe({
       next:deletProduct => {
        this.products = this.products.filter(product => product.idProduct != id);
     },
       error: error => {
        console.error('There was an error!', error);
       } } );
  }

  saveProduct() {
    let p:product = this.productForm.value;
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
    let p:product = this.productForm.value;
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
