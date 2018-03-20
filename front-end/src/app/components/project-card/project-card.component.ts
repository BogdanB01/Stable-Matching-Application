import { Component, OnInit } from '@angular/core';
import { Tag } from '../../shared/interfaces/tag';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.sass']
})
export class ProjectCardComponent implements OnInit {
  color: string;

  availableColors = [
    { name: 'Web', color: '' },
    { name: 'Java', color: 'primary' },
    { name: 'PostgreSQL', color: 'accent' },
    { name: 'Rest', color: 'warn' }
  ];

  constructor(private router: Router) { }

  goToDetails(): void {
    this.router.navigate(['/project-details']);
  }
  ngOnInit() {
  }

}
