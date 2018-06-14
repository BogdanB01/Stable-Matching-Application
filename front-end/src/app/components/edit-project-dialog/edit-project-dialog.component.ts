import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EditorConfig } from '../create-project/editor-config';
import { UploadService } from '../../shared/services/upload.service';
import { ProjectService } from '../../shared/services/project.service';

@Component({
  selector: 'app-edit-project-dialog',
  templateUrl: './edit-project-dialog.component.html',
  styleUrls: ['./edit-project-dialog.component.sass']
})
export class EditProjectDialogComponent implements OnInit {
  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];
  editorConfig = EditorConfig;
  tags = [];
  references = [];
  questions = [];
  updateProjectForm: FormGroup;
  file = null;
  onUpdate = new EventEmitter();

  constructor(
    public dialogRef: MatDialogRef<EditProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public project: any,
    private uploadService: UploadService,
    private projectService: ProjectService,
    private fb: FormBuilder) {
      this.tags = project.tags;
      this.references = project.bibliographies;
      this.questions = project.questions;
      this.file = project.file;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updateProject(): void {
    if (this.updateProjectForm.valid) {
      const project = this.prepareUpdateProject();
      this.projectService.updateProject(project).subscribe(res => {
        console.log(res);
        this.onUpdate.emit(res);
        this.dialogRef.close();
      }, err => {
        console.log('error occured');
      });
    }
  }

  ngOnInit() {
    this.updateProjectForm = this.fb.group({
      titleControl : [this.project.title, Validators.required],
      capacityControl: [this.project.capacity, Validators.required],
      descriptionControl: [this.project.description, Validators.required]
    });
  }


  prepareUpdateProject(): any {
    const formModel = this.updateProjectForm.value;

    const updateProject: any = {
      id: this.project.id,
      title: formModel.titleControl as string,
      capacity: formModel.capacityControl as string,
      description: formModel.descriptionControl as string,
      tags: Object.assign([], this.tags),
      bibliographies: Object.assign([], this.references),
      questions: Object.assign([], this.questions),
      file: this.file,
      assignedProjects: Object.assign([], this.project.assignedProjects)
    };

    console.log(updateProject);
    return updateProject;
  }

  addTag(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    // Add our tag
    if ((value || '').trim()) {
      this.tags.push({name: value.trim()});
    }
    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeTag(tag: any): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  removeReference(index: number): void {
    this.references.splice(index, 1);
  }

  addReference(value: any) {
    if (value !== '') {
      this.references.push({ name: value});
    }
  }

  addQuestion(value: any) {
    if (value !== '') {
      this.questions.push({question: value});
    }
  }
  removeQuestion(index: number): void {
    this.questions.splice(index, 1);
  }

  removeFile(): void {
    this.file = null;
  }

  downloadFile(): void {
    this.uploadService.getFile(this.file.path);
  }

  fileChange(event): void {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0)  {
      const file = fileList[0];
      this.uploadService.pushFileToStorage(file).subscribe(res => {
        this.file = {path: res.body};
        console.log('File uploaded successfully!');
      }, err => {
        console.log('Error uploading file!');
      });
    }
  }
}
