import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../environments/environment';
import { Page } from '../models/page';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly url = `${environment.baseUrl}/consumer`;
  constructor(private http: HttpClient) {}

  public getProducts(
    page: number,
    pageSize: number
  ): Observable<Page<Product>> {
    var params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('pageSize', pageSize.toString());
    return this.http.get<Page<Product>>(`${this.url}/product`, {
      params: params,
    });
  }

  public getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.url}/product/${id}`);
  }
}
