import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'program',
        data: { pageTitle: 'vmsv2DataApp.program.home.title' },
        loadChildren: () => import('./program/program.module').then(m => m.ProgramModule),
      },
      {
        path: 'hierarchy',
        data: { pageTitle: 'vmsv2DataApp.hierarchy.home.title' },
        loadChildren: () => import('./hierarchy/hierarchy.module').then(m => m.HierarchyModule),
      },
      {
        path: 'program-user',
        data: { pageTitle: 'vmsv2DataApp.programUser.home.title' },
        loadChildren: () => import('./program-user/program-user.module').then(m => m.ProgramUserModule),
      },
      {
        path: 'work-location',
        data: { pageTitle: 'vmsv2DataApp.workLocation.home.title' },
        loadChildren: () => import('./work-location/work-location.module').then(m => m.WorkLocationModule),
      },
      {
        path: 'foundational-data-type',
        data: { pageTitle: 'vmsv2DataApp.foundationalDataType.home.title' },
        loadChildren: () => import('./foundational-data-type/foundational-data-type.module').then(m => m.FoundationalDataTypeModule),
      },
      {
        path: 'custom-field-type',
        data: { pageTitle: 'vmsv2DataApp.customFieldType.home.title' },
        loadChildren: () => import('./custom-field-type/custom-field-type.module').then(m => m.CustomFieldTypeModule),
      },
      {
        path: 'foundational-data',
        data: { pageTitle: 'vmsv2DataApp.foundationalData.home.title' },
        loadChildren: () => import('./foundational-data/foundational-data.module').then(m => m.FoundationalDataModule),
      },
      {
        path: 'custom-field-data',
        data: { pageTitle: 'vmsv2DataApp.customFieldData.home.title' },
        loadChildren: () => import('./custom-field-data/custom-field-data.module').then(m => m.CustomFieldDataModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
