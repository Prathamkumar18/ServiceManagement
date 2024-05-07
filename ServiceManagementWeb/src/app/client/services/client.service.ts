import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../basic/services/storage/user-storage.service';
import { Observable } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  constructor(private http: HttpClient) {}

  getAllAds(): Observable<any> {
    return this.http.get(BASIC_URL + `api/client/ads`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getAllAdsSorted(category:string,sortBy:string): Observable<any>{
    return this.http.get(BASIC_URL + `api/client/ads/${category}/${sortBy}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  searchAdByName(name: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/client/search/${name}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getAdDetailsByAdId(adId: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/client/ad/${adId}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  bookService(bookDTO: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/client/book-service`, bookDTO, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getAllBookingsByUserId(): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http.get(BASIC_URL + `api/client/my-bookings/${userId}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getPriceByAdId(adId:any): Observable<any> {
    return this.http.get(BASIC_URL + `api/client/ad/price/${adId}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  giveReview(reviewDTO: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/client/review`, reviewDTO, {
      headers: this.createAuthorizationHeader(),
    });
  }

  payAmount(bookId: any,amount:number): Observable<any> {
    return this.http.post(BASIC_URL + `api/client/payment/${bookId}`,amount , {
      headers: this.createAuthorizationHeader(),
    });
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    );
  }
}
