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

const appRoutes: Routes = [
    { path: 'projects', component: ProjectListComponent /*, canActivate: [AuthGuard] */},
    { path: 'login', component: LoginComponent },
    { path: 'project-details', component: ProjectDetailsComponent /*, canActivate: [AuthGuard] */},
    { path: 'student-account', component: StudentAccountComponent },
    { path: 'lecturer-account', component: LecturerAccountComponent },
    { path: 'admin-account', component: AdminAccountComponent }
];

@NgModule({
    imports: [
      RouterModule.forRoot(
        appRoutes,
        { enableTracing: true } // <-- debugging purposes only
      )
    ],
    exports: [
      RouterModule
    ]
  })

export class AppRoutingModule {}
