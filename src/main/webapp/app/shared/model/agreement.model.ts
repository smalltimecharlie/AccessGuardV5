import { Moment } from 'moment';
import { IAgreementType } from 'app/shared/model/agreement-type.model';

export interface IAgreement {
    id?: number;
    agreementName?: string;
    agreementStartDate?: Moment;
    agreementEndDate?: Moment;
    sourceId?: string;
    source?: string;
    agreenmentType?: IAgreementType;
}

export class Agreement implements IAgreement {
    constructor(
        public id?: number,
        public agreementName?: string,
        public agreementStartDate?: Moment,
        public agreementEndDate?: Moment,
        public sourceId?: string,
        public source?: string,
        public agreenmentType?: IAgreementType
    ) {}
}
