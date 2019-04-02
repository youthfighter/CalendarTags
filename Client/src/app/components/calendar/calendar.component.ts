import { Component, OnInit, Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  private calenderData: any[][] = [[], [], [], [], [], []];
  private styleType = 'normal'; //normal small
  private tempMonthDatas = new Map(); //月数据缓存
  private _year: number;
  get year() {
    return this._year;
  }
  set year(value) {
    this._year = value;
  }

  private types = [
    { name: '看书', color: '#00acc1' },
    { name: '学习', color: '#25b343' },
    { name: '锻炼', color: '#4b88e4' },
    { name: '做饭', color: '#f7b84b' },
    { name: '单词', color: '#f1556c' },
    { name: '总结', color: '#515A6E' }
  ];

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
    this.initStyleType();
    console.log(this.tbody);
    console.log(this.tag)
    this.setTags(1, 1, [{ type: 1 }, { type: 0 }, { type: 2 }, { type: 3 }, { type: 4 }, { type: 5 }]);
  }

  private initStyleType() {
    console.log(this.tbody.nativeElement.clientHeight)
  }

  public turnToMonth(year: number, month: number) {
    if (!year || !month) return;
  }

  private initCalenderData() {
    this.MonthChangedEvent.emit({ year: this.year, month: this.month });
    let tempMonthData = this.tempMonthDatas.get(`${this.year}-${this.month}`);
    if (tempMonthData) {
      this.calenderData = tempMonthData;
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
    this.calenderData = tempCalenderData;
    this.tempMonthDatas.set(`${this.year}-${this.month}`, tempCalenderData);
    console.log(this.calenderData);
  }

  isToday(cd: Date) {
    let td = new Date();
    return cd.toLocaleDateString() == td.toLocaleDateString();
  }

  private isCurrentMonth() {
    let td = new Date();
    return this.year == td.getFullYear() && this.month == td.getMonth();
  }

  private toCurrentMonth() {
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
    let tagsArr = this.calenderData[i][j].tags;
    if (tagsArr) {
      if (!this.hasTag(tagsArr, type)) tagsArr.push({ type });
    } else {
      this.calenderData[i][j].tags = [{ type }];
    }
  }

  public setTags(i: number, j: number, tags: any[]) {
    this.calenderData[i][j].tags = tags;
  }

  drag(event, type) {
    console.log(event);
    event.dataTransfer.setData("Text", type);
  }

  allowDrop(event) {
    event.preventDefault();
    console.log('allow')
  }

  drop(event) {
    event.preventDefault();
    let ele = event.target as HTMLDivElement;
    let row = parseInt(ele.dataset.row);
    let column = parseInt(ele.dataset.column);
    if (isNaN(row) || isNaN(column)) return false;
    this.addTag(row, column, parseInt(event.dataTransfer.getData("Text")));
  }

  @ViewChild('tbody')
  tbody: ElementRef;

  @ViewChild('tag')
  tag: ElementRef;
}
