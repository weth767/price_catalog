import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../../environments/environment';
import { Domain } from '../../models/domain';
import { Page } from '../../models/page';

@Injectable({
  providedIn: 'root',
})
export class DomainService {
  private readonly url = `${environment.baseUrl}/collector/domain`;

  constructor(private http: HttpClient) {}

  public getDomainsPageabled(
    page: number,
    pageSize: number
  ): Observable<Page<Domain>> {
    var params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('pageSize', pageSize.toString());
    return this.http.get<Page<Domain>>(`${this.url}`, {
      params: params,
    });
  }
}
