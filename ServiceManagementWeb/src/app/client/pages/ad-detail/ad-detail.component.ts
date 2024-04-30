import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserStorageService } from '../../../basic/services/storage/user-storage.service';

@Component({
  selector: 'app-ad-detail',
  templateUrl: './ad-detail.component.html',
  styleUrl: './ad-detail.component.scss',
})
export class AdDetailComponent {
  adId = this.activatedRoute.snapshot.params['adId'];
  avatarUrl: any;
  ad: any;
  validateForm: FormGroup;
  reviews: any;

  constructor(
    private clientService: ClientService,
    private activatedRoute: ActivatedRoute,
    private Router: Router,
    private notification: NzNotificationService
  ) {}

  ngOnInit() {
    this.validateForm = new FormGroup({
      bookDate: new FormControl(null, [Validators.required]),
    });
    this.getAdDetailsById();
  }

  getAdDetailsById() {
    this.clientService.getAdDetailsByAdId(this.adId).subscribe((res) => {
      this.avatarUrl = 'data:image/jpeg;base64,' + res.adDTO.returnedImg;
      this.ad = res.adDTO;
      this.reviews = res.reviewDTO;
    });
  }

  bookService() {
    const bookServiceDTO = {
      bookDate: this.validateForm.get('bookDate').value,
      adId: this.adId,
      userId: UserStorageService.getUserId(),
    };
    this.clientService.bookService(bookServiceDTO).subscribe((res) => {
      this.notification.success('SUCCESS', 'Request posted successfully!', {
        nzDuration: 5000,
      });
      this.Router.navigateByUrl('/client/bookings');
    });
  }
}
