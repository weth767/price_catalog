import { Sortable } from './sortable';

export class Pageable {
  pageNumber?: number;
  pageSize?: number;
  sort?: Sortable[] | Sortable;
  offset?: number;
  paged?: boolean;
  unpaged?: boolean;
}
