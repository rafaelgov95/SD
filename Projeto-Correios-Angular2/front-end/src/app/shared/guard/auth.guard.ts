import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Router } from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate() {
        if (localStorage.getItem('currentUser')) {
            return true;
        }

        this.router.navigate(['/']);
        return false;
    }
    redirectTo() {
        if (localStorage.getItem('isLoggedin')) {

            return 'dashboard';
        }

        this.router.navigate(['/login']);
        // return 'login';

    }
}
