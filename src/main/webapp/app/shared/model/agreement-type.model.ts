import { Moment } from 'moment';

export interface IAgreementType {
    id?: number;
    agreementUrl?: string;
    agreementTypeName?: string;
    agreementTypeStartDate?: Moment;
    agreementTypeEndDate?: Moment;
}

export class AgreementType implements IAgreementType {
    constructor(
        public id?: number,
        public agreementUrl?: string,
        public agreementTypeName?: string,
        public agreementTypeStartDate?: Moment,
        public agreementTypeEndDate?: Moment
    ) {}
}
