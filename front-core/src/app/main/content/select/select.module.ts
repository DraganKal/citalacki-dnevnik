import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { SelectComponent } from './select/select.component';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [SelectComponent],
  exports:[SelectComponent]
})
export class SelectModule { }
