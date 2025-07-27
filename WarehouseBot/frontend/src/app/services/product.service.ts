import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {API_ENDPOINT} from  './apiURL';
import {SessionUser} from '../interface/session-user';
import {Product} from '../interface/product';
import { Observable, of } from 'rxjs';
import {inject} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

 httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  router : Router = inject(Router);

  constructor(private http: HttpClient) { }
  createProd(p : Product) : Observable<any> {
	  return this.http.post(API_ENDPOINT+'/products',p,this.httpOptions);
  }
}
