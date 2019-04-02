import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) {  }

  async getMonthData() {
    return this.http.get('http://youthfighter.top/dailyreport/v1/dailyreport?year=2019&month=4').toPromise();
  }
}
