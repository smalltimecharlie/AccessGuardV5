import { ILocationType } from 'app/shared/model/location-type.model';
import { IEndpointType } from 'app/shared/model/endpoint-type.model';
import { IAgreementOrganisation } from 'app/shared/model/agreement-organisation.model';

export interface IOrganisationEndpoint {
    id?: number;
    organisation?: string;
    connectionUri?: string;
    active?: boolean;
    locationType?: ILocationType;
    endpointType?: IEndpointType;
    agreementOrganisation?: IAgreementOrganisation;
}

export class OrganisationEndpoint implements IOrganisationEndpoint {
    constructor(
        public id?: number,
        public organisation?: string,
        public connectionUri?: string,
        public active?: boolean,
        public locationType?: ILocationType,
        public endpointType?: IEndpointType,
        public agreementOrganisation?: IAgreementOrganisation
    ) {
        this.active = this.active || false;
    }
}
