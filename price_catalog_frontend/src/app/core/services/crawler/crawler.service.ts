import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ExplorationData } from '../../models/exploration-data';
import { MessageResponse } from '../../models/message-response';

@Injectable({
  providedIn: 'root',
})
export class CrawlerService {
  private readonly url = `${environment.baseUrl}/collector/crawler`;

  constructor(private http: HttpClient) {}

  public startExploration(
    explorationData: ExplorationData
  ): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(
      `${this.url}/start`,
      explorationData
    );
  }

  public stopExploration(): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.url}/start`, {});
  }

  public verifyIsCrawlerRunning(): Observable<boolean> {
    return this.http.get<boolean>(`${this.url}`);
  }
}
