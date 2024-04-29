import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';

@Component({
  selector: 'app-company-dashboard',
  templateUrl: './company-dashboard.component.html',
  styleUrl: './company-dashboard.component.scss'
})
export class CompanyDashboardComponent {
  bookings:any;

  constructor(private companyService:CompanyService){}

  ngOnInit(){
    this.getAllAdBookings()
  }

  getAllAdBookings(){
    this.companyService.getAllAdBookings().subscribe(res=>{
      this.bookings=res;
    })
  }
}
