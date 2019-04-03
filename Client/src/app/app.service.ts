import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  async getMonthData(year: number, month: number) {
    return this.http.get(`http://api.youthfighter.top/dailyreport/v1/monthreport?year=${year}&month=${month}`).toPromise();
  }
}
