import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProjectCardComponent } from './components/project-card/project-card.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AuthGuard } from './shared/services/auth.guard';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { ProjectDetailsComponent } from './components/project-details/project-details.component';
import { StudentAccountComponent } from './components/student-account/student-account.component';
import { LecturerAccountComponent } from './components/lecturer-account/lecturer-account.component';
import { AdminAccountComponent } from './components/admin-account/admin-account.component';
import { EditStudentAccountComponent } from './components/edit-student-account/edit-student-account.component';
import { MatchingAreaComponent } from './components/matching-area/matching-area.component';
import { ProjectInfoComponent } from './components/project-info/project-info.component';
import { LecturerProjectsComponent } from './components/lecturer-projects/lecturer-projects.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { EditLecturerAccountComponent } from './components/edit-lecturer-account/edit-lecturer-account.component';
import { ProjectDetailsResolve } from './shared/services/project.resolve.service';
import { ProjectInfoResolve } from './shared/services/project.info.resolve.service';
import { SearchProjectsComponent } from './components/search-projects/search-projects.component';

const appRoutes: Routes = [
    { path: 'lecturer-account', component: LecturerAccountComponent,
      children: [
        {path: '', redirectTo: 'proposed-projects', pathMatch: 'full'},
        {path: 'proposed-projects', component: LecturerProjectsComponent},
        {path: 'add-project', component: CreateProjectComponent},
        {path: 'account-settings', component: EditLecturerAccountComponent},
      ]},
    { path: 'lecturer-account/proposed-projects/:id/info', component: ProjectInfoComponent,
      resolve: {
        message: ProjectDetailsResolve
      }
    },
    { path: 'projects', component: ProjectListComponent /*, canActivate: [AuthGuard] */},
   // { path: 'login', component: LoginComponent },
    { path: 'student-settings', component: EditStudentAccountComponent},
    { path: 'projects/:id', component: ProjectDetailsComponent,
        resolve: {
          message: ProjectDetailsResolve
        }
    },
    { path: 'search', component: SearchProjectsComponent },
    { path: 'student-account', component: StudentAccountComponent },
    { path: 'admin-account', component: AdminAccountComponent },
    { path: 'matching-area', component: MatchingAreaComponent}
];

@NgModule({
    imports: [
      RouterModule.forRoot(
        appRoutes
      //  { enableTracing: true } // <-- debugging purposes only
      )
    ],
    exports: [
      RouterModule
    ]
  })

export class AppRoutingModule {}
