import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrl: './create-ad.component.scss',
})
export class CreateAdComponent {
  selectedFile: File | null;
  imagePreview: String | ArrayBuffer | null;
  validateForm: FormGroup;

  constructor(
    private router: Router,
    private companyService: CompanyService,
    private notification: NzNotificationService
  ) {}

  ngOnInit() {
    this.validateForm = new FormGroup({
      serviceName: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      price: new FormControl(null, [Validators.required]),
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }
  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }
  postAd() {
    const formData: FormData = new FormData();
    formData.append('img', this.selectedFile);
    formData.append('serviceName', this.validateForm.value.serviceName);
    formData.append('description', this.validateForm.value.description);
    formData.append('price', this.validateForm.value.price);
    console.log(formData.get('img'));
    this.companyService.postAd(formData).subscribe(
      (res) => {
        this.notification.success('SUCCESS', `Ad posted successfully!`, {
          nzDuration: 5000,
        });
        this.router.navigateByUrl('/company/ad');
      },
      (error) => {
        this.notification.error('ERROR', `${error.error}`, {
          nzDuration: 5000,
        });
      }
    );
  }
}
