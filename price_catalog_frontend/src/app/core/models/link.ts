import { DomainWithoutLink } from './domain-without-link';

export class Link {
  id?: number;
  url?: string;
  verified?: boolean;
  verifiedIn?: Date;
  domain?: DomainWithoutLink;
}
