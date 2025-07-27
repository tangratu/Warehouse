import { Component, OnInit } from '@angular/core';
import {ReactiveFormsModule,Validators } from '@angular/forms';
import {FormControl, FormGroup} from '@angular/forms';
import { RouterModule } from '@angular/router';
import {Order} from '../interface/order';
import {OrderService} from '../services/order.service';
import {AsyncPipe,NgFor} from '@angular/common';
import { Observable } from 'rxjs';
import {Product} from '../interface/product';

@Component({
  selector: 'app-order',
  imports: [ReactiveFormsModule,RouterModule,AsyncPipe,NgFor],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit {
	totalPrice : number;
	prodOptions$ : Observable<Product[]>;
	orderItems : string[];
	status: string;
	route: string;
	order: Map<string,number>;
	addformgrp = new FormGroup({		
		product: new FormControl<Product| null>(null,[Validators.required]),
		qty: new FormControl<number>(0,[Validators.required,Validators.min(1)]),		
    });
	

	constructor(private orderService: OrderService) {
		this.prodOptions$=this.orderService.getProdOptions();
		this.totalPrice=0;
		this.orderItems = [];
		this.status='NA';
		this.route='';
		this.order= new Map<string,number>();
		
	}
	ngOnInit() : void {
		
		this.totalPrice=0;
		this.orderItems = [];
	}
	
	
	addProduct() : void {
		this.orderItems.push(this.addformgrp.value.product!.name+' x '+this.addformgrp.value.qty!.toString()+' | Cost: '+(this.addformgrp.value.product!.price*this.addformgrp.value.qty!).toString());
		this.totalPrice+=this.addformgrp.value.product!.price*this.addformgrp.value.qty!;
		this.order.set(this.addformgrp.value.product!.name,this.addformgrp.value.qty!);
		console.log(this.order);
	}
	clearOrd() : void {
		this.orderItems= [];
		this.totalPrice=0;		
		this.order.clear();
	}
	placeOrd() : void {
		console.log(this.order);
		this.orderService.postOrder(this.order).subscribe(answ => {
			console.log(answ);
			if(answ.status=="FAIL") {
				this.status="FAIL: "+answ.message;
				this.route='';
			}
			else {
				this.status="SUCCESS: "+answ.message;
			    this.orderService.getRoute(answ.id).subscribe(r => this.route=r.route);
		    }
		});
		this.clearOrd();
		
	}
		
	

}
