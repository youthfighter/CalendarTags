import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private appService: AppService) {}

  async ngOnInit() {
    let result = await this.appService.getMonthData();
    console.log(result);
  }
}
