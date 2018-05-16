import { Component, Inject, EventEmitter} from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';
import { SearchService } from '../../shared/services/search.service';
import { DataSource } from '@angular/cdk/table';
import { ProjectService } from '../../shared/services/project.service';
import { SnackBarService } from '../../shared/services/snackbar.service';

@Component({
  selector: 'app-search-student-dialog',
  templateUrl: 'search-student-dialog.html',
  styleUrls: ['./search-student-dialog.sass']
})
export class SearchStudentDialogComponent {


  searchTerm: FormControl = new FormControl();
  onAssign = new EventEmitter();

  searchResult = [];
  projectId = null;
  constructor(
    private searchService: SearchService,
    private projectService: ProjectService,
    public dialogRef: MatDialogRef<SearchStudentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBarService: SnackBarService) {

      this.projectId = data.projectId;
      this.searchTerm.valueChanges
        .debounceTime(400)
        .subscribe(stud => {
          // console.log(stud);
          if (stud !== '') {
            this.searchService.searchStudent(stud).subscribe(response => {
              this.searchResult = response;
              console.log(response);
            });
          }
        });
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  assignStudent(name: any): void {
    if (name !== '') {
      this.projectService.assignStudent(this.projectId, name).subscribe(res => {
        this.onAssign.emit(res);
        this.snackBarService.showSnackBar('Studentul a fost adaugat cu succes!');
        this.dialogRef.close();
      }, err => {
        this.snackBarService.showSnackBar(err.error.message);
        console.log(err);
      }
    );
    }
  }

  displayFn(student: any): void {
    return student ? student.name : undefined;
  }
}
