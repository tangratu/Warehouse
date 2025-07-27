import { Component } from '@angular/core';
import {ReactiveFormsModule,Validators } from '@angular/forms';
import {FormControl, FormGroup} from '@angular/forms';
import { RouterModule } from '@angular/router';
import {User} from '../interface/user';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule,RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
	constructor(private userService: UserService) {
		
	}
	regformgroup = new FormGroup({
		
		username: new FormControl<string>('',[Validators.required,Validators.maxLength(25),Validators.minLength(3)]),
	
		password: new FormControl<string>('',[Validators.required]),
		address: new FormControl<string>('',[Validators.required,Validators.maxLength(40)]),
		email: new FormControl<string>('',[Validators.required,Validators.email]),
});
    onSubmit(): void {
		
		let user: User = {			
            name: this.regformgroup.value.username!,
            password: this.regformgroup.value.password!,
            address: this.regformgroup.value.address!,
            email: this.regformgroup.value.email!,            
		};
		console.log(user);
		
		this.userService.register(user).subscribe(u =>  this.userService.goToLogin());
		
    }

}
