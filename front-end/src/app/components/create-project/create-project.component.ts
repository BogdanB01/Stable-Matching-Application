import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { FormGroup, FormControl, FormGroupDirective, NgForm, FormBuilder, Validators } from '@angular/forms';
import { EditorConfig } from './editor-config';
import { UploadService } from '../../shared/services/upload.service';
import { ProjectService } from '../../shared/services/project.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.sass']
})
export class CreateProjectComponent implements OnInit, OnDestroy {

  separatorKeysCodes = [ENTER, COMMA];
  tags = [];
  references = [];
  questions = [];
  file = null;

  editorConfig = EditorConfig;

  @ViewChild('reference') ref: ElementRef;
  @ViewChild('question') questionRef: ElementRef;
  @ViewChild('fileInput') fileInput: ElementRef;

  createProjectForm: FormGroup;
  constructor(private fb: FormBuilder, private uploadService: UploadService, private projectService: ProjectService) {
    this.createForm();
  }

  ngOnInit() {
  }

  ngOnDestroy() {
  }

  createForm() {
    this.createProjectForm = this.fb.group({
      titleControl: ['', Validators.required],
      capacityControl: ['', [Validators.required, Validators.min(1)]],
      descriptionControl: ['', Validators.required],
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
    this.references.unshift({ name: reference });
    this.ref.nativeElement.value = '';
  }


  addQuestion(question: string): void {
    if (question === '') {
      return;
    }
    this.questions.unshift({question: question});
    this.questionRef.nativeElement.value = '';
  }

  removeReference(index: number): void {
    this.references.splice(index, 1);
  }

  removeQuestion(index: number): void {
    this.questions.splice(index, 1);
  }

  createProject(): void {
    const project = this.prepareCreateProject();
    // console.log(project);
    this.projectService.createProject(project).subscribe(
      res => {
        // clear form
        this.createProjectForm.reset();
        this.tags = [];
        this.references = [];
        this.questions = [];
        this.file = null;
        console.log(res);
      },
      err => {
        console.log(err);
      }
    );
  }

  fileChange(event): void {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0)  {
      const file = fileList[0];
      this.uploadService.pushFileToStorage(file).subscribe(res => {
        this.file = {path: res.body};
        console.log(this.file);
      }, err => {
        console.log('Failed to upload file');
      });
    }
  }

  removeFile(): void {
    this.uploadService.deleteFile(this.file.path).subscribe(res => {
      this.file = null;
      console.log('File deleted successfully');
    });
  }

  prepareCreateProject(): any {
    const formModel = this.createProjectForm.value;
    const project = {
      title: formModel.titleControl as string,
      capacity: formModel.capacityControl as string,
      description: formModel.descriptionControl as string,
      tags: Object.assign([], this.tags),
      questions: Object.assign([], this.questions),
      bibliographies: Object.assign([], this.references),
      file: this.file
    };

    return project;
  }
}


