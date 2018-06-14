import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../../shared/services/project.service';

@Component({
    selector: 'app-project-info',
    templateUrl: './project-info.component.html',
    styleUrls: ['./project-info.component.sass']
})
export class ProjectInfoComponent implements OnInit {

    project: any;
    preferences = null;
    empty = null;
    reorderList = false;

    constructor(private route: ActivatedRoute,
                private projectService: ProjectService) {
        this.project = this.route.snapshot.data.message;
    }

    ngOnInit(): void {
        this.projectService.getProjectPreferences(this.project.id).subscribe(res => {
            this.preferences = res;
            console.log(this.preferences);
        });
    }

    moveFirst(i: number) {
        this.move(i, 0);
    }

    moveLast(i: number) {
        this.move(i, this.preferences.length - 1);
    }

    private move(from, to) {
        if (from === to) {
            return;
        }

        const target = this.preferences[from];
        const increment = to < from ? -1 : 1;

        for (let k = from; k !== to; k += increment) {
            this.preferences[k] = this.preferences[k + increment];
        }
        this.preferences[to] = target;
    }

    saveOrder(): void {
        this.projectService.reorderPreferences(this.preferences, this.project.id);
    }

}
