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
	public ErrorEvent = new EventEmitter();
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
		let dayData = this.getDayData(day);
		dayData['tags'] = tags;
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
	private getDayData(day: number) {
		let result;
		for (let i = 0; i < this._calenderData.length; i++) {
			for (let j = 0; j < this._calenderData[i].length; j++) {
				let dayData = this._calenderData[i][j];
				if (this.year === dayData.year && this.month === dayData.month && day === dayData.day) {
					result = dayData;
					break;
				}
			}
		}
		return result;
	}

	public TagMouseOverEvent = new EventEmitter();
	private mouseOverTag(data: Map<number, Tag[]>) {
		this.TagMouseOverEvent.emit(data);
	}
	public TagMouseOutEvent = new EventEmitter();
	private mouseOutTag(data: Map<number, Tag[]>) {
		this.TagMouseOutEvent.emit(data);
	}

	private hasTag(tagsArr: Tag[], tagId: string) {
		let flag = false;
		if (Array.isArray(tagsArr)) {
			for (let ele of tagsArr) {
				if (ele.id === tagId) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public addTag(day: number, tag: Tag) {
		if (day < 1) {
			this.ErrorEvent.emit('day < 1');
			return false;
		}
		let dayData = this.getDayData(day);
		if (Array.isArray(dayData['tags'])) {
			dayData['tags'].push(tag);
		} else {
			dayData['tags'] = tag;
		}
	}

	private drag(event, tag: Tag) {
		if (!this.canEdit) return false;
		event.dataTransfer.setData("Text", JSON.stringify(tag));
	}

	private allowDrop(event) {
		if (!this.canEdit) return false;
		event.preventDefault();
	}

	public AddTagEvent = new EventEmitter();
	private drop(event, data: any) {
		if (!this.canEdit) return false;
		event.preventDefault();
		let tag = JSON.parse(event.dataTransfer.getData("Text")) as Tag;
		if (!tag.id) {
			this.ErrorEvent.emit('tag id is null');
			return false;
		}
		if (this.hasTag(data.tags, tag.id)) {
			this.ErrorEvent.emit('tag is exist');
			return false;
		}
		this.AddTagEvent.emit({
			year: this.year,
			month: this.month + 1,
			day: data.day,
			tagId: tag.id
		});
	}
	public TagClickEvent = new EventEmitter();
	private tagClick(tag: Tag) {
		this.TagClickEvent.emit(tag);
	}

	@ViewChild('tbody')
	tbody: ElementRef;

	@ViewChild('tag')
	tag: ElementRef;
}
