import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {API_ENDPOINT} from  './apiURL';
import {SessionUser} from '../interface/session-user';
import {User} from '../interface/user';
import { Observable, of } from 'rxjs';
import {inject} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  router : Router = inject(Router);

  constructor(private http: HttpClient) { }
  register(u: User): Observable<any>{
	 return this.http.post(API_ENDPOINT+'/users',u,this.httpOptions);
  }
  login(m: any) :Observable<SessionUser> {
	 
	  return this.http.post<SessionUser>(API_ENDPOINT+'/users/verify',m,this.httpOptions);
  }
  authenticate() : boolean {
	  if(sessionStorage.getItem('flag')){ //drop this and the whole flag thing
		  return true;
	  }
	  return false;
  }
  goToIndex() : void {
	  this.router.navigate(['/order']);
  }
  goToLogin() : void {
	  this.router.navigate(['/login']);
  }
}
