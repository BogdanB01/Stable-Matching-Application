import { Component, OnInit, Input } from '@angular/core';
import { StudentService } from '../../shared/services/student.service';

@Component({
    selector: 'app-student-details',
    templateUrl: './student-details.component.html',
    styleUrls: ['./student-details.component.sass']
})
export class StudentDetailsComponent implements OnInit {

    expandedFirstTime = false;
    details = null;

    @Input() projectId: any;
    @Input() preference: any;
    constructor(private studentService: StudentService) {}

    grades = [

    ];

    questions = [

    ];

    ngOnInit(): void {
        console.log(this.preference);
    }

    expandedPanel() {
        if (this.expandedFirstTime === false) {
            this.studentService.getDetails(this.preference.student.id, this.projectId).subscribe(res => {
                this.details = res;
                console.log(res);
            });

        }
        this.expandedFirstTime = true;
    }
}
