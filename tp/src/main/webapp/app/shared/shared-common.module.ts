import { NgModule } from '@angular/core';

import { ClientwebSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ClientwebSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ClientwebSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ClientwebSharedCommonModule {}
