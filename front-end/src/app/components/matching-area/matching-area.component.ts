import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../shared/services/upload.service';
import { HistoryService } from '../../shared/services/history.service';
import { SnackBarService } from '../../shared/services/snackbar.service';
import { MatDialog } from '@angular/material';
import { ConfirmDialogComponent } from '../../dialogs/confirm/confirm.dialog.component';

@Component({
    selector: 'app-matching-area',
    templateUrl: './matching-area.component.html',
    styleUrls: ['./matching-area.component.sass']
})
export class MatchingAreaComponent implements OnInit {

    readonly APP_STATE_CLOSED = 'CLOSED';
    readonly APP_STATE_OPENED = 'OPENED';
    selectedYear: number;
    appStatus: boolean;
    years = [];

    constructor(private uploadService: UploadService,
                private historyService: HistoryService,
                private snackBarService: SnackBarService,
                private dialog: MatDialog) {}

    ngOnInit(): void {
        this.historyService.getYears().subscribe(data => {
            this.years = data;
        });

        this.historyService.getApplicationStatus().subscribe(data => {
            if (data.state === this.APP_STATE_CLOSED) {
                this.appStatus = false;
            } else if (data.state === this.APP_STATE_OPENED) {
                this.appStatus = true;
            }
        }, err => {
            console.log(err);
        });

    }

    uploadStudentsFile($event) {
        const fileList: FileList = $event.target.files;
        if (fileList.length > 0) {
            const file = fileList[0];
            this.uploadService.uploadFile(file, 'students');
        }
    }

    uploadLecturersFile($event) {
        const fileList: FileList = $event.target.files;
        if (fileList.length > 0) {
            const file = fileList[0];
            this.uploadService.uploadFile(file, 'lecturers');
        }
    }

    uploadGradesFile($event) {
        const fileList: FileList = $event.target.files;
        if (fileList.length > 0) {
            const file = fileList[0];
            this.uploadService.uploadFile(file, 'grades');
        }
    }

    uploadCoursesFile($event) {
        const fileList: FileList = $event.target.files;
        if (fileList.length > 0) {
            const file = fileList[0];
            this.uploadService.uploadFile(file, 'courses');
        }
    }

    match() {
        this.historyService.matchStudentsToProjectsAndGetReport();
    }

    viewHistoryForYear() {
        if (this.selectedYear !== undefined) {
            console.log(this.selectedYear);
            this.historyService.getReportForYear(this.selectedYear);
        }
    }

    changeAppStatus($event) {
        console.log(this.appStatus);
        if (this.appStatus === true) {
            console.log('activeaza');
            this.historyService.setApplicationStatus(this.APP_STATE_OPENED).subscribe(res => {
                console.log(res);
                this.snackBarService.showSnackBar('Starea aplicatiei a fost schimbata!');
            }, err => {
                console.log(err);
                this.appStatus = true;
            });
        } else {
            this.historyService.setApplicationStatus(this.APP_STATE_CLOSED).subscribe(res => {
                this.snackBarService.showSnackBar('Starea aplicatiei a fost schimbata!');
            }, err => {
                this.appStatus = false;
                console.log(err);
            });
        }
    }

    save() {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '350px',
            data: {
              title: 'Repartizeaza proiectele! Aceasta actiune este ireversibila!',
              positiveButton: 'Ok'
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.historyService.saveMatches();
            }
        });
    }

    deleteStudents() {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '350px',
            data: {
              title: 'Sterge studentii/preferintele',
              positiveButton: 'Delete'
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.historyService.clearStudents();
            }
        });
    }
}
