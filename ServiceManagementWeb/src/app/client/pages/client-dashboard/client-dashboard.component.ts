import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-client-dashboard',
  templateUrl: './client-dashboard.component.html',
  styleUrl: './client-dashboard.component.scss',
})
export class ClientDashboardComponent {
  ads: any[]=[];
  validateForm: FormGroup;
  buttonNo:number=0;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.validateForm = new FormGroup({
      service: new FormControl(null, [Validators.required]),
    });
    this.getAllAds();
  }

  getAllAds() {
    if(this.buttonNo===0){
      this.clientService.getAllAds().subscribe((res) => {
        this.ads = res;
      });
    }
    else if(this.buttonNo===1){
      this.clientService.getAllAdsSorted("price","low").subscribe((res) => {
        this.ads = res;
      });
    }
    else if(this.buttonNo===2){
      this.clientService.getAllAdsSorted("price","high").subscribe((res) => {
        this.ads = res;
      });
    }
    else if(this.buttonNo===3){
      this.clientService.getAllAdsSorted("average_rating","low").subscribe((res) => {
        this.ads = res;
      });
    }
    else if(this.buttonNo===4){
      this.clientService.getAllAdsSorted("average_rating","high").subscribe((res) => {
        this.ads = res;
      });
    }
  }

  getSortedAdsByCategory(){

  }

  searchAdByName() {
    this.clientService
      .searchAdByName(this.validateForm.get('service').value)
      .subscribe((res) => {
        this.ads = res;
      });
  }

  updateImage(image) {
    return 'data:image/jpeg;base64,' + image;
  }
}
