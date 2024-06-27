import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../../environments/environment';
import { Link } from '../../models/link';
import { Page } from '../../models/page';

@Injectable({
  providedIn: 'root',
})
export class LinkService {
  private readonly url = `${environment.baseUrl}/collector/link`;

  constructor(private http: HttpClient) {}

  public getDomainsPageabled(
    page: number,
    pageSize: number,
    domain: string | null
  ): Observable<Page<Link>> {
    var params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('pageSize', pageSize.toString());
    if (domain != null) {
      params = params.append('domain', domain.toString());
    }
    return this.http.get<Page<Link>>(`${this.url}`, {
      params: params,
    });
  }
}
