import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommunity } from 'app/shared/model/community.model';

type EntityResponseType = HttpResponse<ICommunity>;
type EntityArrayResponseType = HttpResponse<ICommunity[]>;

@Injectable({ providedIn: 'root' })
export class CommunityService {
  public resourceUrl = SERVER_API_URL + 'api/communities';

  constructor(protected http: HttpClient) {}

  create(community: ICommunity): Observable<EntityResponseType> {
    return this.http.post<ICommunity>(this.resourceUrl, community, { observe: 'response' });
  }

  update(community: ICommunity): Observable<EntityResponseType> {
    return this.http.put<ICommunity>(this.resourceUrl, community, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommunity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommunity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
