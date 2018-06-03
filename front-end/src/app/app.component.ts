import { ChangeDetectorRef, Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'app';

  isLoggedIn$: Observable<boolean>;

  mobileQuery: MediaQueryList;
  searchOpen = false;
  private searchTerm = '';

  private _mobileQueryListener: () => void;


  menus = [
    {
      'name' : 'Proiecte',
      'icon' : 'dashboard',
      'link' : '/projects',
      'open' : false,
    },
    {
      'name' : 'Cont profesor',
      'icon' : 'account_circle',
      'link' : '/lecturer-account',
      'open' : true
    },
    {
      'name' : 'Cont student',
      'icon' : 'account_box',
      'link' : false,
      'open' : false,
      'sub' : [
        {
          'name' : 'Aplicarile mele',
          'icon' : 'account_box',
          'link' : '/student-account',
          'chip' : false,
          'open' : true
        },
        {
          'name' : 'Setari cont',
          'icon' : 'build',
          'link' : '/student-settings',
          'chip' : false,
          'open' : true
        }
      ]
    },
    {
      'name' : 'Cont admin',
      'icon' : 'account_box',
      'link' : false,
      'open' : false,
      'sub' : [
        {
          'name' : 'Utilizatori',
          'icon' : '',
          'link' : '/admin-account',
          'chip' : false,
          'open' : true
        },
        {
          'name' : 'Cursuri',
          'icon' : 'build',
          'link' : '',
          'chip' : false,
          'open' : true
        },
        {
          'name' : 'Matching area',
          'icon' : 'build',
          'link' : '/matching-area',
          'chip' : false,
          'open' : true
        }
      ]
    },
    {
      'name': 'LogOut',
      'icon': 'exit_to_app',
      'link': true,
      'open': false
    }
  ];


  constructor(
    private router: Router,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private authService: AuthService) {
      this.mobileQuery = media.matchMedia('(max-width: 600px)');
      this._mobileQueryListener = () => changeDetectorRef.detectChanges();
      this.mobileQuery.addListener(this._mobileQueryListener);
      this.isLoggedIn$ = this.authService.isLoggedIn;

      console.log(this.isLoggedIn$);

  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }


}
