import { Component, OnInit, Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Tag } from './Tag';

@Component({
	selector: 'app-calendar',
	templateUrl: './calendar.component.html',
	styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
	
	@Input('canEdit')
	private canEdit = false;
	get CanEdit() {
		return this.canEdit;
	}
	private _calenderData: any[][] = [[], [], [], [], [], []];
	get calenderData() {
		return this._calenderData;
	}
	private tempMonthDatas = new Map(); //月数据缓存
	private _year: number;
	get year() {
		return this._year;
	}
	set year(value) {
		if (value < 1970) value = 1970;
		this._year = value;
	}

	private _month: number;
	get month() {
		return this._month;
	}
	set month(value) {
		if (value < 0) {
			this._year -= 1;
			this._month += 11;
		} else if (value > 11) {
			this._year += 1;
			this._month -= 11;
		} else {
			this._month = value;
		}
	}

	public MonthChangedEvent = new EventEmitter<any>();

	constructor() { }

	ngOnInit() {
		let curDate = new Date();
		this.year = curDate.getFullYear();
		this.month = curDate.getMonth();
		this.initCalenderData();
	}

	private initCalenderData() {
		this.MonthChangedEvent.emit({ year: this.year, month: this.month });
		let tempMonthData = this.tempMonthDatas.get(`${this.year}-${this.month}`);
		if (tempMonthData) {
			this._calenderData = tempMonthData;
			return false;
		}
		let targetDate = new Date(this.year, this.month);
		let week = targetDate.getDay();
		let tempArr: any[] = [];
		for (let i = 0; i < 42; i++) {
			let d = new Date(this.year, this.month, i - week + 1);
			tempArr.push({
				date: d,
				day: d.getDate(),
				month: d.getMonth(),
				year: d.getFullYear()
			});
		}
		let tempCalenderData = [[], [], [], [], [], []];
		for (let i = 0; i < 6; i++) {
			for (let j = 0; j < 7; j++) {
				let idx = i * 7 + j;
				tempCalenderData[i][j] = tempArr[idx];
			}
		}
		this._calenderData = tempCalenderData;
		this.tempMonthDatas.set(`${this.year}-${this.month}`, tempCalenderData);
		console.log(this._calenderData);
	}

	isToday(cd: Date) {
		let td = new Date();
		return cd.toLocaleDateString() == td.toLocaleDateString() && td.getMonth() == this.month;
	}

	isCurrentMonth() {
		let td = new Date();
		return this.year == td.getFullYear() && this.month == td.getMonth();
	}

	toCurrentMonth() {
		let td = new Date();
		if (this.year == td.getFullYear() && this.month == td.getMonth()) return;
		this.year = td.getFullYear();
		this.month = td.getMonth();
		this.initCalenderData()
	}

	lastMonth() {
		this.month -= 1;
		this.initCalenderData();
	}

	nextMonth() {
		this.month += 1;
		this.initCalenderData();
	}

	/**
	 * @param day 要设置的日记是那一天
	 * @param tags 标签数组
	 */
	public setDayTags(day: number, tags: Tag[]) {
		if (day < 1) return
		this._calenderData.forEach(ele => {
			ele.forEach(e => {
				if (this.year === e.year && this.month === e.month && day === e.day) {
					e['tags'] = tags;
				}
			});
		});
	}

	/**
	 * 设置某月每天的标签
	 */
	public setMonthTags(data: Map<number, Tag[]>) {
		if (!data) return;
		this._calenderData.forEach(ele => {
			ele.forEach(e => {
				if (this.year === e.year && this.month === e.month) {
					e['tags'] = data[e.day] || [];
				}
			});
		});
	}


	public TagMouseOverEvent = new EventEmitter();
	private mouseOverTag(data: Map<number, Tag[]>) {
		this.TagMouseOverEvent.emit(data);
	}
	public TagMouseOutEvent = new EventEmitter();
	private mouseOutTag(data: Map<number, Tag[]>) {
		this.TagMouseOutEvent.emit(data);
	}

	private hasTag(tagsArr: any[], type: number) {
		let flag = false;
		for (let ele of tagsArr) {
			if (ele.type === type) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public addTag(i: number, j: number, type: number) {
		let tagsArr = this._calenderData[i][j].tags;
		if (tagsArr) {
			if (!this.hasTag(tagsArr, type)) tagsArr.push({ type });
		} else {
			this._calenderData[i][j].tags = [{ type }];
		}
	}

	drag(event, data) {
		if (!this.canEdit) return false;
		console.log(event);
		event.dataTransfer.setData("Text", JSON.stringify(data));
	}

	allowDrop(event) {
		if (!this.canEdit) return false;
		event.preventDefault();
		console.log('allow')
	}

	private BeforeAddTagEvent = new EventEmitter();
	drop(event, row: number, column: number) {
		if (!this.canEdit) return false;
		event.preventDefault();
		if (isNaN(row) || isNaN(column)) return false;
		this.BeforeAddTagEvent.emit(JSON.parse(event.dataTransfer.getData("Text")))
		this.addTag(row, column, parseInt(event.dataTransfer.getData("Text")));
	}

	@ViewChild('tbody')
	tbody: ElementRef;

	@ViewChild('tag')
	tag: ElementRef;
}
