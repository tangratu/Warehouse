import { Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {OrderComponent} from './order/order.component';
import {ProductComponent} from './product/product.component'

export const routes: Routes = [
	{path: 'login', component: LoginComponent},
	{path: 'register', component: RegisterComponent},
	{path: 'order',component: OrderComponent},
	{path: 'product',component: ProductComponent},
];
