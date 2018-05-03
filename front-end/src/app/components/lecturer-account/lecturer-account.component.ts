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
      'label': 'Proiecte propuse'
    },
    {
      'path': '/lecturer-account/add-project',
      'label': 'Adauga un proiect'
    },
    {
      'path': '/lecturer-account/account-settings',
      'label': 'Setarile contului'
    }
  ];
}



