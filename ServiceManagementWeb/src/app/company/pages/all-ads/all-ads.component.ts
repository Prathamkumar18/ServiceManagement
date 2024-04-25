import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-all-ads',
  templateUrl: './all-ads.component.html',
  styleUrl: './all-ads.component.scss'
})
export class AllAdsComponent {
  ads:any;

  constructor(private companyService:CompanyService,private notification:NzNotificationService){}

  ngOnInit(){
    this.getAllAdsByUserId();
  }

  getAllAdsByUserId(){
    this.companyService.getAllAdsByUserId().subscribe(res=>{
      this.ads=res;
    })
  }

  updateImage(image){
    return 'data:image/jpeg;base64,'+image;
  }

  deleteAd(adId){
    this.companyService.deleteAd(adId).subscribe(res=>{
      this.notification.success("SUCCESS",`Ad deleted successfully!`,{nzDuration:5000})
      this.getAllAdsByUserId();
    },error=>{  
    this.notification.error("ERROR",`${error.error}`,{nzDuration:5000})
    });
  }
}
