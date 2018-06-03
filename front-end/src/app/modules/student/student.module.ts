import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import { EditStudentAccountComponent } from './edit-student-account/edit-student-account.component';
import { MaterialModule } from '../../material.module';
import { FlexLayoutModule } from '@angular/flex-layout';


@NgModule({
  imports: [
    CommonModule,
    StudentRoutingModule,
    MaterialModule,
    FlexLayoutModule
  ],
  declarations: [
    EditStudentAccountComponent
  ]
})
export class StudentModule { }
