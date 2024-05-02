import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent {
  bookId=this.activatedRoute.snapshot.params['id'];
  adId=this.activatedRoute.snapshot.params['adId'];
  originalAmount:number=0;
  totalAmount:number=0;
  discountAmount:number=0;
  couponCode:string="";
  couponMessage:string="";

  constructor(
    private clientService:ClientService
    ,private router:Router
    ,private activatedRoute:ActivatedRoute
    ,private notification:NzNotificationService
  ){}

  ngOnInit(){
    this.getOriginalAmount();
  }

  getOriginalAmount(){
    this.clientService.getPriceByAdId(this.adId).subscribe(res=>{
      this.originalAmount=res;
      this.totalAmount=res;
    })
  }

  applyCoupon(){
    if(this.couponCode==='SAVE10'){
      this.discountAmount=this.originalAmount/10;
      this.totalAmount=this.originalAmount-this.discountAmount;
      this.couponCode="";
      this.couponMessage="SAVE10 applied";
    }
    else if(this.couponCode==='GET20'){
      this.discountAmount=20;
      this.totalAmount=this.originalAmount-this.discountAmount;
      this.couponCode="";
      this.couponMessage="GET20 applied";
    }
    else{
      this.couponCode="";
      this.couponMessage="invalid coupon!";
    }
  }

  payAmount(){
    this.clientService.payAmount(this.bookId,this.totalAmount).subscribe(res=>{
      this.notification.success('SUCCESS','Transaction successful!',{nzDuration:3000});
      this.router.navigateByUrl('/client/bookings');
    },error=>{
      this.notification.error('ERROR','Transaction failed!',{nzDuration:3000});
    })
  }
}
