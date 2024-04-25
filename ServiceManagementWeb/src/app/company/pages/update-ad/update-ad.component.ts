import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-update-ad',
  templateUrl: './update-ad.component.html',
  styleUrl: './update-ad.component.scss'
})
export class UpdateAdComponent {
  adId:any=this.activatedRoute.snapshot.params['id'];
  selectedFile:File | null ;
  imagePreview:String | ArrayBuffer |null;
  validateForm:FormGroup;
  existingImg: string| null;
  imgChanged=false;

  constructor(private companyService:CompanyService,private activatedRoute:ActivatedRoute,private notification:NzNotificationService,private router:Router){}

  ngOnInit(){
    this.validateForm=new FormGroup({
      serviceName: new FormControl(null,[Validators.required]),
      description: new FormControl(null,[Validators.required]),
      price: new FormControl(null,[Validators.required]),
    });
    this.getAdById();
  }

  getAdById(){
    this.companyService.getAdById(this.adId).subscribe(res=>{
      this.existingImg='data:image/jpeg;base64,'+ res['returnedImg'];
      this.validateForm.patchValue(res);
    })
  }


  onFileSelected(event:any){
    this.selectedFile=event.target.files[0];
    this.previewImage();
    this.existingImg=null;
    this.imgChanged=true;
  }

  previewImage(){
    const reader=new FileReader();
    reader.onload=()=>{
      this.imagePreview=reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }
  updateAd(){
    const formData:FormData=new FormData();
    if(this.imgChanged && this.selectedFile){
      formData.append('img',this.selectedFile);
    }
    formData.append('serviceName',this.validateForm.value.serviceName);
    formData.append('description',this.validateForm.value.description);
    formData.append('price',this.validateForm.value.price);
    this.companyService.updateAd(this.adId,formData).subscribe(res=>{
      this.notification.success("SUCCESS",`Ad updated successfully!`,{nzDuration:5000})
      this.router.navigateByUrl("/company/ads");
    },error=>{  
    this.notification.error("ERROR",`${error.error}`,{nzDuration:5000})
    });
  }
}
