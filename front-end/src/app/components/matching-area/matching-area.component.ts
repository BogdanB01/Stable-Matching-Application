import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../shared/services/upload.service';

@Component({
    selector: 'app-matching-area',
    templateUrl: './matching-area.component.html',
    styleUrls: ['./matching-area.component.sass']
})
export class MatchingAreaComponent implements OnInit {

    constructor(private uploadService: UploadService) {}

    ngOnInit(): void {

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

}
