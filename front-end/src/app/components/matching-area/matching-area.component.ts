import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../shared/services/upload.service';
import { HistoryService } from '../../shared/services/history.service';

@Component({
    selector: 'app-matching-area',
    templateUrl: './matching-area.component.html',
    styleUrls: ['./matching-area.component.sass']
})
export class MatchingAreaComponent implements OnInit {

    selectedYear: number;

    years = [];

    constructor(private uploadService: UploadService,
                private historyService: HistoryService) {}

    ngOnInit(): void {
        this.historyService.getYears().subscribe(data => {
            this.years = data;
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
        this.uploadService.matchStudentsToProjectsAndGetReport();
    }

    viewHistoryForYear() {
        if (this.selectedYear !== undefined) {
            console.log(this.selectedYear);
            this.historyService.getReportForYear(this.selectedYear);
        }
    }
}
