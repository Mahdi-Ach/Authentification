import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HiSuperAdminComponent } from './hi-super-admin.component';

describe('HiSuperAdminComponent', () => {
  let component: HiSuperAdminComponent;
  let fixture: ComponentFixture<HiSuperAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HiSuperAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HiSuperAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
