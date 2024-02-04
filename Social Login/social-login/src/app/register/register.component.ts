import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterService } from '../service/register.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  myForm!: FormGroup;
  response !: string;
  constructor(private formBuilder: FormBuilder,private serviceRegister:RegisterService) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.myForm = this.formBuilder.group({
      // Define your form controls here
      password: ['', Validators.required],
      email: ['', Validators.required],
      // Add more form controls as needed
    });
  }

  onSubmit(): void {
    if (this.myForm.valid) {
      // Form is valid, perform submission logic here
      console.log('Form submitted:', this.myForm.controls['email'].value);
      this.serviceRegister.getResponseSingup({email:this.myForm.controls['email'].value,password:this.myForm.controls['password'].value},"http://localhost:8087/api/user/register").subscribe({
        next : (value:any) =>{
          console.log(value)
          this.response = value.accept;
        },
        error : err =>{
          console.log("error")
          this.response = err.error.reject;
        }
      })
    } else {
      // Form is invalid, display error messages or handle accordingly
      console.log('Form is invalid');
    }
  }
}
