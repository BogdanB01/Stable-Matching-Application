import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';


@Component({
  selector: 'app-edit-project-dialog',
  templateUrl: './edit-project-dialog.component.html',
  styleUrls: ['./edit-project-dialog.component.sass']
})
export class EditProjectDialogComponent implements OnInit {
  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];
  tags = [
    { name : 'Web' },
    { name : 'Java' },
    { name : 'Spring' }
  ];
  references = [
    { url: 'http://google.com' },
    { url: 'http://spring.io' }
  ];
  constructor(
    public dialogRef: MatDialogRef<EditProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
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

  removeReference(index: number): void {
    console.log('da');
    this.references.splice(index, 1);
  }

  addReference(value: any) {

    console.log(value);
    if (value !== '') {
      this.references.push({ url: value});
    }
  }
}
