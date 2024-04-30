import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ActivatedRoute, Router } from '@angular/router';
import { UserStorageService } from '../../../basic/services/storage/user-storage.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrl: './review.component.scss'
})
export class ReviewComponent {
  bookId=this.activatedRoute.snapshot.params['id'];

  validateForm:FormGroup;

  constructor(private clientService:ClientService,private notification:NzNotificationService,private router:Router,private activatedRoute:ActivatedRoute){}

  ngOnInit(){
    this.validateForm=new FormGroup({
      rating: new FormControl(null,[Validators.required]),
      review: new FormControl(null,[Validators.required])
    });
  }

  giveReview(){
    const reviewDTO={
      rating: this.validateForm.get('rating').value,
      review: this.validateForm.get('review').value,
      userId: UserStorageService.getUserId(),
      bookId: this.bookId
    };
    console.log(reviewDTO.rating)
    this.clientService.giveReview(reviewDTO).subscribe(res=>{
      this.notification.success('SUCCESS','Review posted successfully!',{nzDuration:5000});
      this.router.navigateByUrl('/client/bookings');
    },error=>{
      this.notification.error('ERROR',`${error.message}`,{nzDuration:5000});
    });
  }
}
