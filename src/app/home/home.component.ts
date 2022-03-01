import { DataSource } from '@angular/cdk/table';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { CourseService } from '../course.service';
import { of } from 'rxjs';
import { RouteConfigLoadEnd, Router } from '@angular/router';
import { CourseDto } from '../model/course-dto';
import { Employee } from '../model/Employee';
import { Course } from '../course';
import { HomeService } from '../Service/home.service';

export interface DialogData {
  data: any[];
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public registeredCoursesByEmployee!: any;
  public unregisteredCoursesByEmployee!: any;
  public numberOfCourses!: any;
  public numberOfEmployeesInLast7days!: any;
  public completeCourseByEmployee!: any;
  public incompleteCourseByEmployee!:any;

  courses!: Course[];
  public employees!: any[];


  statistics!:  Map<Number, Employee[]>;
  coursesDto!: CourseDto[];

  constructor(public dialog: MatDialog,
    private homeService: HomeService) {}

  ngOnInit(): void {
    this.getUserHomePageStatistics();
    this.test();
  }

  public test():void{
    console.log("test", this.statistics);
  }

  Click(){
    this.dialog.open(DialogEmployee, {
      data: this.statistics.get(1)
    });
  }

  Click2(){
    // this.dialog.open(DialogCourse, {
    //   data: this.employees
    // });
  }


  public getUserHomePageStatistics(): void {
    // Map<status, Map<count, employees>>
    const employeesSubcribedCourse = new Map<Number,Employee[]>();

    this.homeService.getEmployees().subscribe(
      (employees: Employee[]) => {
        // đã đăng ký
        employeesSubcribedCourse.set(
          1, employees.filter(emp => Number(emp.status) === 1)
        );

        // chưa đăng ký
        employeesSubcribedCourse.set(
          2,
            employees.filter(emp => Number(emp.status) === 2)
        );
        
        // đã hoàn thành
        employeesSubcribedCourse.set(
          3,employees.filter(emp => Number(emp.status) === 3)
        );

        // chưa hoàn thành
        employeesSubcribedCourse.set(
          4, employees.filter(emp => Number(emp.status) === 4)
        );

        //console.log(...[...employeesSubcribedCourse.entries()]);
          this.statistics = employeesSubcribedCourse;
          console.log(this.statistics);
      }
    )
  }

  
    
}


@Component({
  selector: 'dialog-employee',
  templateUrl: 'dialogEmployee.html',
  styleUrls: ['dialogEmployee.css']
})
export class DialogEmployee {
  public employees!: any[];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any[],
    private courseService: CourseService,
    private router: Router,
    public dialog: MatDialog) {
      
  }

  displayedColumns = ['id', 'name', 'email', 'status', 'course_title', 'platform'];

isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
  expandedElement: any;
  test() {
    console.log('test');
  }

  cellClicked(element: { id: string; }) {
    console.log(element.id + ' cell clicked');
    this.router.navigate(['user-screen', element.id]);
  }
}

export interface Element {
  id: string,
  name: string,
  email:string,
  status:number,
  course_title:string,
  category: string
}


// @Component({
//   selector: 'dialog-course',
//   templateUrl: 'dialogCourse.html',
//   styleUrls: ['dialogCourse.css']
// })
// export class DialogCourse {
//   public employees!: any[];
//   constructor(@Inject(MAT_DIALOG_DATA) public data: any[],
//     private employeeService: EmployeeService) {
      
//   }

//   GetAllEmployee(){
//     this.employeeService.getCourses().subscribe(
//       (response: any[]) =>{
//         this.employees = response;
//         console.log(response);
//       },
//       (error: HttpErrorResponse) =>{
//         alert(error.message);
//       }
//     )
//   }
// }
