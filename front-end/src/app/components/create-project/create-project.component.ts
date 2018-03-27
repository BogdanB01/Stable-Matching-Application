import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { FormGroup, FormControl, FormGroupDirective, NgForm, FormBuilder, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.sass']
})
export class CreateProjectComponent implements OnInit {

  separatorKeysCodes = [ENTER, COMMA];
  tags = [];
  references = [];

  @ViewChild('reference') ref: ElementRef;

  createProjectForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  constructor(private fb: FormBuilder) {
    this.createForm();
  }

  ngOnInit() {
  }

  createForm() {
    this.createProjectForm = this.fb.group({
      titleControl: ['', Validators.required],
      capacityControl: ['', Validators.required],
      descriptionControl: ['', Validators.required]
    });
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our tag
    if ((value || '').trim()) {
      this.tags.push({ name: value.trim() });
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(tag: any): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  addReference(reference: string): void {
    if (reference === '') {
      return;
    }
    this.references.unshift({ url: reference });
    this.ref.nativeElement.value = '';
  }

  removeReference(index: number): void {
    this.references.splice(index, 1);
  }

  createProject(): void {
    console.log('ma submite');
  }
}


