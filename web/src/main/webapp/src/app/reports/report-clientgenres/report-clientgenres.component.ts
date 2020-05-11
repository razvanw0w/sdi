import {Component, OnInit} from '@angular/core';
import {ClientGenre} from "./clientgenre.model";
import {ReportService} from "../shared/report.service";

@Component({
  selector: 'app-report-clientgenres',
  templateUrl: './report-clientgenres.component.html',
  styleUrls: ['./report-clientgenres.component.css']
})
export class ReportClientgenresComponent implements OnInit {
  clientGenres: ClientGenre[];

  constructor(private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.reportService.getClientGenres().subscribe(dto => this.clientGenres = dto.clientGenres)
  }
}
