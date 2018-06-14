import { Component, OnInit, Inject } from '@angular/core';
import { CreateProjectComponent } from '../create-project/create-project.component';

@Component({
  selector: 'app-lecturer-account',
  templateUrl: './lecturer-account.component.html',
  styleUrls: ['./lecturer-account.component.sass']
})
export class LecturerAccountComponent {

  navLinks = [
    {
      'path': '/lecturer-account/proposed-projects',
      'label': 'LECTURERS.ACCOUNT.PROPOSED_PROJECTS'
    },
    {
      'path': '/lecturer-account/add-project',
      'label': 'LECTURERS.ACCOUNT.ADD_PROJECT'
    },
    {
      'path': '/lecturer-account/account-settings',
      'label': 'LECTURERS.ACCOUNT.ACCOUNT_SETTINGS'
    }
  ];
}



