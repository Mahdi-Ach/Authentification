import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HiAdminComponent } from './hi-admin.component';

describe('HiAdminComponent', () => {
  let component: HiAdminComponent;
  let fixture: ComponentFixture<HiAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HiAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HiAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
