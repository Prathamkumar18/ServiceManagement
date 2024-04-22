import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {  NzNotificationService } from 'ng-zorro-antd/notification';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-signup-client',
  templateUrl: './signup-client.component.html',
  styleUrl: './signup-client.component.scss'
})
export class SignupClientComponent {
  validateForm!: FormGroup;

  constructor(
    private authService:AuthService,
    private notification:NzNotificationService,
    private router:Router,
   ){}

   ngOnInit(){
    this.validateForm= new FormGroup({
      email: new FormControl(null,[Validators.email,Validators.required]),
      name: new FormControl(null,[Validators.required]),
      lastname: new FormControl(null,[Validators.required]),
      phone: new FormControl(null),
      password:new FormControl(null,[Validators.required]),
      checkPassword: new FormControl(null,[Validators.required]),
    });
   }

   submitForm(){
    console.log(this.validateForm.value)
    this.authService.registerClient(this.validateForm.value).subscribe(res=>{
      this.notification.success('SUCCESS','Signup successful',{nzDuration:5000});
      this.router.navigateByUrl('/login');
    },error=>{
      this.notification.error('ERROR',`${error.error}`,{nzDuration:5000});
    });
   }
}
