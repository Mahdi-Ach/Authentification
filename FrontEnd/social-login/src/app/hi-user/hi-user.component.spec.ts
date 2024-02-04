import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HiUserComponent } from './hi-user.component';

describe('HiUserComponent', () => {
  let component: HiUserComponent;
  let fixture: ComponentFixture<HiUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HiUserComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HiUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
