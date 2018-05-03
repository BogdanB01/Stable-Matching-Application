import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-student-details',
    templateUrl: './student-details.component.html',
    styleUrls: ['./student-details.component.sass']
})
export class StudentDetailsComponent implements OnInit {


    grades = [
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
        {'course': 'Structuri de date', 'value' : 10 },
    ];

    questions = [
        {'question': 'Care e materia ta preferata?', 'answer': 'Machine Learning'},
        {'question': 'Care e materia cel mai putin preferata?', 'answer': 'Matematica'},
        {'question': 'Ai vreo restanta', 'answer': 'nu'}
    ];
    ngOnInit(): void {

    }
}
