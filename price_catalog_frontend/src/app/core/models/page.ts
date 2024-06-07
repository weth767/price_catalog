import { Pageable } from './pageable';
import { Sortable } from './sortable';

export abstract class Page<T> {
  content?: T[];
  pageable?: Pageable;
  totalElements?: number;
  totalPages?: number;
  last?: boolean;
  first?: boolean;
  size?: number;
  number?: number;
  sort?: Sortable[] | Sortable;
  numberOfElements?: number;
  empty?: boolean;

  public get isEmpty(): boolean {
    return this.totalElements == 0;
  }

  public get isNotEmpty(): boolean {
    return (this.totalElements ?? 0) > 0;
  }
}
