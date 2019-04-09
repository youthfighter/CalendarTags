import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Tag } from './components/calendar/Tag';

@Injectable({
	providedIn: 'root'
})
export class AppService {

	private BASE_PATH = 'http://api.youthfighter.top/calendartag';

	constructor(private http: HttpClient) { }

	async getMonthData(year: number, month: number) {
		if (year < 1970 || month < 1 || month > 12) {
			return {
				error: '参数错误'
			}
		}
		return {
			data: {
				'1': [
					{
						id: '1',
						name: '学习',
						color: '#abc'
					}
				]
			}
		}
		//return this.http.get(`${this.BASE_PATH}/v1/monthreport?year=${year}&month=${month}`).toPromise();
	}
}
