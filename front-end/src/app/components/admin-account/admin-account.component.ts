import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserService } from '../../shared/services/user.service';
import { tap, distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { MatPaginator, MatDialog } from '@angular/material';
import { fromEvent } from 'rxjs/observable/fromEvent';

@Component({
  selector: 'app-admin-account',
  templateUrl: './admin-account.component.html',
  styleUrls: ['./admin-account.component.sass']
})
export class AdminAccountComponent implements OnInit, AfterViewInit {

  displayedColumns = ['email', 'name', 'role', 'actions'];
  dataSource: UsersDataSource;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  constructor(private userService: UserService,
              public dialog: MatDialog) {}

  ngOnInit() {
    this.dataSource = new UsersDataSource(this.userService);
    this.dataSource.loadUsers('', 0, 10);
  }

  ngAfterViewInit() {

    fromEvent(this.input.nativeElement, 'keyup')
            .pipe(
                debounceTime(150),
                distinctUntilChanged(),
                tap(() => {
                    this.paginator.pageIndex = 0;
                    console.log('aicia');
                    this.loadUsersPage();
                })
            )
            .subscribe();


    this.paginator.page
      .pipe(
        tap(() => this.loadUsersPage())
      )
      .subscribe();
  }

  loadUsersPage() {
    this.dataSource.loadUsers(
      this.input.nativeElement.value,
      this.paginator.pageIndex,
      this.paginator.pageSize
    );
  }

  removeUser(user): void {
    console.log(user);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: {
        title: user.name,
        positiveButton: 'Delete'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.userService.deleteUser(user.id).subscribe(res => {
          this.refreshTable();
        }, err => {
          console.log(err);
        });
      }
    });
  }


  private refreshTable(): void {

    if (this.paginator.hasNextPage()) {
      this.paginator.nextPage();
      this.paginator.previousPage();
    } else if (this.paginator.hasPreviousPage()) {
      this.paginator.previousPage();
      this.paginator.nextPage();
    } else {
      const aux = this.input.nativeElement.value;
     // this.input.nativeElement.keyup();
     this.input.nativeElement.dispatchEvent(new KeyboardEvent('keyup', {'key' : 'a'}));
     this.input.nativeElement.value = aux;
    }
  }

}

import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { ConfirmDialogComponent } from '../../dialogs/confirm/confirm.dialog.component';

export interface Page {
  content: Array<any>;
  first: boolean;
  last: boolean;
  numberOfElement: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export class UsersDataSource implements DataSource<any> {

  private usersSubject = new BehaviorSubject<any>(null);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private lengthSubject = new BehaviorSubject<number>(0);

  public loading$ = this.loadingSubject.asObservable();
  public length$ = this.lengthSubject.asObservable();

  constructor(private userService: UserService) {}

  connect(collectionViewer: CollectionViewer): Observable<any> {
    console.log(this.usersSubject);
    return this.usersSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.usersSubject.complete();
    this.loadingSubject.complete();
    this.lengthSubject.complete();
  }

  loadUsers(filter: string, pageIndex: number, pageSize: number) {
    this.loadingSubject.next(true);

    this.userService.getUsers(pageIndex, pageSize, filter).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
    .subscribe((users: Page) => {
      console.log(users);
      this.usersSubject.next(users.content);
      this.lengthSubject.next(users.totalElements);
    });
  }

}
