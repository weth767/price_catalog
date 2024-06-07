import { Brand } from './brand';
import { ProductPrice } from './product-price';

export class Product {
  id?: number;
  name?: string;
  description?: string;
  brand?: Brand;
  status?: boolean;
  productPrices?: ProductPrice[];
}
