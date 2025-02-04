import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  formLogin!:FormGroup;

  constructor(private formBuilder:FormBuilder ,private router:Router) {
  }
  ngOnInit(): void {
    this.formLogin=this.formBuilder.group({
      username:this.formBuilder.control(""),
      password:this.formBuilder.control("")

    });
  }

  handleLogin() {
if (this.formLogin.value.username=="admin" && this.formLogin.value.password=="admin"){
  this.router.navigate(["/admin"]);
}
  }
}
