import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../shared/services/student.service';

@Component({
  selector: 'app-student-account',
  templateUrl: './student-account.component.html',
  styleUrls: ['./student-account.component.sass']
})
export class StudentAccountComponent implements OnInit {


  draggable = false;
  preferences = null;

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.studentService.getPreferences().subscribe(res => {
      this.preferences = res;
      console.log(res);
    }, err => {
      console.log('eroare');
    });
  }

  removeProject(index: number): void {
    this.studentService.removePreference(this.preferences[index].id).subscribe(res => {
      console.log(res);
      this.preferences.splice(index, 1);
    }, err => {
      console.log(err);
    });
  }

  saveOrder(): void {
    console.log(this.preferences);
    this.studentService.reorderPreference(this.preferences).subscribe(res => {
      console.log(res);
    }, err => {
      console.log(err);
    });
  }
}

