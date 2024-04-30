import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserStorageService } from '../../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  validateForm: FormGroup;
  constructor(
    private authService: AuthService,
    private notification: NzNotificationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.validateForm = new FormGroup({
      userName: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
    });
  }

  submitForm() {
    this.authService
      .login(
        this.validateForm.get(['userName']).value,
        this.validateForm.get(['password']).value
      )
      .subscribe(
        (res) => {
          this.notification.success('SUCCESS', 'login successful', {
            nzDuration: 5000,
          });
          console.log(res);
          if (UserStorageService.isClientLoggedIn()) {
            this.router.navigateByUrl('client/dashboard');
          } else if (UserStorageService.isCompanyLoggedIn()) {
            this.router.navigateByUrl('company/dashboard');
          }
        },
        (error) => {
          this.notification.error('ERROR', `${error.error}`, {
            nzDuration: 5000,
          });
        }
      );
  }
}
