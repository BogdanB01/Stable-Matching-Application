import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { StudentService } from '../../shared/services/student.service';

@Component({
    selector: 'app-apply-dialog-component',
    templateUrl: './apply-dialog-component.html'
})
export class ApplyDialogComponent implements OnInit {

    questions: Array<any>;
    questionsForm: FormGroup;

    constructor(public dialogRef: MatDialogRef<ApplyDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                private fb: FormBuilder,
                private studentService: StudentService) {
                    this.questions = data.questions;
                }

    ngOnInit() {
        this.questionsForm = this.fb.group({
            items: this.fb.array( [ ] )
        });

        for (let i = 0; i < this.questions.length; i++) {
            this.addItem();
        }

        // const control: FormArray = this.questionsForm.get('items') as FormArray;
    }

    createItem(): FormGroup {
        return this.fb.group({
            answer: ['', Validators.required]
        });
    }

    addItem(): void {
        const control: FormArray = this.questionsForm.get('items') as FormArray;
        control.push(this.createItem());
    }

    submit(): void {
        const request = this.prepareSubmit();

        this.studentService.addPreference(request).subscribe(res => {
            console.log(res);
        }, err => {
            console.log(err);
        });
    }

    prepareSubmit() {
        const formModel = this.questionsForm.value;

        // deep copy of form model questions
        const request = {
            projectId : this.data.projectId,
            answers : []
        };

        for (let i = 0; i < this.questions.length; i++) {
            request.answers.push({
                questionId : this.questions[i].id,
                answer: formModel.items[i].answer
            });
        }

        console.log(request);

        return request;
    }
}
