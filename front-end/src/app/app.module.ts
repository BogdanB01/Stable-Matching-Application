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
import { LecturerAccountComponent } from './components/lecturer-account/lecturer-account.component';
import { EditProjectDialogComponent } from './components/edit-project-dialog/edit-project-dialog.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { EditLecturerAccountComponent } from './components/edit-lecturer-account/edit-lecturer-account.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { EditStudentAccountComponent } from './components/edit-student-account/edit-student-account.component';
import { AdminAccountComponent } from './components/admin-account/admin-account.component';
import { StatisticsDialogComponent } from './components/lecturer-project-statistics-dialog/statistics-dialog-component';
import { SidemenuItemComponent } from './components/sidemenu-item/sidemenu-item.component';
import { MatchingAreaComponent } from './components/matching-area/matching-area.component';
import { ProjectInfoComponent } from './components/project-info/project-info.component';
import { StudentDetailsComponent } from './components/student-details/student-details.component';
import { LecturerProjectsComponent } from './components/lecturer-projects/lecturer-projects.component';
import { ApplyDialogComponent } from './components/apply-dialog/apply-dialog.component';
import { SearchStudentDialogComponent } from './components/lecturer-projects/search-student-dialog.component';
import { SearchProjectsComponent } from './components/search-projects/search-projects.component';

import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MediaMatcher } from '@angular/cdk/layout';

import { DndModule } from 'ng2-dnd';

import { DeleteDialogComponent } from './dialogs/delete/delete.dialog.component';
import { PastProjectsComponent } from './dialogs/past-projects/past-projects.component';


// services
import { AuthService } from './shared/services/auth.service';
import { AuthGuard } from './shared/services/auth.guard';
import { SearchService } from './shared/services/search.service';
import { ProjectService } from './shared/services/project.service';
import { UploadService } from './shared/services/upload.service';
import { StudentService } from './shared/services/student.service';
import { UserService } from './shared/services/user.service';
import { SnackBarService } from './shared/services/snackbar.service';

// resolvers
import { ProjectDetailsResolve } from './shared/services/project.resolve.service';
import { ProjectInfoResolve } from './shared/services/project.info.resolve.service';


// pipes
import { EscapeHtmlPipe } from './shared/pipes/keep-html.pipe';


// editor
import { QuillModule } from 'ngx-quill';

import { HttpClientModule, HttpClient } from '@angular/common/http';

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
    AdminAccountComponent,
    StatisticsDialogComponent,
    SidemenuItemComponent,
    MatchingAreaComponent,
    ProjectInfoComponent,
    StudentDetailsComponent,
    LecturerProjectsComponent,
    EscapeHtmlPipe,
    ApplyDialogComponent,
    DeleteDialogComponent,
    PastProjectsComponent,
    SearchProjectsComponent
  ],
  imports: [
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    FlexLayoutModule,
    BrowserModule,
    DndModule.forRoot(),
    AppRoutingModule,
    QuillModule
  ],
  entryComponents: [
    SearchStudentDialogComponent,
    EditProjectDialogComponent,
    StatisticsDialogComponent,
    ApplyDialogComponent,
    DeleteDialogComponent,
    PastProjectsComponent
  ],
  providers: [
    MediaMatcher,
    AuthService,
    AuthGuard,
    UploadService,
    ProjectService,
    ProjectDetailsResolve,
    ProjectInfoResolve,
    SearchService,
    StudentService,
    UserService,
    SnackBarService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
