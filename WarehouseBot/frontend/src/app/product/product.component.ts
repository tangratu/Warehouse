import { Component } from '@angular/core';
import {ReactiveFormsModule,Validators } from '@angular/forms';
import {FormControl, FormGroup} from '@angular/forms';
import { RouterModule } from '@angular/router';
import {Product} from '../interface/product';
import {ProductService} from '../services/product.service';

@Component({
  selector: 'app-product',
  imports: [ReactiveFormsModule,RouterModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
	constructor(private productService : ProductService) {
		
	}
	prodformgrp = new FormGroup({
		
		name: new FormControl<string>('',[Validators.required]),
	
		price: new FormControl<number>(0,[Validators.required,Validators.min(0.01)]),
		quantity: new FormControl<number>(0,[Validators.required,Validators.min(1)]),
		x_coord: new FormControl<number>(0,[Validators.required,Validators.min(0)]),
		y_coord: new FormControl<number>(0,[Validators.required,Validators.min(0)]),
});
    onCreate(): void {
		
		let prod: Product = {			
            name: this.prodformgrp.value.name!,
            price: this.prodformgrp.value.price!,
            quantity: this.prodformgrp.value.quantity!,
            location: {x:this.prodformgrp.value.x_coord!,y:this.prodformgrp.value.y_coord!},
		};
		console.log(prod);
		this.prodformgrp.reset();
		this.productService.createProd(prod).subscribe(u => console.log(u));
		
    }

}
