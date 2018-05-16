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
  div: any;

  constructor() {
    // this.div = document.createElement('div');
    // this.div.innerHTML = this.project.description;
  }

  ngOnInit() {
    this.div = document.createElement('div');
    this.div.innerHTML = this.project.description;
  }

  searchByTag(tag: any) {
    console.log(tag);
  }

}
