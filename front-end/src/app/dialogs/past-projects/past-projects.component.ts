import { MAT_DIALOG_DATA, MatDialogRef, MatSelectionList } from '@angular/material';
import { Component, Inject, OnInit, EventEmitter } from '@angular/core';
import { ProjectService } from '../../shared/services/project.service';

@Component({
    selector: 'app-past-projects',
    templateUrl: './past-projects.component.html',
    styleUrls: ['./past-projects.component.sass']
  })
export class PastProjectsComponent implements OnInit {

    projects = null;
    selectedOptions = [];
    updatedProjects = new EventEmitter();

    constructor(public dialogRef: MatDialogRef<PastProjectsComponent>,
                private projectService: ProjectService,
                @Inject(MAT_DIALOG_DATA) public data: any) {}

    ngOnInit(): void {
        this.projectService.getInactiveProjectsForLecturer().subscribe(resp => {
            this.projects = resp;
        });
    }

    activateProjects(): void {
        const updatedProjects = [];
        for (let i = 0; i < this.selectedOptions.length; i++) {
            updatedProjects.push(this.projects[this.selectedOptions[i]]);
            updatedProjects[i].active = true;
        }
        this.projectService.updateProjects(updatedProjects).subscribe(res => {
            this.updatedProjects.emit(updatedProjects);
            this.dialogRef.close();
        }, err => {
            console.log(err);
        });


    }

}
