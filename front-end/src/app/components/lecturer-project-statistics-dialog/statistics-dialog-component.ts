import { Component, Inject, OnInit, ViewEncapsulation, AfterViewInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Chart } from 'chart.js';

@Component({
    selector: 'app-statistics-dialog-component',
    templateUrl: './statistics-dialog-component.html'
  })
export class StatisticsDialogComponent implements  OnInit {
    chart = [];
    constructor(
        public dialogRef: MatDialogRef<StatisticsDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        }


    ngOnInit(): void {
        this.chart = new Chart('canvas', {
            type: 'bar',
            data: {
                labels: [['Aplicatii', 'ale', 'stable', 'matching'],
                         ['Baze de', 'date de', 'tip graf'],
                         ['Criptografie', 'avansata'],
                         ['Stable', 'matching'],
                         ['Partajarea', 'secretelor'],
                         ['Proiect de', 'licenta']
                        ],
                datasets: [
                    {
                        backgroundColor: '#7E57C2',
                        borderColor: '#7E57C2',
                        data: [0, 1, 2, 4, 2, 1],
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
                }
            }
        });
    }
}
