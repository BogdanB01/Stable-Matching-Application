import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';

@Component({
    selector: 'app-sidemenu-item',
    templateUrl: './sidemenu-item.component.html',
    styleUrls: ['./sidemenu-item.component.sass']
})
export class SidemenuItemComponent implements OnInit {

    @Input() menu;
    @Input() iconOnly;
    @Input() secondaryMenu = false;

    constructor(private authService: AuthService) { }

    ngOnInit() {

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
        console.log(this.menu);
        if (this.menu.name === 'LogOut') {
            console.log('salut');
            this.authService.logout();
        }
    }
}
