import { ChangeDetectorRef, Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './shared/services/auth.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { APP_CONSTANTS } from './shared/app.constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'app';

  // isLoggedIn$: Observable<boolean>;

  mobileQuery: MediaQueryList;
  searchOpen = false;
  private searchTerm = '';

  private _mobileQueryListener: () => void;

  public menus: any;

  constructor(
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    public authService: AuthService,
    private translate: TranslateService) {
      translate.setDefaultLang('ro');
      this.mobileQuery = media.matchMedia('(max-width: 600px)');
      this._mobileQueryListener = () => changeDetectorRef.detectChanges();
      this.mobileQuery.addListener(this._mobileQueryListener);
      // this.isLoggedIn$ = this.authService.isLoggedIn;

      this.authService.role.subscribe(role => {
        console.log(role);
        switch (role) {
          case APP_CONSTANTS.ROLE_LECTURER: {
            this.menus = APP_CONSTANTS.LECTURER_MENU;
            break;
          }
          case APP_CONSTANTS.ROLE_STUDENT: {
            this.menus = APP_CONSTANTS.STUDENT_MENU;
            break;
          }
          case APP_CONSTANTS.ROLE_ADMIN: {
            this.menus = APP_CONSTANTS.ADMIN_MENU;
            break;
          }
          // default:
          //   this.authService.logout();
        }
      });
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

}
