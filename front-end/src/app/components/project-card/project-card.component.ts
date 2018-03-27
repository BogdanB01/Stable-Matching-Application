import { Component, OnInit, Input } from '@angular/core';
import { Project } from '../../shared/interfaces/project';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.sass']
})
export class ProjectCardComponent implements OnInit {
  @Input() project: Project;
  color: string;

  // availableColors = [
  //   { name: 'Web', color: '' },
  //   { name: 'Java', color: 'primary' },
  //   { name: 'PostgreSQL', color: 'accent' },
  //   { name: 'Rest', color: 'warn' }
  // ];

  constructor() { }

  ngOnInit() {
  }

}
