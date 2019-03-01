import { IProducts } from 'app/shared/model/products.model';

export interface IProductsUsers {
    id?: number;
    email?: string;
    active?: boolean;
    products?: IProducts;
}

export class ProductsUsers implements IProductsUsers {
    constructor(public id?: number, public email?: string, public active?: boolean, public products?: IProducts) {
        this.active = this.active || false;
    }
}
