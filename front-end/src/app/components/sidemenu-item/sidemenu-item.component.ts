import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-sidemenu-item',
    templateUrl: './sidemenu-item.component.html',
    styleUrls: ['./sidemenu-item.component.sass']
})
export class SidemenuItemComponent implements OnInit {

    @Input() menu;
    @Input() iconOnly;
    @Input() secondaryMenu = false;
    title = '';

    constructor(private authService: AuthService,
                private translate: TranslateService) {
                }

    ngOnInit() {
        this.menu.open = false;
        // console.log(this.title);
    }

    openLink() {
        this.menu.open = this.menu.open;
    }
    getHeight() {

        if (this.menu.open === false) {
            return '48px';
        } else {
            if (this.menu && this.menu.sub) {
                const height = (this.menu.sub.length * 56) + 56 + 'px';
                return height;
           }
        }
    }

    chechForChildMenu() {
        return (this.menu && this.menu.sub) ? true : false;
    }

    click() {
        this.menu.open = !this.menu.open;

        if (this.menu.name === 'MENU.RO') {
            this.translate.use('ro');
        } else if (this.menu.name === 'MENU.EN') {
            this.translate.use('en');
        }

        if (this.menu.name === 'MENU.LOGOUT') {
            console.log('salut');
            this.authService.logout();
        }
    }
}
