import { LinkWithoutDomain } from './link-without-domain';

export class Domain {
  id?: number;
  name?: string;
  url?: string;
  verified?: boolean;
  verifiedIn?: Date;
  links = new Array<LinkWithoutDomain>();
}
