import { Component, OnInit, OnDestroy, AfterViewChecked } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { LoaderService } from './loader.service';
import { LoaderState } from './loader';
import { ChangeDetectorRef } from '@angular/core';
@Component({
    selector: 'app-loader',
    templateUrl: './loader.component.html',
    styleUrls: ['./loader.component.sass']
})
export class LoaderComponent implements OnDestroy, AfterViewChecked {

    show = false;
    private subscription: Subscription;

    constructor(private loaderService: LoaderService,
                private cdRef: ChangeDetectorRef) {}

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    ngAfterViewChecked(): void {
        this.subscription = this.loaderService.loaderState
        .subscribe((state: LoaderState) => {
            this.show = state.show;
            this.cdRef.detectChanges();
        });
    }
}
