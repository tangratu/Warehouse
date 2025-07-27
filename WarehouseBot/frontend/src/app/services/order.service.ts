import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {API_ENDPOINT} from  './apiURL';
import {Product} from '../interface/product';
import { Observable, of } from 'rxjs';
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {Order} from '../interface/order';
import {HttpParams} from "@angular/common/http";
import {Route} from '../interface/route';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  router : Router = inject(Router);

  constructor(private http: HttpClient) { }
  getProdOptions() : Observable<Product[]>{
	 return this.http.get<Product[]>(API_ENDPOINT+'/products',this.httpOptions);
  }
  postOrder(order:Map<string,number>) :Observable<Order> {
	  
	  return this.http.post<Order>(API_ENDPOINT+'/orders',Object.fromEntries(order.entries()),this.httpOptions);
  }
  getRoute(id: number) : Observable<Route> {
	  const req_param = new HttpParams().set('order_id',id);	  
	  return this.http.get<Route>(API_ENDPOINT+'/orders/route',{headers: new HttpHeaders({ 'Content-Type': 'application/json' }),params: req_param});
  }
}
