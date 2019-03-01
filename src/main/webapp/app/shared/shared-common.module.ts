import { NgModule } from '@angular/core';

import { AccessGuardV5SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AccessGuardV5SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AccessGuardV5SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AccessGuardV5SharedCommonModule {}
