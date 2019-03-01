export interface IEndpointType {
    id?: number;
    endpointTypeName?: string;
    active?: boolean;
}

export class EndpointType implements IEndpointType {
    constructor(public id?: number, public endpointTypeName?: string, public active?: boolean) {
        this.active = this.active || false;
    }
}
