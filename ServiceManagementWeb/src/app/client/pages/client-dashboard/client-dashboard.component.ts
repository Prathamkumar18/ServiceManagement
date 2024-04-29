import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-client-dashboard',
  templateUrl: './client-dashboard.component.html',
  styleUrl: './client-dashboard.component.scss'
})
export class ClientDashboardComponent {
  ads:any[];
  validateForm:FormGroup;

  constructor(private clientService:ClientService){}

  ngOnInit(){
    this.validateForm=new FormGroup({
      service: new FormControl(null,[Validators.required])
    });
    this.getAllAds();
  }

  f(event:any){}

  getAllAds(){
    this.clientService.getAllAds().subscribe(res =>{
      this.ads=res;
    })
  }

  searchAdByName(){
    this.clientService.searchAdByName(this.validateForm.get('service').value).subscribe(res=>{
      this.ads=res;
    });
  }
  
  updateImage(image){
    return 'data:image/jpeg;base64,'+image;
  }
}
