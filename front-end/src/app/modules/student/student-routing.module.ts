import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditStudentAccountComponent } from './edit-student-account/edit-student-account.component';
const routes: Routes = [
  {
    path: 'edit',
    component: EditStudentAccountComponent
  },
  {
    path: '',
    redirectTo: '/edit',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentRoutingModule { }
