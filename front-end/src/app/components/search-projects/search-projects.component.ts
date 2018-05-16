import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { tap, distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { Subject } from 'rxjs/Subject';
import { ProjectListComponent } from '../project-list/project-list.component';

@Component({
    selector: 'app-search-projects',
    templateUrl: './search-projects.component.html',
    styleUrls: ['./search-projects.component.sass']
  })
export class SearchProjectsComponent implements AfterViewInit {

  searchType = 'title';
  @ViewChild('input') input: ElementRef;

  @ViewChild('child')
  private child: ProjectListComponent;

  ngAfterViewInit(): void {

    fromEvent(this.input.nativeElement, 'keyup')
    .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.child.initEvents(0, this.searchType, this.input.nativeElement.value);
        })
    )
    .subscribe();

  }
}
