import {Cell} from './cell';
export interface Product {
	name:string;
	quantity:number;
	price:number;
	location: Cell;
}
