import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';

@Component({
  selector: 'app-all-ads',
  templateUrl: './all-ads.component.html',
  styleUrl: './all-ads.component.scss'
})
export class AllAdsComponent {
  ads:any;

  constructor(private companyService:CompanyService){}

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
}
