import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InitialFormComponent } from './initial-form/initial-form.component';
import { MainFormComponent } from './main-form/main-form.component';

const routes: Routes = [
  { path: '', component: InitialFormComponent },
  { path:'start', component:MainFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
