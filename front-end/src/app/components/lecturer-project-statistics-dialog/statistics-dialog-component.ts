import { Component, Inject, OnInit, ViewEncapsulation, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Chart } from 'chart.js';
import { ProjectService } from '../../shared/services/project.service';

@Component({
    selector: 'app-statistics-dialog-component',
    templateUrl: './statistics-dialog-component.html'
  })
export class StatisticsDialogComponent implements  OnInit {
    numberOfStudentsChart = [];
    avgMeanChart = [];
    avgPositionChart = [];

    @ViewChild('numberOfStudentsCanvas') numberOfStudentsCanvasRef: ElementRef;
    @ViewChild('avgMeanCanvas') avgMeanCanvasRef: ElementRef;
    @ViewChild('avgPositionCanvas') avgPositionCanvasRef: ElementRef;
    constructor(
        private projectService: ProjectService,
        public dialogRef: MatDialogRef<StatisticsDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        }

    createNumberOfStudentsChart(data) {
        const context = this.numberOfStudentsCanvasRef.nativeElement.getContext('2d');
        this.numberOfStudentsChart = new Chart(context, {
            type: 'bar',
            data: {
                labels: data.map(a => a.title),
                datasets: [
                    {
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        data: data.map(a => a.numberOfStudentsThatAppliedForProject),
                        label: 'Numar studenti',
                        fill: 'false'
                    },
                ]
            },
            options: {
                legend: {
                    display: false
                },
                elements: {
                    line: {
                        tension: 0.000001
                   }
                },
                maintainAspectRatio: false,
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'SITUATIA INSCRIERILOR'
                },
                scales: {
                    yAxes: [{
                            display: true,
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    }

    createAvgMeanChart(data) {
        const context = this.avgMeanCanvasRef.nativeElement.getContext('2d');
        this.numberOfStudentsChart = new Chart(context, {
            type: 'bar',
            data: {
                labels: data.map(a => a.title),
                datasets: [
                    {
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        data: data.map(a => a.averageStudentMean),
                        label: 'Medie studenti',
                        fill: 'false'
                    },
                ]
            },
            options: {
                legend: {
                    display: false
                },
                elements: {
                    line: {
                        tension: 0.000001
                   }
                },
                maintainAspectRatio: false,
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'MEDIA STUDENTILOR INSCRISI'
                },
                scales: {
                    yAxes: [{
                            display: true,
                            ticks: {
                                beginAtZero: true,
                                stepValue: 0.5,
                                max: 10
                            }
                        }]
                }
            }
        });
    }

    createAvgPositionChart(data) {
        const context = this.avgPositionCanvasRef.nativeElement.getContext('2d');
        this.avgPositionChart = new Chart(context, {
            type: 'bar',
            data: {
                labels: data.map(a => a.title),
                datasets: [
                    {
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        data: data.map(a => a.averagePositionInStudentsPreferences),
                        label: 'Medie pozitie',
                        fill: 'false'
                    },
                ]
            },
            options: {
                legend: {
                    display: false
                },
                elements: {
                    line: {
                        tension: 0.000001
                   }
                },
                maintainAspectRatio: false,
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'MEDIA PREFERINTELOR ASUPRA PROIECTELOR'
                },
                scales: {
                    yAxes: [{
                            display: true,
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    }
    ngOnInit(): void {
        this.projectService.getProjectStatistics().subscribe(res => {
            console.log(res);
            this.createNumberOfStudentsChart(res);
            this.createAvgMeanChart(res);
            this.createAvgPositionChart(res);
        });
    }
}
