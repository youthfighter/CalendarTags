import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';
import { CalendarComponent } from 'src/app/components/calendar/calendar.component';
import { ViewChild } from '@angular/core';

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
      this.calendar.setTagsBatch(result.data);
      console.log(result);
    });   
  }

  @ViewChild('calendar')
  calendar: CalendarComponent;
}
