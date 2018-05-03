import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Component, Inject } from '@angular/core';

@Component({
    selector: 'app-delete.dialog',
    templateUrl: './delete.dialog.component.html',
    styleUrls: ['./delete.dialog.component.sass']
  })
export class DeleteDialogComponent {
    constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {}

    onNoClick(): void {
        this.dialogRef.close();
    }

    confirmDelete(): void {
        console.log('confirm delete');
    }
}
