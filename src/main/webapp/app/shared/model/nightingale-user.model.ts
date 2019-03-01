import { Moment } from 'moment';
import { IAgreementOrganisation } from 'app/shared/model/agreement-organisation.model';

export interface INightingaleUser {
    id?: number;
    email?: string;
    active?: boolean;
    cognitoUsername?: string;
    cognitoEventContentType?: string;
    cognitoEvent?: any;
    cognitoSignupDate?: Moment;
    agreementOrganisation?: IAgreementOrganisation;
}

export class NightingaleUser implements INightingaleUser {
    constructor(
        public id?: number,
        public email?: string,
        public active?: boolean,
        public cognitoUsername?: string,
        public cognitoEventContentType?: string,
        public cognitoEvent?: any,
        public cognitoSignupDate?: Moment,
        public agreementOrganisation?: IAgreementOrganisation
    ) {
        this.active = this.active || false;
    }
}
