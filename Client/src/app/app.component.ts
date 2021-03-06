import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';
import { CalendarComponent } from 'src/app/components/calendar/calendar.component';
import { ViewChild } from '@angular/core';
import { Tag } from './components/calendar/Tag';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private appService: AppService) {}

  async ngOnInit() {
    this.calendar.MonthChangedEvent.subscribe(async data => {
      let result = await this.appService.getMonthData(data.year, data.month) as any;
      if (result.error) {
        console.log(result.error);
        return;
      }
      this.calendar.setMonthTags(result.data);
      console.log(result);
    });
    this.calendar.TagMouseOverEvent.subscribe((data: Map<number, Tag[]>) => {
      console.log(data);
    });

    this.calendar.TagMouseOutEvent.subscribe((data: Map<number, Tag[]>) => {
      console.log(data);
    });
    this.calendar.AddTagEvent.subscribe(data => {
      console.log('AddTagEvent', data);
    });
    this.calendar.ErrorEvent.subscribe(data => {
      console.log('ErrorEvent', data)
    });
    this.calendar.TagClickEvent.subscribe(data => {
      console.log('TagClickEvent', data);
    })
  }

  @ViewChild('calendar')
  calendar: CalendarComponent;
}
