import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { UserStorageService } from '../../../basic/services/storage/user-storage.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.scss',
})
export class MyBookingsComponent {
  bookedServices: any[];

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.getMyBookings();
  }

  getMyBookings() {
    this.clientService.getAllBookingsByUserId().subscribe((res) => {
      this.bookedServices = res;
    });
  }
}
