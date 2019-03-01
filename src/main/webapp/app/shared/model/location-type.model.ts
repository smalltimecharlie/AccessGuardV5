export interface ILocationType {
    id?: number;
    locationTypeName?: string;
    active?: boolean;
}

export class LocationType implements ILocationType {
    constructor(public id?: number, public locationTypeName?: string, public active?: boolean) {
        this.active = this.active || false;
    }
}
