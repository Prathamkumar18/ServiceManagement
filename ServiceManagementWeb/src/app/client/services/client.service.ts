import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../basic/services/storage/user-storage.service';
import { Observable } from 'rxjs';

const BASIC_URL="http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http:HttpClient) { }

  getAllAds():Observable<any>{
    return this.http.get(BASIC_URL+`api/client/ads`,{
      headers: this.createAuthorizationHeader()
    });
  }

  searchAdByName(name:any):Observable<any>{
    return this.http.get(BASIC_URL+`api/client/search/${name}`,{
      headers: this.createAuthorizationHeader()
    });
  }

  createAuthorizationHeader(): HttpHeaders{
    let authHeaders:HttpHeaders=new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer '+UserStorageService.getToken()
    )
  }
}
