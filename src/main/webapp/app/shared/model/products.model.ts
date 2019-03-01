import { IProductsUsers } from 'app/shared/model/products-users.model';

export interface IProducts {
    id?: number;
    productName?: string;
    productUrl?: string;
    productsUsers?: IProductsUsers;
}

export class Products implements IProducts {
    constructor(public id?: number, public productName?: string, public productUrl?: string, public productsUsers?: IProductsUsers) {}
}
