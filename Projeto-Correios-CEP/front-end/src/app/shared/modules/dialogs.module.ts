import { DialogsService } from './../dialog/dialogs.service';
import { ConfirmDialog } from './../dialog/confirm-dialog.component';
import { MdDialogModule, MdButtonModule  } from '@angular/material';
import { NgModule } from '@angular/core';



@NgModule({
    imports: [
        MdDialogModule,
        MdButtonModule,
    ],
    exports: [
        ConfirmDialog,
    ],
    declarations: [
        ConfirmDialog,
    ],
    providers: [
        DialogsService,
    ],
    entryComponents: [
        ConfirmDialog,
    ],
})
export class DialogsModule { }
