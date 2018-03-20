import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-account',
  templateUrl: './student-account.component.html',
  styleUrls: ['./student-account.component.sass']
})
export class StudentAccountComponent implements OnInit {

  tasks: Array<string> = ['Aplicatii ale problemor de tip Stable Matching',
                          'Baze de date de tip graf',
                          'Criptografie pe curbe eliptice',
                          'Sisteme distribuite',
                          'Dreseaza-l pe cutzu virtual',
                          'Inca un proiect de licenta',
                          'Alt proiect',
                          'La fel'
                        ];
  constructor() { }

  ngOnInit() {
  }

  removeProject(index: number): void {
    this.tasks.splice(index, 1);
  }
}
