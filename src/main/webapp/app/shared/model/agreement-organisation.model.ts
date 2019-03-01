import { Moment } from 'moment';
import { IAgreement } from 'app/shared/model/agreement.model';

export interface IAgreementOrganisation {
    id?: number;
    sourceSystemAgreementId?: string;
    agreementType?: string;
    agreementName?: string;
    organisation?: string;
    agreementStatus?: string;
    createdDate?: Moment;
    agreement?: IAgreement;
}

export class AgreementOrganisation implements IAgreementOrganisation {
    constructor(
        public id?: number,
        public sourceSystemAgreementId?: string,
        public agreementType?: string,
        public agreementName?: string,
        public organisation?: string,
        public agreementStatus?: string,
        public createdDate?: Moment,
        public agreement?: IAgreement
    ) {}
}
