import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../../environments/environment';
import { CountBrand } from '../../models/count-brand';
import { Page } from '../../models/page';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  private readonly url = environment.baseUrl;
  constructor(private http: HttpClient) {}

  public getCountedBrandsPage(
    page: number,
    pageSize: number
  ): Observable<Page<CountBrand>> {
    var params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('pageSize', pageSize.toString());
    return this.http.get<Page<CountBrand>>(`${this.url}/brand`, {
      params: params,
    });
  }
}
