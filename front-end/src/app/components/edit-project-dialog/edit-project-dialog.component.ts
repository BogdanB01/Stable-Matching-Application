import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-edit-project-dialog',
  templateUrl: './edit-project-dialog.component.html',
  styleUrls: ['./edit-project-dialog.component.sass']
})
export class EditProjectDialogComponent implements OnInit {
  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];
  tags = [];
  references = [];
  updateProjectForm: FormGroup;
  onUpdate = new EventEmitter();
  constructor(
    public dialogRef: MatDialogRef<EditProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder) {
      this.tags = data.project.tags;
      this.references = data.project.references;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updateProject(): void {
    if (this.updateProjectForm.valid) {
      const project = this.prepareUpdateProject();
      this.onUpdate.emit(project);
      this.dialogRef.close();
    }
  }

  ngOnInit() {
    this.updateProjectForm = this.fb.group({
      titleControl : [this.data.project.title, Validators.required],
      capacityControl: [this.data.project.capacity, Validators.required],
      descriptionControl: [this.data.project.description, Validators.required]
    });
  }


  prepareUpdateProject(): any {
    const formModel = this.updateProjectForm.value;

    const updateProject: any = {
      title: formModel.titleControl as string,
      capacity: formModel.capacityControl as string,
      description: formModel.descriptionControl as string,
      tags: Object.assign([], this.tags),
      references: Object.assign([], this.references)
    };

    return updateProject;
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    // Add our tag
    if ((value || '').trim()) {
      this.tags.push(value.trim());
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

  removeReference(index: number): void {
    this.references.splice(index, 1);
  }

  addReference(value: any) {
    console.log(value);
    if (value !== '') {
      this.references.push({ url: value});
    }
  }
}
