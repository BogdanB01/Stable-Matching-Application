import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app.routing.module';

import { ReactiveFormsModule } from '@angular/forms';  // <-- #1 import module
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { ProjectCardComponent } from './components/project-card/project-card.component';
import { ProjectDetailsComponent } from './components/project-details/project-details.component';
import { StudentAccountComponent } from './components/student-account/student-account.component';
import { LecturerAccountComponent, SearchStudentDialogComponent } from './components/lecturer-account/lecturer-account.component';
import { EditProjectDialogComponent } from './components/edit-project-dialog/edit-project-dialog.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { EditLecturerAccountComponent } from './components/edit-lecturer-account/edit-lecturer-account.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { EditStudentAccountComponent } from './components/edit-student-account/edit-student-account.component';
import { AdminAccountComponent } from './components/admin-account/admin-account.component';


import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MediaMatcher } from '@angular/cdk/layout';

import { DndModule } from 'ng2-dnd';


// services
import { AuthService } from './shared/services/auth.service';
import { AuthGuard } from './shared/services/auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SidebarComponent,
    ProjectCardComponent,
    ProjectDetailsComponent,
    StudentAccountComponent,
    LecturerAccountComponent,
    SearchStudentDialogComponent,
    EditProjectDialogComponent,
    CreateProjectComponent,
    EditLecturerAccountComponent,
    ProjectListComponent,
    EditStudentAccountComponent,
    AdminAccountComponent
  ],
  imports: [
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    FlexLayoutModule,
    BrowserModule,
    DndModule.forRoot(),
    AppRoutingModule
  ],
  entryComponents: [
    SearchStudentDialogComponent,
    EditProjectDialogComponent
  ],
  providers: [
    MediaMatcher,
    AuthService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
