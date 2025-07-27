import { Component } from '@angular/core';
import {ReactiveFormsModule,Validators } from '@angular/forms';
import {FormControl, FormGroup} from '@angular/forms';
import { RouterModule } from '@angular/router';
import {User} from '../interface/user';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
	
	constructor(private userService : UserService) {
		
	}
	logformgroup = new FormGroup({		
		username: new FormControl<string|null>('',[Validators.required,Validators.maxLength(25),Validators.minLength(3)]),
		password: new FormControl<string|null>('',[Validators.required]),		
    });
	onLogin(): void {
		
		var m = { "username": <string>this.logformgroup.value.username,"password":<string>this.logformgroup.value.password};
		
		this.userService.login(m).subscribe
		(
			(u) =>
			{
				if(!u)
				{
					console.warn("no such user");
				}
				else
				{
					
					console.log(typeof u);
					sessionStorage.setItem('name',u.name);
					sessionStorage.setItem('email',u.email);
					sessionStorage.setItem('address',u.address);					
					sessionStorage.setItem('flag','set');
					this.userService.goToIndex();

				}
				
		
			}
		);
		
		
		
		this.logformgroup.reset();		
	}

}
