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
import { AdminAccountComponent } from './components/admin-account/admin-account.component';
import { StatisticsDialogComponent } from './components/lecturer-project-statistics-dialog/statistics-dialog-component';
import { SidemenuItemComponent } from './components/sidemenu-item/sidemenu-item.component';
import { MatchingAreaComponent } from './components/matching-area/matching-area.component';
import { ProjectInfoComponent } from './components/project-info/project-info.component';
import { StudentDetailsComponent } from './components/student-details/student-details.component';
import { LecturerProjectsComponent } from './components/lecturer-projects/lecturer-projects.component';
import { ApplyDialogComponent } from './components/apply-dialog/apply-dialog.component';
import { SearchProjectsComponent } from './components/search-projects/search-projects.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { EditStudentAccountComponent } from './components/edit-student-account/edit-student-account.component';
import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MediaMatcher } from '@angular/cdk/layout';

import { DndModule } from 'ng2-dnd';

import { ConfirmDialogComponent } from './dialogs/confirm/confirm.dialog.component';
import { PastProjectsComponent } from './dialogs/past-projects/past-projects.component';


// services
import { AuthService } from './shared/services/auth.service';
import { AuthGuard } from './shared/guards/auth.guard';
import { RoleGuard } from './shared/guards/role.guard';
import { SearchService } from './shared/services/search.service';
import { ProjectService } from './shared/services/project.service';
import { UploadService } from './shared/services/upload.service';
import { StudentService } from './shared/services/student.service';
import { UserService } from './shared/services/user.service';
import { SnackBarService } from './shared/services/snackbar.service';
import { HistoryService } from './shared/services/history.service';

// resolvers
import { ProjectDetailsResolve } from './shared/services/project.resolve.service';
import { ProjectInfoResolve } from './shared/services/project.info.resolve.service';


// pipes
import { EscapeHtmlPipe } from './shared/pipes/keep-html.pipe';


// editor
import { QuillModule } from 'ngx-quill';

import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ApiInterceptor } from './shared/interceptors/api-interceptor';
import { JwtInterceptor } from './shared/interceptors/jwt.interceptor';
import { LoaderService } from './components/loader/loader.service';
import { LoaderComponent } from './components/loader/loader.component';

// import ngx-translate and the http loader
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AuthModule } from './auth.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SidebarComponent,
    ProjectCardComponent,
    ProjectDetailsComponent,
    StudentAccountComponent,
    LecturerAccountComponent,
    EditProjectDialogComponent,
    CreateProjectComponent,
    EditLecturerAccountComponent,
    ProjectListComponent,
    AdminAccountComponent,
    StatisticsDialogComponent,
    SidemenuItemComponent,
    MatchingAreaComponent,
    ProjectInfoComponent,
    StudentDetailsComponent,
    LecturerProjectsComponent,
    EscapeHtmlPipe,
    ApplyDialogComponent,
    ConfirmDialogComponent,
    PastProjectsComponent,
    SearchProjectsComponent,
    LoaderComponent,
    EditStudentAccountComponent,
    NotFoundComponent
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
    QuillModule,
    AuthModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  entryComponents: [
    EditProjectDialogComponent,
    StatisticsDialogComponent,
    ApplyDialogComponent,
    ConfirmDialogComponent,
    PastProjectsComponent
  ],
  providers: [
    MediaMatcher,
    AuthService,
    AuthGuard,
    RoleGuard,
    UploadService,
    ProjectService,
    ProjectDetailsResolve,
    ProjectInfoResolve,
    SearchService,
    StudentService,
    UserService,
    SnackBarService,
    LoaderService,
    HistoryService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
